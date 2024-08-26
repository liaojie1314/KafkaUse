package blog.yuanyuan.consumer;


import blog.yuanyuan.model.User;
import blog.yuanyuan.util.JSONUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    //@KafkaListener(topics = {"helloTopic"}, groupId = "helloGroup")
    public void onEvent(@Payload String event,
                        @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                        ConsumerRecord<String, String> record) {
        System.out.println("接收到事件：" + event + ", topic:" + topic + ", partition:" + partition);
        System.out.println("record:" + record.toString());
    }

    //@KafkaListener(topics = {"helloTopic"}, groupId = "helloGroup")
    public void onEvent2(String userJson,
                         @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                         ConsumerRecord<String, String> record) {
        User user = JSONUtils.toBean(userJson, User.class);
        System.out.println("接收到事件：" + user + ", topic:" + topic + ", partition:" + partition);
        System.out.println("record:" + record.toString());
    }

    //@KafkaListener(topics = {"${kafka.topic.name}"}, groupId = "${kafka.consumer.group}")
    public void onEvent3(String userJson,
                         @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                         ConsumerRecord<String, String> record) {
        User user = JSONUtils.toBean(userJson, User.class);
        System.out.println("接收到事件：" + user + ", topic:" + topic + ", partition:" + partition);
        System.out.println("record:" + record.toString());
    }

    //@KafkaListener(topics = {"${kafka.topic.name}"}, groupId = "${kafka.consumer.group}")
    public void onEvent4(String userJson,
                         @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                         ConsumerRecord<String, String> record,
                         Acknowledgment ack) {
        try {
            User user = JSONUtils.toBean(userJson, User.class);
            System.out.println("接收到事件：" + user + ", topic:" + topic + ", partition:" + partition);
            System.out.println("record:" + record.toString());

            ack.acknowledge(); //手动确认消息，告诉kafka服务器，该消息我已经收到，默认情况下，kafka是自动确认消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(groupId = "${kafka.consumer.group}",
            topicPartitions = {
                    @TopicPartition(
                            topic = "${kafka.topic.name}",
                            partitions = {"0", "1", "2"},
                            partitionOffsets = {
                                    @PartitionOffset(partition = "3", initialOffset = "3"),
                                    @PartitionOffset(partition = "4", initialOffset = "3")
                            })
            })
    public void onEvent5(String userJson,
                         @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                         ConsumerRecord<String, String> record,
                         Acknowledgment ack) {
        User user = JSONUtils.toBean(userJson, User.class);
        System.out.println("接收到事件：" + user + ", topic:" + topic + ", partition:" + partition);
        System.out.println("record:" + record.toString());
        ack.acknowledge(); //手动确认消息，告诉kafka服务器，该消息我已经收到，默认情况下，kafka是自动确认消息
    }
}
