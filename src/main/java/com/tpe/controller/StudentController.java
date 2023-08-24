package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // not: getAll()********

    @GetMapping
    public ResponseEntity<List<Student>>getAll(){

      List<Student> students =  studentService.getAll();

      return ResponseEntity.ok(students);

    }
    // Not: createStudent()************************************
    @PostMapping // http://localhost:8080/students  + POST + JSON
    public ResponseEntity<Map<String, String>> createStudent(@RequestBody @Valid Student student){

        studentService.createStudent(student);

        Map<String ,String> map = new HashMap<>();
        map.put("message", "Student is created successfully");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); // map + 201

    }

    @GetMapping("/query")// http://localhost:8080/students/query?id=1 + GET

    public ResponseEntity<Student>getStudent(@RequestParam("id")Long id){
       Student student = studentService.findStudent(id);
       return ResponseEntity.ok(student);
    }


    @GetMapping("/{id}")       // http://localhost:8080/students/1 + GET

    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){

        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")  // http://localhost:8080/students/1 + DELETE

    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id){

        studentService.deleteStudent(id);
        String message = " Student is deleted  successfuly";

        return new ResponseEntity<>(message,HttpStatus.OK);

    }

      @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id,
                                                @RequestBody StudentDTO studentDTO){


        studentService.updateStudent(id,studentDTO);
        String message = "Student is updated successfully";
        return  new ResponseEntity<>(message,HttpStatus.OK);



   }

    // Not: pageable *************************************************************
    @GetMapping("/page") // http://localhost:8080/students/page + GET
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page, // kacinci sayfa gelecek
            @RequestParam("size") int size, // her sayfada kac adet urun olsun  ( ilk iki annot. zorunlu )
            @RequestParam("sort") String prop, // hangi filed a gore siralama yapilacak
            @RequestParam("direction")Sort.Direction direction // dogal siralamami yoksa tersden siralamami yapilacak
    ){

        Pageable pageable = PageRequest.of(page,size, Sort.by(direction, prop));

        Page<Student> studentPage = studentService.getAllWithPage(pageable);

        return ResponseEntity.ok(studentPage);
    }

    // Not: JPQL **********************************************************************
    // 75 puan alan ogrencileri getirelim
    @GetMapping("/grade/{grade}") // http://localhost:8080/students/grade/75 + GET
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable("grade") Integer grade) {

        List<Student> students = studentService.findAllEqualsGrade(grade);

        return ResponseEntity.ok(students);
    }









}
