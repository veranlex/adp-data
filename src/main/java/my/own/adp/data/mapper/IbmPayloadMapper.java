package my.own.adp.data.mapper;

import my.own.adp.data.ibm.model.IbmPayload;

import javax.jms.Message;

//TODO Добавить документацию
public interface IbmPayloadMapper {

    IbmPayload messageToIbmPayLoad(String queueName, Message message);

}
