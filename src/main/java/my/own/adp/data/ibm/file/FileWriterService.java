package my.own.adp.data.ibm.file;

//TODO Добавить документацию
public interface FileWriterService {

    void writeMessageFromIBMMQToFile(String queueName, String jmsMessageID, String content);

}
