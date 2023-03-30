package my.own.adp.data.rabbit.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.dto.IncomingMsgJournalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import my.own.adp.data.entity.Status;
import my.own.adp.data.rabbit.conf.RabbitSpringProperties;
import my.own.adp.data.rabbit.service.RabbitService;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Data
public class RabbitServiceImpl implements RabbitService {
    private final RabbitPublisherImpl rabbitPublisher;
    private final RabbitSpringProperties springProperties;
    private final Map<String, String> routingKeyMap;

    @Autowired
    public RabbitServiceImpl(RabbitPublisherImpl rabbitPublisher,
                             RabbitSpringProperties springProperties) {
        this.rabbitPublisher = rabbitPublisher;
        this.springProperties = springProperties;
        routingKeyMap = Map.of(
                springProperties.rabbitmq().queues().personalQueue().queueCrewpv(), springProperties.rabbitmq().queues().personalQueue().crewpvRoutingkey(),
                springProperties.rabbitmq().queues().personalQueue().queueCommondata(), springProperties.rabbitmq().queues().personalQueue().commondataRroutingkey(),
                springProperties.rabbitmq().queues().personalQueue().queueId(), springProperties.rabbitmq().queues().personalQueue().idRoutingkey(),
                springProperties.rabbitmq().queues().personalQueue().queueMed(), springProperties.rabbitmq().queues().personalQueue().medRoutingkey(),
                springProperties.rabbitmq().queues().personalQueue().queueVac(), springProperties.rabbitmq().queues().personalQueue().vacRoutingkey(),
                springProperties.rabbitmq().queues().personalQueue().queueVacationleftovers(), springProperties.rabbitmq().queues().personalQueue().vacationleftoversRoutingkey(),
                springProperties.rabbitmq().queues().personalQueue().queueHrmd(), springProperties.rabbitmq().queues().personalQueue().hrmdRoutingkey()
        );
    }


    @Override
    public void sendAndReceive(List<IncomingMsgJournalDto> listMessages, String queueName) {
        listMessages.forEach(m -> {
            m.setSendCounter(m.getSendCounter() + 1);
            m.setStatus(Status.PROCESSING.getMessage());
        });
        rabbitPublisher.sendMessage(listMessages, routingKeyMap.get(queueName));
    }
}
