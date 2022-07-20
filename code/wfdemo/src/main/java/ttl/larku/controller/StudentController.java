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

@RestController
@RequestMapping("student")
public class StudentController {

    @GetMapping("/hello")
    public String sayHello(){
        return "here we go";

    }

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") int id){
        return ResponseEntity.ok(studentService.getStudent(id));
//        return studentService.getStudent(id);

    }

    @GetMapping
    public List<Student> getAllStudent(){
        return studentService.getAllStudents();

    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student){

        var newStudent= studentService.createStudent(student);
        URI newResource = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(newStudent.getId())
                .toUri();
        return ResponseEntity.created(newResource).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id){

        boolean b= studentService.deleteStudent(id);
        if(!b){
            return ResponseEntity.status(404).body("no student with id " +id);
        } else{
            return ResponseEntity.noContent().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody Student student){

        boolean b= studentService.updateStudent(student);
        if(!b){
            return ResponseEntity.status(404).body("no student with id " + student.getId());
        } else{
            return ResponseEntity.noContent().build();
        }

    }

}
