package workout.server.socket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @link https://developer.okta.com/blog/2019/10/09/java-spring-websocket-tutorial
 */

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Value("${web.socket.endpoint}")
    private String wsEndpoint;

    @Value("${web.socket.subscribe.data.endpoint}")
    private String subDataEndpoint;

    @Value("${web.socket.endpoint}")
    private String appEndpoint;

    @Override
    public void configureMessageBroker (MessageBrokerRegistry config) {
        config.enableSimpleBroker (subDataEndpoint);
        config.setApplicationDestinationPrefixes (appEndpoint);
    }

    @Override
    public void registerStompEndpoints (StompEndpointRegistry registry) {
        registry
                .addEndpoint (wsEndpoint)
                .withSockJS ()
        ;
    }

    @Override
    protected void configureInbound (MessageSecurityMetadataSourceRegistry messages) {
        messages
                .anyMessage ()
                .authenticated ()
        ;
    }

    @Override
    protected boolean sameOriginDisabled () {
        return true;
    }


}