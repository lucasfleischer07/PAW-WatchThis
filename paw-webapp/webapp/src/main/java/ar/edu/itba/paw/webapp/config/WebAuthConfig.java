package ar.edu.itba.paw.webapp.config;


//import ar.edu.itba.paw.webapp.auth.JwtAuthenticationFilter;
import ar.edu.itba.paw.webapp.auth.RedirectionSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.web.cors.CorsConfiguration.ALL;

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

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new RedirectionSuccessHandler("/");
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)	throws	Exception	{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

//    TODO: VER BIEN ESTO
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(ALL));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Link", "Location", "ETag", "Total-Elements"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.addAllowedHeader(ALL);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception	{
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.cors()
                .and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic()
                .and().headers().cacheControl().disable()
                   // .invalidSessionUrl("/login/sign-in")
                .and().authorizeRequests()
//                    .antMatchers("/login/sign-in","/login/sign-up","/login/forgot-password").anonymous()
//                    //.antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/contentForm").hasRole("ADMIN")
//                    .antMatchers("/content/create").hasRole("ADMIN")
//                    .antMatchers("/content/editInfo/*").hasRole("ADMIN")
//                    .antMatchers("/report/reportedContent/**").hasRole("ADMIN")
//                    .antMatchers("/reviewForm/movie/*").authenticated()
//                    .antMatchers("/reviewForm/serie/*").authenticated()
//                    .antMatchers("/login/sign-out").authenticated()
//                    .antMatchers("/profile").authenticated()
//                    .antMatchers("/profile/page/*").authenticated()
//                    .antMatchers("/profile/editProfile").authenticated()
//                    .antMatchers("/profile/viewedList").authenticated()
//                    .antMatchers("/profile/viewedList/page/*").authenticated()
//                    .antMatchers("/profile/watchList").authenticated()
//                    .antMatchers("/profile/watchList/page/*").authenticated()
//                    .antMatchers("/reviewForm/edit/movie/*/*").authenticated()
//                    .antMatchers("/reviewForm/edit/serie/*/*").authenticated()
//                    .antMatchers("/reviewReputation/thumbUp/*").authenticated()
//                    .antMatchers("/reviewReputation/thumbDown/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/reviewForm/movie/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/reviewForm/serie/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/profile/editProfile").authenticated()
//                    .antMatchers(HttpMethod.POST,"/reviewForm/edit/movie/*/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/reviewForm/edit/serie/*/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/review/*/delete").authenticated()
//                    .antMatchers(HttpMethod.POST,"/review/add/comment/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/report/review/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/watchList/add/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/watchList/delete/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/viewedList/add/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/viewedList/delete/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/report/comment/*").authenticated()
//                    .antMatchers(HttpMethod.POST,"/comment/*/delete").authenticated()
//                    .antMatchers(HttpMethod.POST,"/content/create").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST,"/profile/*").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST,"/content/editInfo/*").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST,"/content/*/delete").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST,"/report/reportedContent/*/*/report/delete").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
//                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }


    private String loadRememberMeKey(){
        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("rememberMe.key"))){
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
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
