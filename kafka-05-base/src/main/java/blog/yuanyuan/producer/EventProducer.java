package blog.yuanyuan.producer;


import blog.yuanyuan.model.User;
import blog.yuanyuan.util.JSONUtils;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class EventProducer {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent() {
        User user = User.builder().id(100).phone("13723456789").birthday(new Date()).build();
        String userJson = JSONUtils.toJson(user);
        kafkaTemplate.send("topicA", userJson);
    }
}
