package blog.yuanyuan.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    //采用监听器方式接受事件
    @KafkaListener(topics = {"hello"}, groupId = "hello-group")
    public void receive(String message) {
        System.out.println("接收到消息：" + message);
    }
}
