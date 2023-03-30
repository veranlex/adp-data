package my.own.adp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "incoming_msg_journal", schema = "INTEGRATION")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncomingMsgJournal {

    @Id
    private String id;

    @Column(name = "create_date")
    private Timestamp createDate;

    private String payload;

    private String status;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "send_counter")
    private Integer sendCounter;

    @Column(name = "source_queue")
    private String sourceQueue;

}


