package blog.yuanyuan.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = {"clusterTopic"}, groupId = "clusterGroup")
    public void onEvent(ConsumerRecord<String, String> record) {
        System.out.println("消费消息：" + record);
    }
}
