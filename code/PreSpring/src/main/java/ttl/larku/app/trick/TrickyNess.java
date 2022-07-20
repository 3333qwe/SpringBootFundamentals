package ttl.larku.app.trick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author whynot
 */
interface Trick {
    public void doTrick();
}

@Component
//@Primary
//@Profile("dev")
@Qualifier("us-east")
class Trick1 implements Trick {
    @Override
    public void doTrick() {
        System.out.println("HandStand");
    }
}

@Component
//@Profile("prod")
@Qualifier("us-east")
class Trick2 implements Trick {
    @Override
    public void doTrick() {
        System.out.println("CartWheel");
    }
}

@Component
//@Profile("prod")
@Qualifier("us-west")
class Trick3 implements Trick {
    @Override
    public void doTrick() {
        System.out.println("CartWheel");
    }
}

@Component
class Circus {
    //    @Autowired(required = false)
//    @Qualifier("trick2")
//    @Resource(name = "trick2")
    private Trick trick;

    @Autowired(required = false)
    private List<Trick> tricks = new ArrayList<>();

    @Autowired
    private Map<String, Trick> mapOfTricks = new HashMap<>();


    public void startShow() {
        tricks.forEach(Trick::doTrick);
//        trick.doTrick();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().addActiveProfile("dev");
        context.scan("ttl.larku.app.trick");
        context.refresh();

        Circus circus = context.getBean("circus", Circus.class);
        circus.startShow();

    }
}
