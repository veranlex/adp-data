package my.own.adp.data.rabbit.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "spring")
public record RabbitSpringProperties(@NestedConfigurationProperty RabbitProperties rabbitmq) {

    public record RabbitProperties(String port,
                                   String host,
                                   String username,
                                   String password,
                                   String virtualHost,
                                   String directExchange,
                                   QueuesProperties queues
    ) {
    }

    public record QueuesProperties(
            PersonalQueue personalQueue,
            SchedulerQueue schedulerQueue
    ) {
    }
    public record PersonalQueue(
            String queueCrewpv,
            String queueCommondata,
            String queueId,
            String queueMed,
            String queueVac,
            String queueVacationleftovers,
            String queueHrmd,
            String crewpvRoutingkey,
            String commondataRroutingkey,
            String idRoutingkey,
            String medRoutingkey,
            String vacRoutingkey,
            String vacationleftoversRoutingkey,
            String hrmdRoutingkey
    ) {
    }
    public record SchedulerQueue(
            String queueForScheduler,
            String routingKeyForScheduler
    ) {
    }

}
