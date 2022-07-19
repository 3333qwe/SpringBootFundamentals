package ttl.larku.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

@Configuration
@ComponentScan({"ttl.larku.service", "ttl.larku.dao", "ttl.larku.misc"})
@PropertySource({"classpath:/larkUContext.properties"})
public class LarkUConfig {

    @Bean
    public BaseDAO<Student> studentDAO(){
        return new InMemoryStudentDAO();
    }

    @Bean
    public StudentService studentService(){
        var studentService = new StudentService();
        studentService.setStudentDAO(studentDAO());
        return studentService;
    }

}
