package ar.edu.itba.paw.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
@EnableTransactionManagement
@EnableAsync
@EnableWebMvc
@ComponentScan({
        "ar.edu.itba.paw.webapp.controller",
        "ar.edu.itba.paw.services",
        "ar.edu.itba.paw.persistance"
})
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);


    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    @Bean
    public DataSource dataSource(){
//        * Conexion a Heroku
        LOGGER.info("Data base set up");
//        String dbUrl="jdbc:postgresql://localhost:5432/postgres";
//        String username = "postgres";
//        String password = "postgres";

        String dbUrl="jdbc:postgresql://ec2-44-209-158-64.compute-1.amazonaws.com:5432/dejahu751a4sa5";
        String username = "nnsbcsmyzbkewt";
        String password = "2d3518851436a2f7f6c4367b2c79aa6f66f456a436aeaf02f44cd07a80497f27";
        final SimpleDriverDataSource basicDataSource = new SimpleDriverDataSource();
        basicDataSource.setDriverClass(org.postgresql.Driver.class);
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;

//        * Conexion a la db de  la catedra
//        String dbUrl="jdbc:postgresql://10.16.1.110:5432/paw-2022b-3";
//        String username = "paw-2022b-3";
//        String password = "h79KBcheb";
//        final SimpleDriverDataSource basicDataSource = new SimpleDriverDataSource();
//        basicDataSource.setDriverClass(org.postgresql.Driver.class);
//        basicDataSource.setUrl(dbUrl);
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//        return basicDataSource;


    }
/*
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        final LocalContainerEntityManagerFactoryBean factoryBean=new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
        factoryBean.setDataSource(dataSource());
        final JpaVendorAdapter vendorAdapter=new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        final Properties properties=new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQL92Dialect");

        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf)
    {return new JpaTransactionManager(emf);}
*/
@Bean
public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
    factoryBean.setDataSource(dataSource());
    final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    factoryBean.setJpaVendorAdapter(vendorAdapter);
    final Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", "update");
    properties.setProperty("hibernate.show_sql", "true"); // TODO: remove this
    properties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQL92Dialect");

// Si ponen esto en prod, hay tabla!!!
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("format_sql", "true");
    factoryBean.setJpaProperties(properties);
    return factoryBean;
}

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
}
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }



    // * ----------------------- Internacionalizacion ------------------------------------------------------------------
    @Bean
    public MessageSource messageSource(){
        final ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();

        msgSource.setBasename("classpath:i18n/messages");
        msgSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        msgSource.setCacheSeconds((int)TimeUnit.MINUTES.toSeconds(2));


        return msgSource;
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------- Para subir fotos a la BDD--------------------------------------------------------------

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(300000);
        multipartResolver.setMaxUploadSizePerFile(100000000);
        multipartResolver.setMaxUploadSize(100000000 * 6);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------- Para que no matchee los paths con / al final-------------------------------------------
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer.setUseTrailingSlashMatch(false);
    }


}
