package blog.yuanyuan.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventConsumer {

    @KafkaListener(topics = {"batchTopic"}, groupId = "batchGroup")
    public void onEvent(List<ConsumerRecord<String, String>> records) {
        System.out.println("批量消费，records.size: " + records.size() + ", records: " + records);
    }
}
