package my.own.adp.data.service;

import java.util.List;

//TODO Добавить документацию
public interface RabbitMsgJournalService {

    void sendAndReceiveMsgJournalByIds(List<String> ids);

    void sendAndReceiveMsgJournalByStatuses(List<String> statuses);


}
