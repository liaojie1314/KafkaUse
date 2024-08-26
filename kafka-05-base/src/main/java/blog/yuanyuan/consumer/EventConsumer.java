package blog.yuanyuan.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = {"topicA"}, groupId = "aGroup")
    @SendTo(value = "topicB")
    public String onEventA(ConsumerRecord<String, String> record) {
        System.out.println("消费A消息：" + record);
        return record.value() + "--forward message";
    }


    @KafkaListener(topics = {"topicB"}, groupId = "bGroup")
    public void onEventB(ConsumerRecord<String, String> record) {
        System.out.println("消费B消息：" + record);
    }
}
