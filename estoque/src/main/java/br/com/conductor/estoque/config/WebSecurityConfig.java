package br.com.conductor.estoque.config;


import br.com.conductor.estoque.security.JwtAuthenticationEntryPoint;
import br.com.conductor.estoque.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ENDPOINTS_LIBERADOS = {
            "/tokens/**",
            "/webjars/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/api/usuarios",
            "/api/usuarios/auth",
            "/actuator/**",
            "/api",
            "/api/produtos/teste",
            "/api/produtos/teste-estoque-jef"
    };

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(ENDPOINTS_LIBERADOS).permitAll()
                .antMatchers("/api/produtos/estoque").permitAll()
                .antMatchers("/api/produtos/teste").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

}