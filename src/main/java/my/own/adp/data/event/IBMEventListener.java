package my.own.adp.data.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import my.own.adp.data.rabbit.service.RabbitService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IBMEventListener {

    private final RabbitService rabbitService;

    @Async
    @EventListener
    public void eventListener(IBMEvent ibmEvent) {
        rabbitService.sendAndReceive(List.of(ibmEvent.getMessage()), ibmEvent.getQueueName());
    }

}
