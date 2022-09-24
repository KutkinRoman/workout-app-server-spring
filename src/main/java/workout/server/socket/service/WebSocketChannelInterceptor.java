package workout.server.socket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import workout.server.security.entity.inter.CustomUserDetails;
import workout.server.security.service.JwtService;

import java.util.List;
import java.util.Objects;

import static workout.server.security.constans.TokenType.BEARER;


@Component
@RequiredArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    @Value("${web.socket.subscribe.data.endpoint}")
    private String subscribeDataEndpoint;

    private final JwtService jwtService;

    @Override
    public Message<?> preSend (Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = validationAccessor (MessageHeaderAccessor.getAccessor (message, StompHeaderAccessor.class));
        switch (Objects.requireNonNull (accessor.getCommand ())) {
            case CONNECT:
                connectAuthorization (accessor);
                break;
            case SUBSCRIBE:
                validationSubscribe (accessor);
                break;
        }
        return message;
    }

    private StompHeaderAccessor validationAccessor (StompHeaderAccessor accessor) {
        if (accessor == null) {
            throw new NullPointerException (StompHeaderAccessor.class.getName () + " must not be null");
        }
        if (accessor.getCommand () == null) {
            throw new NullPointerException (StompCommand.class.getName () + " must not be null");
        }
        return accessor;
    }

    private void connectAuthorization (StompHeaderAccessor accessor) {
        List<String> nativeHeader = accessor.getNativeHeader (HttpHeaders.AUTHORIZATION);
        if (nativeHeader == null || nativeHeader.isEmpty ()) {
            throw new NullPointerException (NativeMessageHeaderAccessor.class.getName () + " must not be null");
        }
        String accessToken = nativeHeader.get (0).substring (BEARER.getHeader ().length ());
        if (!jwtService.validateJwtAccessToken (accessToken)) {
            accessor.setUser (null);
            return;
        }
        authenticateUser (accessor, accessToken);
    }

    private void authenticateUser (StompHeaderAccessor accessor, String accessToken) {
        CustomUserDetails user = jwtService.parseUserDetails (accessToken);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken (user, null, user.getAuthorities ());
        authentication.setDetails (user);
        SecurityContextHolder.getContext ().setAuthentication (authentication);
        accessor.setUser (authentication);
    }

    private void validationSubscribe (StompHeaderAccessor accessor) {
        final String validEndpoint = subscribeDataEndpoint + "/" + Objects.requireNonNull (accessor.getUser ()).getName ();
        if (!validEndpoint.equals (accessor.getDestination ())) {
            accessor.setUser (null);
        }
    }
}
