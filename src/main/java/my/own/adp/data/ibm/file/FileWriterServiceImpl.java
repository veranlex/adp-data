package my.own.adp.data.ibm.file;

import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.ibm.conf.props.IbmJmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import my.own.adp.data.utils.DateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileWriterServiceImpl implements FileWriterService {

    private final String filesLocation;

    @Autowired
    public FileWriterServiceImpl(IbmJmsProperties properties) throws IOException {
        this.filesLocation = properties.files().location();
        Files.createDirectories(Paths.get(filesLocation));
    }


    @Override
    public void writeMessageFromIBMMQToFile(String queueName, String jmsMessageID, String content) {
        try {
            var fileName = """
                    %s.txt"""
                    .formatted(
                            DateUtil.convertTimestampToDate(System.currentTimeMillis()) +
                                    UUID.randomUUID()
                    );
            var fileQueuePath = filesLocation + "/" + queueName;

            Files.createDirectories(Paths.get(fileQueuePath));
            log.info("Начат процесс записи сообщения в файл: {}", fileName);

            File f = new File(fileQueuePath + "/" + fileName);
            FileWriter fstream = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
            log.info("сообщение сохранено в файл очередь={}, id сообщения={}, имя файла={}, данные={}", queueName, jmsMessageID, fileName, content.length());
        } catch (Exception e) {
            log.error(String.format("Не удалось сохранить сообщение. очередь=%s, айди сообщения=%s content=%s", queueName, jmsMessageID, content.length()), e);
            throw new RuntimeException(e);
        }
    }

}
