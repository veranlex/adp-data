package my.own.adp.data.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web")
@Data
public class WebConfigProperties {

    private Cors cors;

    @Data
    public static class Cors {
        private String[] allowedOrigins;
        private String[] allowedMethods;
        private long maxAge;
        private String[] allowedHeaders;
        private String[] exposedHeaders;
    }
}
