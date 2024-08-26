package blog.yuanyuan.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = {"myTopic"}, groupId = "myGroup",
            concurrency = "3", containerFactory = "kafkaListenerContainerFactory") // 3个线程即3个消费者
    public void onEventA(ConsumerRecord<String, String> record) {
        System.out.println(Thread.currentThread().getId() + " --> 消费消息：" + record);
    }
}
