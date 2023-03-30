package my.own.adp.data.ibm.switcher.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.stereotype.Service;
import my.own.adp.data.ibm.conf.IbmJmsListenerEndpointRegistrar;
import my.own.adp.data.ibm.queues.container.QueuesContainer;
import my.own.adp.data.ibm.msg.MessageService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class SwitcherServiceImpl implements SwitcherService {
    private final List<IbmJmsListenerEndpointRegistrar> registrars;
    private final JmsListenerEndpointRegistry registry;
    private final MessageService messageService;
    private final QueuesContainer queuesContainer;


    @Override
    public void changeListenerQueues(List<String> queues) {
        registry.getListenerContainerIds()
                .forEach(id -> registry.getListenerContainer(id).stop());
        queuesContainer.invalidateAll();

        log.info("прослушивание всех очередей отменено");

        registrars.forEach(registrar -> registrar.getQueues()
                .forEach(queueName -> {
                            if (!queues.contains(queueName)) {
                                queuesContainer.disable(queueName);
                                return;
                            }
                            var endpoint = new SimpleJmsListenerEndpoint();
                            endpoint.setId(queueName + "-" + UUID.randomUUID());
                            endpoint.setDestination(queueName);
                            endpoint.setConcurrency("1-1");

                            endpoint.setMessageListener(message ->
                                    messageService.handleMessage(queueName, message)
                            );
                            registrar.registerEndpoint(endpoint);
                            queuesContainer.enable(queueName);
                            log.info("старт прослушивания очереди={}", queueName);

                        }
                )
        );
    }
}
