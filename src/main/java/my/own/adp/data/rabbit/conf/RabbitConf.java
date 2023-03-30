package my.own.adp.data.rabbit.conf;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConf {

    private final RabbitSpringProperties rabbitSpringProperties;

    @Autowired
    public RabbitConf(RabbitSpringProperties rabbitSpringProperties) {
        this.rabbitSpringProperties = rabbitSpringProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitSpringProperties.rabbitmq().host());
        cachingConnectionFactory.setPort(Integer.parseInt(rabbitSpringProperties.rabbitmq().port()));
        cachingConnectionFactory.setUsername(rabbitSpringProperties.rabbitmq().username());
        cachingConnectionFactory.setPassword(rabbitSpringProperties.rabbitmq().password());
        cachingConnectionFactory.setVirtualHost(rabbitSpringProperties.rabbitmq().virtualHost());
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(rabbitSpringProperties.rabbitmq().directExchange());
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue queueCrewpv() {
        return new Queue(rabbitSpringProperties.rabbitmq().queues().personalQueue().queueCrewpv());
    }

    @Bean
    public Queue queueCommondata() {
        return new Queue(rabbitSpringProperties.rabbitmq().queues().personalQueue().queueCommondata());
    }

    @Bean
    public Queue queueId() {
        return new Queue(rabbitSpringProperties.rabbitmq().queues().personalQueue().queueId());
    }

    @Bean
    public Queue queueMed() {
        return new Queue(rabbitSpringProperties.rabbitmq().queues().personalQueue().queueMed());
    }

    @Bean
    public Queue queueVac() {
        return new Queue(rabbitSpringProperties.rabbitmq().queues().personalQueue().queueVac());
    }

    @Bean
    public Queue queueVacationleftovers() {
        return new Queue(rabbitSpringProperties.rabbitmq().queues().personalQueue().queueVacationleftovers());
    }

    @Bean
    public Queue queueHrmd() {
        return new Queue(rabbitSpringProperties.rabbitmq().queues().personalQueue().queueHrmd());
    }

    @Bean
    DirectExchange exchangeIbmEvent() {
        return new DirectExchange(rabbitSpringProperties.rabbitmq().directExchange(), true, false);
    }

    @Bean
    Binding bindingQueueCrewpv() {
        return BindingBuilder.bind(queueCrewpv()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().personalQueue().crewpvRoutingkey());
    }

    @Bean
    Binding bindingQueueCommondata() {
        return BindingBuilder.bind(queueCommondata()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().personalQueue().commondataRroutingkey());
    }

    @Bean
    Binding bindingQueueId() {
        return BindingBuilder.bind(queueId()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().personalQueue().idRoutingkey());
    }

    @Bean
    Binding bindingQueueMed() {
        return BindingBuilder.bind(queueMed()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().personalQueue().medRoutingkey());
    }

    @Bean
    Binding bindingQueueVac() {
        return BindingBuilder.bind(queueVac()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().personalQueue().vacRoutingkey());
    }

    @Bean
    Binding bindingQueueVacationleftovers() {
        return BindingBuilder.bind(queueVacationleftovers()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().personalQueue().vacationleftoversRoutingkey());
    }

    @Bean
    Binding bindingQueueHrmd() {
        return BindingBuilder.bind(queueHrmd()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().personalQueue().hrmdRoutingkey());
    }

    @Bean
    Binding bindingIbmEventToCommonExchangeScheduler() {
        return BindingBuilder.bind(queueCrewpv()).to(exchangeIbmEvent()).with(rabbitSpringProperties.rabbitmq().queues().schedulerQueue().routingKeyForScheduler());
    }

}
