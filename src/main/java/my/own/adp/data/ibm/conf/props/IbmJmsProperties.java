package my.own.adp.data.ibm.conf.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@ConfigurationProperties("ibm")
public record IbmJmsProperties(@NestedConfigurationProperty IbmFileProps files,
                               @NestedConfigurationProperty IbmJmsMqConnectionProps mq) {

    public record IbmFileProps(String location) {
    }

    public record IbmJmsMqConnectionProps(String host,
                                          int port,
                                          String user,
                                          String password,
                                          String queueManager,
                                          @NestedConfigurationProperty IbmJmsMqProps crewcn,
                                          @NestedConfigurationProperty IbmJmsMqProps eco) {
    }

    public record IbmJmsMqProps(String channel,
                                List<String> queues) {
    }
}

