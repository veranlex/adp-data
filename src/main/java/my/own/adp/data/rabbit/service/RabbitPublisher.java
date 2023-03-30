package my.own.adp.data.rabbit.service;

import my.own.adp.data.dto.IncomingMsgJournalDto;

import java.util.List;

//TODO Добавить документацию
public interface RabbitPublisher {

    void sendMessage(List<IncomingMsgJournalDto> message, String routingKey);

}
