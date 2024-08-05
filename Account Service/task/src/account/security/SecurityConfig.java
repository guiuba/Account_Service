package account.security;

//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig  { //extends WebSecurityConfigurerAdapter
    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    //  private final LoggingService loggingService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, RestAuthenticationEntryPoint restAuthenticationEntryPoint) { // , LoggingService loggingService
        this.userDetailsService = userDetailsService;
        //  this.loggingService = loggingService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

 //   @Override

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }*/
 /*   @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }*/

  //  @Override

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handles auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and().authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll()
              //  .mvcMatchers(HttpMethod.GET, "/api/auth/teste").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/empl/payment").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/auth/changepass").authenticated()

                 .requestMatchers("/api/security/events").hasRole("AUDITOR")

             //   .hasRole("USER", "ACCOUNTANT")
                .requestMatchers(HttpMethod.POST, "/api/acct/payments").hasRole("ACCOUNTANT")
                .requestMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole("ACCOUNTANT")
                .requestMatchers(HttpMethod.PUT, "/api/admin/user/role").hasRole("ADMINISTRATOR")
                .requestMatchers(HttpMethod.DELETE, "/api/admin/user").hasRole("ADMINISTRATOR")
                .requestMatchers(HttpMethod.GET, "/api/admin/user/**").hasRole("ADMINISTRATOR")
                .anyRequest().authenticated()
                //   )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


/*    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        };
    }*/

 /*   @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(loggingService);
    }*/
}
