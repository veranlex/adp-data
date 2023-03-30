package my.own.adp.data.rabbit.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import my.own.adp.data.dto.IncomingMsgJournalDto;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Service;
import my.own.adp.data.entity.Status;
import my.own.adp.data.rabbit.service.RabbitReceiver;
import my.own.adp.data.service.IncomingMsgJournalService;

import java.util.List;

@Service
@Data
public class RabbitReceiverImpl implements RabbitReceiver {
    private final IncomingMsgJournalService incomingMsgJournalService;
    private static TypeReference<List<IncomingMsgJournalDto>> typeReference = new TypeReference<>() {};

    @Override
    public void receive(List<IncomingMsgJournalDto> listMessages, Object response) {
        if (response instanceof AmqpException amqpException) {
            handleMessage(listMessages, amqpException.getMessage());
        } else if (response == null) {
            handleMessage(listMessages, "Сервис обработчик не ответил");
        } else {
            listMessages = new ObjectMapper().convertValue(response, typeReference);
        }
        listMessages.forEach(message -> incomingMsgJournalService.updateStatusOnProcessing(message, message.getStatus()));
    }

    @Override
    public void handleMessage(List<IncomingMsgJournalDto> listMessages, String exception) {
        listMessages.forEach(m -> {
            m.setStatus(Status.SEND_ERROR.getMessage());
            m.setErrorMessage(exception);
        });
    }
}
