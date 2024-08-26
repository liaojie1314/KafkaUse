package blog.yuanyuan.producer;


import blog.yuanyuan.model.User;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class EventProducer {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate2;

    public void sendEvent() {
        kafkaTemplate.send("hello", "hello kafka");
    }

    public void sendEvent2() {
        //通过构建器模式创建Message对象
        Message<String> message = MessageBuilder.withPayload("hello kafka")
                .setHeader(KafkaHeaders.TOPIC, "hello") //设置消息主题
                .build();
        kafkaTemplate.send(message);
    }

    public void sendEvent3() {
        //Headers里面是放一些信息（信息是key-value键值对），到时候消费者接收到消息后，可以通过headers来获取这些信息
        Headers headers = new RecordHeaders();
        headers.add("phone", "13723456789".getBytes(StandardCharsets.UTF_8));
        headers.add("id", "id-1".getBytes(StandardCharsets.UTF_8));
        //String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "hello",
                0,
                System.currentTimeMillis(),
                "k1",
                "hello kafka",
                headers
        );
        kafkaTemplate.send(record);
    }

    public void sendEvent4() {
        kafkaTemplate.send("hello", 0, System.currentTimeMillis(), "k2", "hello kafka");
    }

    public void sendEvent5() {
        //Integer partition, Long timestamp, K key, V data
        kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k3", "hello kafka");
    }

    public void sendEvent6() {
        CompletableFuture<SendResult<String, String>> completableFuture
                = kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k3", "hello kafka");

        //通过CompletableFuture来获取消息发送结果
        //1.阻塞等待方式
        try {
            SendResult<String, String> sendResult = completableFuture.get();
            if (sendResult.getRecordMetadata() != null) {
                //kafka服务器确认已经接收到了消息
                System.out.println("消息发送成功:" + sendResult.getRecordMetadata().toString());
            }
            System.out.println("producerRecord:" + sendResult.getProducerRecord());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEvent7() {
        CompletableFuture<SendResult<String, String>> completableFuture
                = kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k3", "hello kafka");

        //通过CompletableFuture来获取消息发送结果
        //2.非阻塞等待方式
        try {
            completableFuture.thenAccept((sendResult) -> {
                if (sendResult.getRecordMetadata() != null) {
                    //kafka服务器确认已经接收到了消息
                    System.out.println("消息发送成功:" + sendResult.getRecordMetadata().toString());
                }
                System.out.println("producerRecord:" + sendResult.getProducerRecord());
            }).exceptionally((t) -> {
                t.printStackTrace(System.err);
                return null;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEvent8() {
        User user = User.builder().id(1001).phone("13723456789").birthday(new Date()).build();
        //分区为空，让kafka自己决定把消息发送到哪个分区
        kafkaTemplate2.sendDefault(null, System.currentTimeMillis(), "k3", user);
    }

    public void sendEvent9() {
        User user = User.builder().id(1001).phone("13723456789").birthday(new Date()).build();
        //分区为空，让kafka自己决定把消息发送到哪个分区
        kafkaTemplate2.send("hello-topic", null, System.currentTimeMillis(), "k9", user);
    }

    public void sendEvent10() {
        User user = User.builder().id(1001).phone("13723456789").birthday(new Date()).build();
        //分区和key为空
        kafkaTemplate2.send("hello-topic", user);
    }
}
