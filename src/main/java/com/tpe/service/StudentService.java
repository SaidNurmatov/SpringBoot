package com.tpe.service;
import com.tpe.domain.Student;
import com.tpe.exeption.ConflictExeption;
import com.tpe.exeption.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
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

        Student student = findStudent(id);

        studentRepository.delete(student);

    }
}
