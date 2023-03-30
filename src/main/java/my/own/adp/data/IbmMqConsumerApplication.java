package my.own.adp.data;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {
        JmsAutoConfiguration.class
})
@EnableRabbit
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class IbmMqConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IbmMqConsumerApplication.class, args);
    }
}
