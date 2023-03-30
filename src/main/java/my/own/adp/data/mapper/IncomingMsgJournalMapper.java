package my.own.adp.data.mapper;

import my.own.adp.data.dto.IncomingMsgJournalDto;
import my.own.adp.data.entity.IncomingMsgJournal;

import java.util.List;

//TODO Добавить документацию
public interface IncomingMsgJournalMapper {

    IncomingMsgJournal toIncomingMsgJournal(IncomingMsgJournalDto incomingMsgJournalDto);

    IncomingMsgJournalDto toIncomingMsgJournalDto(IncomingMsgJournal incomingMsgJournal);

    List<IncomingMsgJournalDto> toListIncomingMsgJournalDto(List<IncomingMsgJournal> incomingMsgJournalList);

}
