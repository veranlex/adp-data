package my.own.adp.data.ibm.switcher.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.own.adp.data.ibm.switcher.service.SwitcherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import my.own.adp.data.ibm.queues.container.QueuesContainer;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/switcher")
public class SwitcherController {

    private final SwitcherService switcherService;
    private final QueuesContainer queuesContainer;

    @GetMapping("/change")
    public Map<String, String> switchQueues(@RequestParam List<String> queues) {
        log.info("запрос на перестройку " + queues);
        switcherService.changeListenerQueues(queues);
        return Map.of("status", "ok");
    }

    @GetMapping("/statuses")
    public Map<Boolean, List<String>> queuesStatuses() {
        return queuesContainer.getStatuses();
    }
}
