package account.security;

import account.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final LogService logService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, RestAuthenticationEntryPoint restAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler, LogService logService) {
        this.userDetailsService = userDetailsService;

        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.logService = logService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                //   .httpBasic(Customizer.withDefaults())
                .httpBasic(basic -> basic.authenticationEntryPoint(restAuthenticationEntryPoint))// Handle auth errors
            /*         .exceptionHandling(ex -> ex
                           //  .authenticationEntryPoint(restAuthenticationEntryPoint)
                             //     .accessDeniedHandler(new CustomAccessDeniedHandler()) //new CustomAccessDeniedHandler(logService)
                             .accessDeniedHandler(accessDeniedHandler()) //customAccessDeniedHandler

                     )*/
                //  .and()
                .csrf(csrf -> csrf.disable()) // For Postman
                .headers(headers -> headers.frameOptions().disable()) // For the H2 console
                .authorizeHttpRequests(auth -> auth  // manage access
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/changepass").hasAnyRole("USER", "ACCOUNTANT", "ADMINISTRATOR") //.authenticated() verificar
                                .requestMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole("USER", "ACCOUNTANT")  //.authenticated() verificar
                                .requestMatchers(HttpMethod.POST, "/api/acct/payments").hasRole("ACCOUNTANT")// visto, substitui
                                .requestMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole("ACCOUNTANT") // visto, substitui
                                .requestMatchers(HttpMethod.GET, "/api/admin/user").hasRole("ADMINISTRATOR")//.permitAll()  // /** novo
                                .requestMatchers(HttpMethod.DELETE, "/api/admin/user/*").hasRole("ADMINISTRATOR")//.hasAuthority("ROLE_ADMINISTRATOR")  // novo
                                .requestMatchers(HttpMethod.PUT, "/api/admin/user/role").hasRole("ADMINISTRATOR") // novo

                        //   .requestMatchers("/api/security/events").hasRole("AUDITOR") // novo
                        //   .hasRole("USER", "ACCOUNTANT")
                        //  .anyRequest().authenticated()

                )
                //  .and()


                /*       .sessionManagement(sessions -> sessions
                               .sessionCreationPolicy(SessionCreationPolicy.STATELESS));*/
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//;

                .and()
               .exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //new CustomAccessDeniedHandler(logService)

        return http.build();

    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(13);
    } // coloquei 13

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

//...
//.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
}
