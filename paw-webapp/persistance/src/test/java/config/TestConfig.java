package config;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
@ComponentScan({"ar.edu.itba.paw.persistance"})
@EnableTransactionManagement
@Configuration
public class TestConfig {
    @Value("classpath:hsqldb.sql")
    private Resource hsqldbSql;
    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Bean
    public DataSource dataSource(){
        final SimpleDriverDataSource ds=new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("");
        return ds;
    }
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds){
        final DataSourceInitializer dsInit=new DataSourceInitializer();
        dsInit.setDataSource(ds);
        dsInit.setDatabasePopulator(dataSourcePopulator());
        return dsInit;
    }

    private DatabasePopulator dataSourcePopulator(){
        final ResourceDatabasePopulator dbp= new ResourceDatabasePopulator();
        dbp.addScripts(hsqldbSql, schemaSql);//Esto lo que hace es que setee el modo compatibilidad con postgres.


        return dbp;
    }
    @Bean
    public PlatformTransactionManager transactionManager(final DataSource ds){
        return new DataSourceTransactionManager(ds);
    }
}

