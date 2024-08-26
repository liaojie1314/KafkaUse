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
        for (int i = 0; i < 2; i++) {
            User user = User.builder().id(i).phone("13723456789" + i).birthday(new Date()).build();
            String userJson = JSONUtils.toJson(user);
            kafkaTemplate.send("clusterTopic", "k" + i, userJson);
        }
    }
}
