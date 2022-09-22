package workout.server.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import workout.server.security.service.AuthTokenFilter;
import workout.server.security.service.UnauthorizedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthTokenFilter authenticationJwtTokenFilter;
    private final UnauthorizedHandler unauthorizedHandler;

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http
                .exceptionHandling ().authenticationEntryPoint (unauthorizedHandler)
                .and ()
                .sessionManagement ().sessionCreationPolicy (SessionCreationPolicy.STATELESS)
                .and ()
                .cors ()
                .and ()
                .csrf ().disable ()
                .formLogin ().disable ()
                .httpBasic ().disable ()
                .authorizeRequests ()
                .anyRequest ().permitAll ()
                .and ()
                .addFilterBefore (authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager () throws Exception {
        return super.authenticationManager ();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder ();
    }

    @Override
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService (userDetailsService)
                .passwordEncoder (passwordEncoder ())
        ;
    }
}
