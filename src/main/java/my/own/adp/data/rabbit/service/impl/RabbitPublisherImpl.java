package my.own.adp.data.rabbit.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.dto.IncomingMsgJournalDto;
import my.own.adp.data.entity.Status;
import my.own.adp.data.service.IncomingMsgJournalService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import my.own.adp.data.rabbit.service.RabbitPublisher;

import java.util.List;

@Service
@Data
@Slf4j
public class RabbitPublisherImpl implements RabbitPublisher {

    private final RabbitTemplate template;
    private final RabbitReceiverImpl rabbitReceiver;
    private final IncomingMsgJournalService incomingMsgJournalService;

    @Override
    public void sendMessage(List<IncomingMsgJournalDto> message, String routingKey) {
        Object response;
        log.info("Посылаем сообщение, используя routing-key {}", routingKey);
        try {
            message.forEach(incomingMsgJournalDto -> incomingMsgJournalService.updateStatusOnProcessing(incomingMsgJournalDto, Status.PROCESSING.getMessage()));
            response = template.convertSendAndReceive(routingKey, message);
            log.info("Получили успешный ответ. Routing-key {}", routingKey);
        } catch (AmqpException e) {
            log.info("Исключение произошло при попытке отправки сообщения. Routing-key " + routingKey);
            response = e;
        }
        rabbitReceiver.receive(message, response);
    }

}
