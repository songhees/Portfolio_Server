package pofo_server.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(
    basePackages = "pofo_server",
    excludeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class)
    })
@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

  private final DataSource dataSource;

  AppConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }


  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public DataSource dataSource(
      @Value("${spring.datasource.driver-class-name}") String driver,
      @Value("${spring.datasource.url}") String url,
      @Value("${spring.datasource.username}") String username,
      @Value("${spring.datasource.password}") String password,
      @Value("${spring.datasource.hikari.schema}") String schema,
      @Value("${spring.datasource.hikari.idle-timeout}") long idleTimeout,
      @Value("${spring.datasource.hikari.connection-timeout}") long connectionTimeout) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(driver);
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setSchema(schema);
    hikariConfig.setUsername(username);
    hikariConfig.setPassword(password);
    hikariConfig.setIdleTimeout(idleTimeout);
    hikariConfig.setConnectionTimeout(connectionTimeout);

    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setConfigLocation(
        resolver.getResource("classpath:/mybatis/mybatis-config.xml"));
    sqlSessionFactoryBean.setMapperLocations(
        resolver.getResources("classpath:/mybatis/mappers/*Mapper.xml"));
    sqlSessionFactoryBean.setFailFast(true);
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSession() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
