package blog.yuanyuan.config;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class CustomProducerInterceptor implements ProducerInterceptor<String, Object> {

    /**
     * 发送消息时，会先调用该方法，对消息进行拦截，可以在该方法中做消息的拦截处理，记录日志等操作
     *
     * @param producerRecord
     * @return ProducerRecord<String, Object>
     */
    @Override
    public ProducerRecord<String, Object> onSend(ProducerRecord producerRecord) {
        System.out.println("拦截器拦截到消息：" + producerRecord.toString());
        return producerRecord;
    }

    /**
     * 服务器收到消息后的一个确认
     * @param recordMetadata
     * @param e
     */
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (recordMetadata != null) {
            System.out.println("服务器收到消息：" + recordMetadata.offset());
        } else {
            System.out.println("消息发送失败了，exception=" + e.getMessage());
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
