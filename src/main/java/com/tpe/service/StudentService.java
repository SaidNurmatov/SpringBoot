package com.tpe.service;
import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exeption.ConflictExeption;
import com.tpe.exeption.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // opsional degil zorunlu
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;


    // not: getAll()********
    public List<Student> getAll() {

       return studentRepository.findAll();

    }

    public void createStudent(Student student) {

        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictExeption("Email is already exist !");

        }

        studentRepository.save(student);

    }

    // Not: getByIdWithRequestParam()**********************************************
    public Student findStudent(Long id) {

        return studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id: "+ id));
    }

    public void deleteStudent(Long id) {

        Student student = findStudent(id); // varsa getir

        studentRepository.delete(student);

    }

    public void updateStudent(Long id, StudentDTO studentDTO) {
        // id li ogrenci var mi kontrolu

        Student student = findStudent(id);

        // email uniq oldugu icin kontorl lazim

        /*
         birden fazla kontrol etmem gereken seyler

         1.mevcut email : a@b.com  , yeni email : a@b.com ise  ----> true
         2. mevcut email : a@b.com , yeni email : d@b.com ise( db de zaten var ) ---->false
         3. mevcut email : a@b.com , yeni email : g@f.com ise ( db de yok ) ---> true

         */
        boolean emailExists = studentRepository.existsByEmail(studentDTO.getEmail());

        if( emailExists && !studentDTO.getEmail().equals(student.getEmail())){

            throw  new ConflictExeption("Email is already exist !!!"); // tekrarli kod mevcud ve hard gibi
        }

        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setGrade(studentDTO.getGrade());
        student.setPhoneNumber(studentDTO.getPhoneNumber());


        studentRepository.save(student); // eski id deismeden kalir


    }

    public Page<Student> getAllWithPage(Pageable pageable) {

       return studentRepository.findAll(pageable);

    }


    // Not: JPQL **********************************************************************
    public List<Student> findAllEqualsGrade(Integer grade) {

        return studentRepository.findAllEqualsGrade(grade);
    }

    public StudentDTO findStudentDtoById(Long id) {

        return studentRepository.findStudentDtoById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id : " + id));
    }
}
