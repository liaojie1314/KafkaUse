package blog.yuanyuan;

import blog.yuanyuan.producer.EventProducer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaBaseApplicationTests {

    @Resource
    private EventProducer eventProducer;


    @Test
    void test01() {
        eventProducer.sendEvent();
    }

}
