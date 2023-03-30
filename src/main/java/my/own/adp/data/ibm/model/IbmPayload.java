package my.own.adp.data.ibm.model;

import lombok.Data;

@Data
public class IbmPayload {

    private final String jmsMessageID;
    private final String content;

}

