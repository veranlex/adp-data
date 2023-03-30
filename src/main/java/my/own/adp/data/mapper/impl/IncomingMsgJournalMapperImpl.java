package my.own.adp.data.mapper.impl;

import my.own.adp.data.dto.IncomingMsgJournalDto;
import my.own.adp.data.mapper.IncomingMsgJournalMapper;
import org.springframework.stereotype.Service;
import my.own.adp.data.entity.IncomingMsgJournal;

import java.util.List;

@Service
public class IncomingMsgJournalMapperImpl implements IncomingMsgJournalMapper {

    @Override
    public IncomingMsgJournal toIncomingMsgJournal(IncomingMsgJournalDto incomingMsgJournalDto) {
        return new IncomingMsgJournal(incomingMsgJournalDto.getId(),
                incomingMsgJournalDto.getCreateDate(),
                incomingMsgJournalDto.getPayload(),
                incomingMsgJournalDto.getStatus(),
                incomingMsgJournalDto.getErrorMessage(),
                incomingMsgJournalDto.getSendCounter(),
                incomingMsgJournalDto.getSourceQueue());
    }

    @Override
    public IncomingMsgJournalDto toIncomingMsgJournalDto(IncomingMsgJournal incomingMsgJournal) {
        return new IncomingMsgJournalDto(incomingMsgJournal.getId(),
                incomingMsgJournal.getCreateDate(),
                incomingMsgJournal.getPayload(),
                incomingMsgJournal.getStatus(),
                incomingMsgJournal.getErrorMessage(),
                incomingMsgJournal.getSendCounter(),
                incomingMsgJournal.getSourceQueue());
    }

    @Override
    public List<IncomingMsgJournalDto> toListIncomingMsgJournalDto(List<IncomingMsgJournal> incomingMsgJournalList) {
        return incomingMsgJournalList.stream().map(this::toIncomingMsgJournalDto).toList();
    }
}
