package ttl.larku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"ttl.larku"})
// make sure to scan local current class
// move it to root will be better
public class WfdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfdemoApplication.class, args);
    }

}

@Component
class MyRunner implements CommandLineRunner{

    @Autowired
    private StudentService ss;

    @Override
    public void run(String... args) throws Exception {
        List<Student> students = ss.getAllStudents();
    }
}
