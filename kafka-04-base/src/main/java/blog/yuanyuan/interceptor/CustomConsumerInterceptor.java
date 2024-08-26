package blog.yuanyuan.interceptor;


import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

/**
 * 自定义消费者拦截器
 */
public class CustomConsumerInterceptor implements ConsumerInterceptor<String, String> {

    /**
     * 在消费消息之前执行
     *
     * @param records
     * @return
     */
    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        System.out.println("onConsumer方法执行：" + records);
        return records;
    }

    /**
     * 消息拿到之后，提交offset之前执行该方法
     *
     * @param offsets
     */
    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        System.out.println("onCommit方法执行：" + offsets);
    }

    /**
     * 消费者关闭之前执行该方法
     */
    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
