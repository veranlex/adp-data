package my.own.adp.data.service;

import my.own.adp.data.dto.IncomingMsgJournalDto;
import my.own.adp.data.model.IncomingMsgJournalIdsRequest;
import my.own.adp.data.model.IncomingMsgJournalStatusesRequest;
import my.own.adp.data.entity.IncomingMsgJournal;

import java.util.List;

//TODO Добавить документацию
public interface IncomingMsgJournalService {

    void createNew(IncomingMsgJournalDto incomingMsgJournalDto);

    List<IncomingMsgJournalDto> getMsgJournalDtoByIds(IncomingMsgJournalIdsRequest messages);

    List<IncomingMsgJournalDto> getMsgJournalDtoByStatuses(IncomingMsgJournalStatusesRequest messages);

    List<IncomingMsgJournal> getEntitiesByIds(List<String> ids);

    List<IncomingMsgJournal> getEntitiesByStatuses(List<String> statuses);

    void updateStatusOnProcessing(IncomingMsgJournalDto incomingMsgJournalDto, String status);



}
