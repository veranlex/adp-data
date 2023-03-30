package my.own.adp.data.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.ibm.model.IbmPayload;
import my.own.adp.data.mapper.IbmPayloadMapper;
import org.springframework.stereotype.Service;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class IbmPayloadMapperImpl implements IbmPayloadMapper {


    @Override
    public IbmPayload messageToIbmPayLoad(String queueName, Message message) {
        var jmsMessageID = getJMSMessageID(message);
        var content= getPayloadFromMessage(queueName, jmsMessageID, message);
        return new IbmPayload(jmsMessageID, content);
    }

    private String getPayloadFromMessage(String queueName, String jmsMessageID, Message message) {
        var content = "";
        try {
            if (message instanceof BytesMessage) {
                byte[] bytes = new byte[((int) ((BytesMessage) message).getBodyLength())];
                ((BytesMessage) message).readBytes(bytes);
                content = new String(bytes, StandardCharsets.UTF_8);
            } else if (message instanceof TextMessage) {
                content = ((TextMessage) message).getText();
            }
            return content;
        } catch (Exception e) {
            log.error(String.format("Не удалось сохранить сообщение. очередь: %s, id сообщения: %s", queueName, jmsMessageID), e);
            throw new RuntimeException(e);
        }
    }

    private String getJMSMessageID(Message message) {
        try {
            return message.getJMSMessageID();
        } catch (JMSException e) {
            log.error("Не удалось получить jmsMessageID");
            throw new RuntimeException(e);
        }
    }
}
