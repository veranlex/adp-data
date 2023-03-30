package my.own.adp.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomingMsgJournalDto implements Serializable {

    private String id;
    private Timestamp createDate;
    private String payload;
    private String status;
    private String errorMessage;
    private Integer sendCounter;
    private String sourceQueue;

}
