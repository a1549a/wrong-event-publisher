package uz.tune.tenge.wrongeventpublisher.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.tune.tenge.wrongeventpublisher.config.RabbitConfiguration;

import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleLockRabbitMQPublisher extends AbstractLockRabbitMQPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public SimpleLockRabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publish(String userId, String deviceId, String ip, WRONG_EVENT wrongEvent) {

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("deviceId", deviceId);
        map.put("ip", ip);
        map.put("service", getService().name());
        map.put("wrongEvent", wrongEvent.name());

        try {
            String body = objectMapper.writeValueAsString(
                    map
            );
            Message message = new Message(body.getBytes());
            message.getMessageProperties().setContentType("application/json");
            rabbitTemplate.send(
                    RabbitConfiguration.exchangeForDirect,
                    RabbitConfiguration.queueForDirect,
                    message);

        } catch (Throwable e) {
            logger.error(e);
        }
    }

    @Autowired
    @Override
    protected void setService(@Value("${spring.application.name}") String service) {
        setServiceOrUnknown(service);
    }
}
