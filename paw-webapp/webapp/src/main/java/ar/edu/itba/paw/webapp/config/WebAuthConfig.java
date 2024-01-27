package ar.edu.itba.paw.webapp.config;


import ar.edu.itba.paw.webapp.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
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
import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    private BasicAuthFilter basicAuthFilter;
    @Autowired
    private AccessControl accessControl;
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

    private static final String ACCESS_CONTROL_CHECK_USER = "@accessControl.checkUser(request, #id)";
    private static final String ACCESS_CONTROL_CHECK_REVIEW_OWNER_OR_ADMIN = "@accessControl.checkReviewOwnerOrAdmin(request, #id)";
    private static final String ACCESS_CONTROL_CHECK_COMMENT_OWNER_OR_ADMIN = "@accessControl.checkCommentOwnerOrAdmin(request, #id)";
    private static final String ACCESS_CONTROL_CHECK_COMMENT_NOT_OWNER = "@accessControl.checkCommentNotOwner(request, #id)";
    private static final String ACCESS_CONTROL_CHECK_REVIEW_NOT_OWNER = "@accessControl.checkReviewNotOwner(request, #id)";


    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                webExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter()
        );
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public WebExpressionVoter webExpressionVoter() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
        return webExpressionVoter;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        return expressionHandler;
    }


    @Bean
    public JwtTokenUtil jwtTokenUtil(@Value("classpath:jwt.key") Resource jwtKeyResource) throws IOException {
        return new JwtTokenUtil(jwtKeyResource);
    }
    @Override @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint () {
        return new UnauthorizedRequestHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(ALL));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "X-Refresh-Token","Link", "Location", "ETag", "Cache-Control", "Total-Elements", "Content-Type", "Total-Reviews", "Total-Review-Reports", "Total-Comment-Reports", "Total-User-Review", "Total-Content"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
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
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic()
                .and().headers().cacheControl().disable()
                .and().exceptionHandling()
                // Set unauthorized requests exception handler
                .authenticationEntryPoint(new UnauthorizedRequestHandler())
                // Set forbidden requests exception handler
                .accessDeniedHandler(new ForbiddenRequestHandler())
                .and().authorizeRequests()
                //content
                .antMatchers(HttpMethod.PUT,"/api/content/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/content/{contentId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/content").hasRole("ADMIN")
                //reviews
                .antMatchers(HttpMethod.POST,"/api/reviews/**").authenticated()
                .antMatchers(HttpMethod.GET,"/api/reviews/reports").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/reviews/{reviewId}/reports").hasRole("ADMIN")
                //comments
                .antMatchers(HttpMethod.POST,"/api/comments/**").authenticated()
                .antMatchers(HttpMethod.GET,"/api/comments/reports").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/comments/{commentId}/reports").hasRole("ADMIN")
                //users
                .antMatchers(HttpMethod.PUT,"/api/user/{id}/profileImage").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/user/{id}/role").hasRole("ADMIN")
                //lists
                .antMatchers("/api/**").permitAll()
                // Add JWT Token Filter
                .and().csrf().disable()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // Add Basic Auth Filter
                .addFilterBefore(basicAuthFilter, UsernamePasswordAuthenticationFilter.class);
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

}
