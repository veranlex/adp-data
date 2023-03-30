package my.own.adp.data.ibm.queues.container;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import my.own.adp.data.ibm.conf.IbmJmsListenerEndpointRegistrar;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class QueuesContainerImpl implements QueuesContainer {
    private static final Map<String, Boolean> QUEUES = new ConcurrentHashMap<>();

    private final List<IbmJmsListenerEndpointRegistrar> registrars;

    @PostConstruct
    void init() {
        invalidateAll();
    }

    @Override
    public void enable(String queue) {
        QUEUES.put(queue, true);
    }

    @Override
    public void disable(String queue) {
        QUEUES.put(queue, false);
    }

    @Override
    public Map<Boolean, List<String>> getStatuses() {
        return QUEUES.entrySet()
                .stream()
                .map(entry -> {
                    var list = new ArrayList<String>();
                    list.add(entry.getKey());
                    return Map.entry(entry.getValue(), list);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> {
                            k.addAll(v);
                            return k;
                        })
                );
    }

    @Override
    public void invalidateAll() {
        registrars.forEach(reg -> {
                    reg.getQueues().forEach(this::disable);
                }
        );
    }

}
