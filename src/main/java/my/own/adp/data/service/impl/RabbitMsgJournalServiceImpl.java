package my.own.adp.data.service.impl;

import lombok.RequiredArgsConstructor;
import my.own.adp.data.rabbit.service.RabbitService;
import org.springframework.stereotype.Service;
import my.own.adp.data.entity.IncomingMsgJournal;
import my.own.adp.data.mapper.IncomingMsgJournalMapper;
import my.own.adp.data.rabbit.conf.RabbitSpringProperties;
import my.own.adp.data.service.RabbitMsgJournalService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMsgJournalServiceImpl implements RabbitMsgJournalService {

    private final IncomingMsgJournalServiceImpl journalServiceImpl;
    private final RabbitService rabbitService;
    private final IncomingMsgJournalMapper incomingMsgJournalMapper;

    @Override
    public void sendAndReceiveMsgJournalByIds(List<String> ids) {
        List<IncomingMsgJournal> msgJournalList = journalServiceImpl.getEntitiesByIds(ids);
        rabbitService.sendAndReceive(incomingMsgJournalMapper.toListIncomingMsgJournalDto(msgJournalList), "");
    }

    @Override
    public void sendAndReceiveMsgJournalByStatuses(List<String> statuses) {
        List<IncomingMsgJournal> msgJournalList = journalServiceImpl.getEntitiesByStatuses(statuses);
        rabbitService.sendAndReceive(incomingMsgJournalMapper.toListIncomingMsgJournalDto(msgJournalList), "");
    }

}
