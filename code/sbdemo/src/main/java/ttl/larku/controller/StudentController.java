package ttl.larku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.net.URI;
import java.util.List;

/**
 * @author whynot
 */

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
//    @GetMapping("/hello")
//    public String sayHello() {
//        return "Here we go";
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
       Student s = studentService.getStudent(id);
       if(s == null) {
           return ResponseEntity.status(404).body("No student with id: " + id);
       }
       return ResponseEntity.ok(s);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return students;
    }

    @PostMapping
    public ResponseEntity<?> insertStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);

        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newStudent.getId())
                .toUri();

        //return ResponseEntity.created(newResource).body(newStudent);
        return ResponseEntity.created(newResource).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        boolean b = studentService.deleteStudent(id);
        if(!b) {
            return ResponseEntity.status(404).body("No student with id: " + id);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        boolean b = studentService.updateStudent(student);
        if(!b) {
            return ResponseEntity.status(404).body("No student with id: " + student.getId());
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }
}
