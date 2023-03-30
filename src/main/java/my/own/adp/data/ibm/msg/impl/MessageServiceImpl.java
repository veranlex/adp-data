package my.own.adp.data.ibm.msg.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.dto.IncomingMsgJournalDto;
import my.own.adp.data.event.IBMEvent;
import my.own.adp.data.ibm.model.IbmPayload;
import my.own.adp.data.ibm.msg.MessageService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import my.own.adp.data.entity.Status;
import my.own.adp.data.ibm.file.FileWriterServiceImpl;
import my.own.adp.data.mapper.IbmPayloadMapper;
import my.own.adp.data.service.IncomingMsgJournalService;

import javax.jms.JMSException;
import javax.jms.Message;
import java.sql.Timestamp;

@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private static final String DEFAULT_ERROR_MESSAGE = "OK";
    private static final int DEFAULT_SEND_COUNTER = 0;

    private final IbmPayloadMapper ibmPayloadMapper;
    private final FileWriterServiceImpl fileWriterServiceImpl;
    private final IncomingMsgJournalService incomingMsgJournalService;
    private final ApplicationEventPublisher publisher;

    @Override
    public void handleMessage(String queueName, Message message) {
        try {
            log.info("Получено новое сообщение из очереди={}", queueName);
            IbmPayload ibmPayload = ibmPayloadMapper.messageToIbmPayLoad(queueName, message);
            fileWriterServiceImpl.writeMessageFromIBMMQToFile(queueName, ibmPayload.getJmsMessageID(), ibmPayload.getContent());
            log.info("Начат процесс сохранения в БД сообщения с id: {}", ibmPayload.getJmsMessageID());
            saveMessageInDataBase(queueName, ibmPayload.getJmsMessageID(), ibmPayload.getContent());
            message.acknowledge();
            publisher.publishEvent(new IBMEvent(
                    IncomingMsgJournalDto.builder()
                            .id(ibmPayload.getJmsMessageID())
                            .createDate(new Timestamp(System.currentTimeMillis()))
                            .payload(ibmPayload.getContent())
                            .status(Status.NEW.getMessage())
                            .errorMessage(DEFAULT_ERROR_MESSAGE)
                            .sendCounter(DEFAULT_SEND_COUNTER)
                            .sourceQueue(queueName)
                            .build(), queueName));
        } catch (JMSException e) {
            log.error("Не удалось обработать сообщение из очереди {} IBMMQ", queueName);
            throw new RuntimeException();
        }
    }

    private void saveMessageInDataBase(String queueName, String jmsMessageID, String content) {
        var incomingMsgJournalDto = IncomingMsgJournalDto.builder();
        try {
            incomingMsgJournalService.createNew(
                    incomingMsgJournalDto
                            .id(jmsMessageID)
                            .createDate(new Timestamp(System.currentTimeMillis()))
                            .payload(content)
                            .status(Status.NEW.getMessage())
                            .errorMessage(DEFAULT_ERROR_MESSAGE)
                            .sendCounter(DEFAULT_SEND_COUNTER)
                            .sourceQueue(queueName)
                            .build()
            );
            log.info("Закончен процесс сохранения файла в БД");
        } catch (Exception e) {
            log.error(String.format("Не удалось сохранить сообщение. очередь: %s, id сообщения: %s размер сообщения: %s", queueName, jmsMessageID, content.length()), e);
            throw new RuntimeException(e);
        }
    }
}

