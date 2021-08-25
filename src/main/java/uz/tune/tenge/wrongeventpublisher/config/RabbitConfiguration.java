package uz.tune.tenge.wrongeventpublisher.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfiguration {

//    public static final String exchangeForDelayed = "delayedExchange";
//
//    public static final String queueForDelayed = "delayedQueue";

    //Temp
    public static final String exchangeForDirect = "exchangeDirect";

    public static final String queueForDirect = "queueDirect";

    @Bean("customConverter")
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
    settings for delayed messages
     */
    /*
    @Bean("customDelayedExchange")
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(exchangeForDelayed, "x-delayed-message", true, false, args);
    }

    @Bean("customDelayedQueue")
    public Queue queueDelayed() {
        return new Queue(queueForDelayed, true);
    }

    @Bean
    public Binding delayedBinding(
            @Qualifier("customDelayedExchange") Exchange exchangeForDelayed,
            @Qualifier("customDelayedQueue") Queue queueForDelayed) {
        return BindingBuilder
                .bind(queueForDelayed)
                .to(exchangeForDelayed)
                .with(RabbitConfiguration.queueForDelayed)
                .noargs();
    }
     */

    @Bean("customExchangeDirect")
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeForDirect, true, false);
    }

    @Bean("customQueueDirect")
    public Queue queueDirect() {
        return new Queue(queueForDirect, true);
    }

    @Bean
    public Binding binding(
            @Qualifier("customExchangeDirect") DirectExchange directExchange,
            @Qualifier("customQueueDirect") Queue queueDirect) {
        return BindingBuilder
                .bind(queueDirect)
                .to(directExchange)
                .with(queueForDirect);
    }


}
