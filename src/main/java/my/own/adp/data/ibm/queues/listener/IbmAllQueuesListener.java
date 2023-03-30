package my.own.adp.data.ibm.queues.listener;

import lombok.AllArgsConstructor;
import my.own.adp.data.ibm.msg.MessageService;
import my.own.adp.data.ibm.queues.container.QueuesContainer;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import my.own.adp.data.ibm.conf.IbmJmsListenerEndpointRegistrar;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

//@Component
@AllArgsConstructor
public class IbmAllQueuesListener {

    private final List<IbmJmsListenerEndpointRegistrar> registrars;
    private final MessageService messageService;
    private final QueuesContainer queuesContainer;


    @PostConstruct
    public void init() {
        configureJmsListeners(registrars);
    }

    public void configureJmsListeners(List<IbmJmsListenerEndpointRegistrar> registrars) {
        registrars.forEach(registrar -> registrar.getQueues()
                .forEach(queueName -> {
                            var endpoint = new SimpleJmsListenerEndpoint();
                            endpoint.setId(queueName + "-" + UUID.randomUUID());
                            endpoint.setDestination(queueName);
                            endpoint.setConcurrency("1-1");

                            endpoint.setMessageListener(message ->
                                    messageService.handleMessage(queueName, message)
                            );
                            registrar.registerEndpoint(endpoint);
                            queuesContainer.enable(queueName);
                        }
                )
        );
    }
}