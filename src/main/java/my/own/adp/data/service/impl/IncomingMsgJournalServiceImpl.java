package my.own.adp.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.dto.IncomingMsgJournalDto;
import my.own.adp.data.model.IncomingMsgJournalIdsRequest;
import my.own.adp.data.model.IncomingMsgJournalStatusesRequest;
import my.own.adp.data.repository.IncomingMsgJournalRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import my.own.adp.data.entity.IncomingMsgJournal;
import my.own.adp.data.handler.exception.IncomingMsgJournalNotFoundException;
import my.own.adp.data.mapper.IncomingMsgJournalMapper;
import my.own.adp.data.service.IncomingMsgJournalService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomingMsgJournalServiceImpl implements IncomingMsgJournalService {

    private final IncomingMsgJournalRepository incomingMsgJournalRepository;
    private final IncomingMsgJournalMapper incomingMsgJournalMapper;

    @Override
    @Transactional
    public void createNew(IncomingMsgJournalDto incomingMsgJournalDto) {
        IncomingMsgJournal incomingMsgJournal = incomingMsgJournalMapper.toIncomingMsgJournal(incomingMsgJournalDto);
        incomingMsgJournalRepository.save(incomingMsgJournal);
        log.info("Сообщение: {} с id: {} сохранено в БД", incomingMsgJournalDto.getPayload().length(), incomingMsgJournalDto.getId());
    }

    @Override
    @SneakyThrows
    public List<IncomingMsgJournalDto> getMsgJournalDtoByIds(IncomingMsgJournalIdsRequest message) {
        List<IncomingMsgJournal> msgJournalList = incomingMsgJournalRepository.getMsgJournalByIds(message.getIds(), PageRequest.of(message.getPageNum(), message.getPageSize()));
        List<IncomingMsgJournalDto> listMsgJournalDto = msgJournalList.stream()
                .map(incomingMsgJournalMapper::toIncomingMsgJournalDto)
                .toList();
        if (listMsgJournalDto.isEmpty()) {
            throw new IncomingMsgJournalNotFoundException("Данные не найдены");
        }
        return listMsgJournalDto;
    }

    @Override
    @SneakyThrows
    public List<IncomingMsgJournalDto> getMsgJournalDtoByStatuses(IncomingMsgJournalStatusesRequest message) {
        List<IncomingMsgJournal> msgJournalList = incomingMsgJournalRepository.getMsgJournalByStatuses(message.getStatuses(), PageRequest.of(message.getPageNum(), message.getPageSize()));
        List<IncomingMsgJournalDto> listMsgJournalDto = msgJournalList.stream()
                .map(incomingMsgJournalMapper::toIncomingMsgJournalDto)
                .toList();
        if (listMsgJournalDto.isEmpty()) {
            throw new IncomingMsgJournalNotFoundException("Данные не найдены");
        }
        return listMsgJournalDto;
    }

    @Override
    @SneakyThrows
    public List<IncomingMsgJournal> getEntitiesByIds(List<String> ids) {
        List<IncomingMsgJournal> msgJournalList = incomingMsgJournalRepository.getMsgJournalByIds(ids, Pageable.unpaged());
        if (msgJournalList.isEmpty()) {
            throw new IncomingMsgJournalNotFoundException("Данные не найдены");
        }
        return msgJournalList;
    }

    @Override
    @SneakyThrows
    public List<IncomingMsgJournal> getEntitiesByStatuses(List<String> statuses) {
        List<IncomingMsgJournal> msgJournalList = incomingMsgJournalRepository.getMsgJournalByStatuses(statuses, Pageable.unpaged());
        if (msgJournalList.isEmpty()) {
            throw new IncomingMsgJournalNotFoundException("Данные не найдены");
        }
        return msgJournalList;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateStatusOnProcessing(IncomingMsgJournalDto incomingMsgJournalDto, String status) {
        IncomingMsgJournal message = incomingMsgJournalMapper.toIncomingMsgJournal(incomingMsgJournalDto);
        try {
            incomingMsgJournalRepository.updateStatus(
                    message.getId(),
                    status,
                    message.getErrorMessage(),
                    message.getSendCounter() + 1);
            log.info("Сообщение с id: {} обновлено в БД", message.getId());
        } catch (Exception e) {
            log.info("Ошибка при попытке сохранения id " + message.getId() + " сообщения: " + e.getMessage() + " " + e.getMessage());
        }
    }
}
