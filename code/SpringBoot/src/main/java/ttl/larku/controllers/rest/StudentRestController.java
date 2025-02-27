package ttl.larku.controllers.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminrest/student")
public class StudentRestController {

    private final UriCreator uriCreator;
    private StudentService studentService;

    //Constructor injection.  Also helps with testing.
    public StudentRestController(StudentService studentService, UriCreator uriCreator) {
        this.studentService = studentService;
        this.uriCreator = uriCreator;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
        Student s = studentService.getStudent(id);
        if (s == null) {
            return ResponseEntity.badRequest().body(RestResultGeneric.ofError("Student with id: " + id + " not found"));
        }
        return ResponseEntity.ok(RestResultGeneric.ofValue(s));
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}, path = "/lowlevel")
    public String getLow(HttpServletRequest request, @RequestHeader HttpHeaders headers) throws IOException {
        String acceptHeader = headers.get("accept").stream().findFirst().orElse(null);
        String method = request.getMethod();
        String result = "boo";
        if (method.equalsIgnoreCase("POST")) {
            result = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        return result;
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(RestResultGeneric.ofValue(students));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student s) {
        s = studentService.createStudent(s);

        URI newResource = uriCreator.getUriFor(s.getId());

//        URI newResource = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(s.getId())
//                .toUri();

        return ResponseEntity.created(newResource).body(RestResultGeneric.ofValue(s));
    }


    @GetMapping(value = "/headers/{id}", headers = {"myheader=myvalue", "otherheader=othervalue"})
    public ResponseEntity<?> getStudentWithHeader(@PathVariable("id") int id) {
        Student s = studentService.getStudent(id);
        if (s == null) {
            return ResponseEntity.badRequest().body(RestResultGeneric.ofError("Student with id: " + id + " not found"));
        }
        return ResponseEntity.ok(RestResultGeneric.ofValue(s));
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> getStudentByName(@PathVariable("name") String name) {
        List<Student> result = studentService.getByName(name);
        return ResponseEntity.ok(RestResultGeneric.ofValue(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        if (studentService.deleteStudent(id)) {
            return ResponseEntity.noContent().build();
        } else {
            RestResultGeneric<Student> rr = RestResultGeneric.ofError("Student with id " + id + " not found");
            return ResponseEntity.badRequest().body(rr);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        if (studentService.updateStudent(student)) {
            return ResponseEntity.noContent().build();
        } else {
            RestResultGeneric<Student> rr = RestResultGeneric.ofError("Student with id "
                    + student.getId() + " not found");
            return ResponseEntity.badRequest().body(rr);
        }
    }


//    //For doing JSR 303 validation.  This requires the spring-boot-starter-validation in the pom file.
//    //Also requires the qualifier with @Autowired, because apparently there are two validators in the file.
//    //Or, of course, just use @Resource with the name.
////    @Autowired
////    @Qualifier("mvcValidator")
//    @Resource(name = "mvcValidator")
//    private Validator validator;
//
//    /**
//     * Two ways to do Validation.
//     * One is to call the validator manually, as on the
//     * first line of this function.
//     *
//     * The other is to apply an @Valid annotation to the Student argument.  This will
//     * make Spring automatically do the validation before it calls this function,
//     * and return a MethodArgumentNotValid Exception on failure.  You can catch that
//     * exception in a @RestControllerAdvice class.
//     * See LastStopHandler for an example of @RestControllerAdvice.
//     *
//     * @param s
//     * @param ucb
//     * @param errors
//     * @return
//     */
//    @PostMapping
//    public ResponseEntity<?> createStudent(@RequestBody Student s,
//                                           UriComponentsBuilder ucb, Errors errors) {
////        validator.validate(s, errors);
//        if (errors.hasErrors()) {
//            List<String> errmsgs = errors.getFieldErrors().stream()
//                    .map(error -> "error:" + error.getField() + ": " + error.getDefaultMessage()
//                            + ", supplied Value: ")
//                    .collect(toList());
//            return ResponseEntity.badRequest().body(RestResultGeneric.ofError(errmsgs));
//        }
//        s = studentService.createStudent(s);
//        UriComponents uriComponents = ucb.path("/student/{id}")
//                .buildAndExpand(s.getId());
//
//        return ResponseEntity.created(uriComponents.toUri()).body(RestResultGeneric.ofValue(s));
//    }
}
