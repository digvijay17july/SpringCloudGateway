package com.gateway.filters;

/*
 *
 * @author Digvijay Singh
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


@RefreshScope
@Component
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {

    final Logger logger = LoggerFactory.getLogger(LoggingGatewayFilterFactory.class);

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z");
    public static final String BASE_MSG = "baseMessage";
    public static final String PRE_LOGGER = "preLogger";
    public static final String POST_LOGGER = "postLogger";

    private String timeZone="IST";

    private String environment="DEV";



    public LoggingGatewayFilterFactory() {
        super(Config.class);
    }
    @Override
    public Config newConfig() {
        return new Config();
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(BASE_MSG, PRE_LOGGER, POST_LOGGER);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isPreLogger())
            {
                logger.info("PRE IS CALLED IN TIMEZONE {} WITH ENVIRONMENT {}",timeZone,environment);
            }
             return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        if (config.isPostLogger()){
                            logger.info("POST IS CALLED IN TIMEZONE {} WITH ENVIRONMENT {}",timeZone,environment);
                        }
                    }));
        };
    }

    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

        public Config() {
        };

        public Config(String baseMessage, boolean preLogger, boolean postLogger) {
            super();
            this.baseMessage = baseMessage;
            this.preLogger = preLogger;
            this.postLogger = postLogger;
        }

        public String getBaseMessage() {
            return this.baseMessage;
        }

        public boolean isPreLogger() {
            return preLogger;
        }

        public boolean isPostLogger() {
            return postLogger;
        }

        public void setBaseMessage(String baseMessage) {
            this.baseMessage = baseMessage;
        }

        public void setPreLogger(boolean preLogger) {
            this.preLogger = preLogger;
        }

        public void setPostLogger(boolean postLogger) {
            this.postLogger = postLogger;
        }
    }

}