package my.own.adp.data.ibm.msg;

import javax.jms.Message;

//TODO Добавить документацию
public interface MessageService {

    void handleMessage(String queueName, Message message);

}
