package my.own.adp.data.ibm.queues.container;

import java.util.List;
import java.util.Map;

//TODO Добавить документацию
public interface QueuesContainer {
    void enable(String queue);

    void disable(String queue);

    Map<Boolean, List<String>> getStatuses();

    void invalidateAll();
}
