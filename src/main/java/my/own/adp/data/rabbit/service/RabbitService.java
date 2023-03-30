package my.own.adp.data.rabbit.service;

import my.own.adp.data.dto.IncomingMsgJournalDto;

import java.util.List;
//TODO Добавить документацию
public interface RabbitService {

    void sendAndReceive(List<IncomingMsgJournalDto> message, String queueName);

}
