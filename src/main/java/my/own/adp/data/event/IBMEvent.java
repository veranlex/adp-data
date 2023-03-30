package my.own.adp.data.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import my.own.adp.data.dto.IncomingMsgJournalDto;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class IBMEvent implements Serializable {

    private IncomingMsgJournalDto message;
    private String queueName;

}
