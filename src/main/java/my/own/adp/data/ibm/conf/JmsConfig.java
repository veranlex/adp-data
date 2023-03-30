package my.own.adp.data.ibm.conf;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsConstants;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import my.own.adp.data.ibm.conf.props.IbmJmsProperties;

import javax.jms.JMSException;
import javax.jms.Session;
import java.util.List;

@Getter
@Configuration
public class JmsConfig {


    @Bean
    JmsConnectionFactory jmsCrewConnectionFactory(IbmJmsProperties properties) throws JMSException {
        return createFactory(
                properties.mq().host(),
                properties.mq().port(),
                properties.mq().queueManager(),
                properties.mq().user(),
                properties.mq().password(),
                properties.mq().crewcn().channel(),
                properties.mq().crewcn().queues()
        );
    }


    @Bean
    JmsConnectionFactory jmsEcoConnectionFactory(IbmJmsProperties properties) throws JMSException {
        return createFactory(
                properties.mq().host(),
                properties.mq().port(),
                properties.mq().queueManager(),
                properties.mq().user(),
                properties.mq().password(),
                properties.mq().eco().channel(),
                properties.mq().eco().queues()
        );
    }


    @Bean
    CachingConnectionFactory cachingEcoConnectionFactory(JmsConnectionFactory jmsEcoConnectionFactory) {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setSessionCacheSize(1);
        factory.setTargetConnectionFactory(jmsEcoConnectionFactory);
        factory.setReconnectOnException(true);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    CachingConnectionFactory cachingCrewConnectionFactory(JmsConnectionFactory jmsCrewConnectionFactory) {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setSessionCacheSize(1);
        factory.setTargetConnectionFactory(jmsCrewConnectionFactory);
        factory.setReconnectOnException(true);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    JmsTemplate jmsEcoTemplate(CachingConnectionFactory cachingEcoConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(cachingEcoConnectionFactory);
        return jmsTemplate;
    }

    @Bean
    JmsTemplate jmsCrewTemplate(CachingConnectionFactory cachingCrewConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(cachingCrewConnectionFactory);
        return jmsTemplate;
    }

    @Bean
    JmsListenerEndpointRegistry jmsListenerEndpointRegistry() {
        return new JmsListenerEndpointRegistry();
    }

    @Bean
    public IbmJmsListenerEndpointRegistrar ibmJmsListenerEndpointRegistrarEco(JmsListenerEndpointRegistry jmsListenerEndpointRegistry,
                                                                              JmsConnectionFactory jmsEcoConnectionFactory) {
        return createRegistrar(jmsListenerEndpointRegistry, jmsEcoConnectionFactory);
    }

    @Bean
    public IbmJmsListenerEndpointRegistrar ibmJmsListenerEndpointRegistrarCrew(JmsListenerEndpointRegistry jmsListenerEndpointRegistry,
                                                                               JmsConnectionFactory jmsCrewConnectionFactory) {
        return createRegistrar(jmsListenerEndpointRegistry, jmsCrewConnectionFactory);
    }

    private static IbmJmsListenerEndpointRegistrar createRegistrar(JmsListenerEndpointRegistry jmsListenerEndpointRegistry, JmsConnectionFactory jmsCrewConnectionFactory) {
        var registrar = new IbmJmsListenerEndpointRegistrar();
        registrar.setQueues((List<String>) jmsCrewConnectionFactory.get("queues"));
        registrar.setEndpointRegistry(jmsListenerEndpointRegistry);

        var containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setConnectionFactory(jmsCrewConnectionFactory);
        containerFactory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        registrar.setContainerFactory(containerFactory);
        return registrar;
    }


    private static JmsConnectionFactory createFactory(String host,
                                                      int port,
                                                      String queueManager,
                                                      String user,
                                                      String password,
                                                      String channel,
                                                      List<String> queues) throws JMSException {
        var ff = JmsFactoryFactory.getInstance(JmsConstants.WMQ_PROVIDER);
        var factory = ff.createConnectionFactory();
        factory.setObjectProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        factory.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
        factory.setObjectProperty(WMQConstants.WMQ_PORT, port);
        factory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManager);
        factory.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
        factory.setStringProperty(WMQConstants.USERID, user);
        factory.setStringProperty(WMQConstants.PASSWORD, password);
        factory.setObjectProperty("queues", queues);
        return factory;
    }
}