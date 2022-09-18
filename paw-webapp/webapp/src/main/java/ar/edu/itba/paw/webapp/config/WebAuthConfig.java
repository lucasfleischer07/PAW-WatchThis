package ar.edu.itba.paw.webapp.config;


import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import com.sun.org.glassfish.gmbal.ManagedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)	throws	Exception	{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception	{
        http.sessionManagement()
                   // .invalidSessionUrl("/login/sign-in")
                .and().authorizeRequests()
                    .antMatchers("/login/sign-in","/login/sign-up").anonymous()
                    //.antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/profile/*").authenticated()
                    .antMatchers("/reviewForm/movie/*").authenticated()
                    .antMatchers("/reviewForm/serie/*").authenticated()
                    .antMatchers("/login/sign-out").authenticated()
                    .antMatchers("/profile/*/edit-profile").authenticated()
                    .antMatchers(HttpMethod.POST,"/reviewForm/movie/*").authenticated()
                    .antMatchers(HttpMethod.POST,"/reviewForm/serie/*").authenticated()
                    .antMatchers(HttpMethod.POST,"/profile/*/edit-profile").authenticated()
                    .antMatchers("/**").permitAll()
                .and().formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginPage("/login/sign-in")
                    .defaultSuccessUrl("/",false)
                .and().rememberMe()
                    .rememberMeParameter("rememberMe")
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                 //   .key(loadRememberMeKey())
                .and().logout()
                    .logoutSuccessUrl("/login/sign-out")
                    .logoutSuccessUrl("/login/sign-in")
                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf().disable();
    }


    private String loadRememberMeKey(){
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("rememberme.key")
        )){
            return FileCopyUtils.copyToString(reader);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void	configure(final WebSecurity web)throws	Exception{
        web.ignoring()
                .antMatchers("/css/**",	"/js/**",	"/img/**",	"/favicon.ico",	"/403");
    }
    }
