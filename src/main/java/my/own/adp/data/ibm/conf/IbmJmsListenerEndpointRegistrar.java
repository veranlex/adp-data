package my.own.adp.data.ibm.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;

import java.util.List;

@Getter
@Setter
public class IbmJmsListenerEndpointRegistrar extends JmsListenerEndpointRegistrar {
    private List<String> queues;
}
