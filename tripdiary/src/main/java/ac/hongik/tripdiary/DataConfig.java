package ac.hongik.tripdiary;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataConfig {
	@Autowired private Environment env;

    @Bean
    public DataSource dataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
    	dataSource.setDriverClassName(env.getProperty("datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("datasource.url"));
        dataSource.setUsername(env.getProperty("datasource.username"));
        dataSource.setPassword(env.getProperty("datasource.password"));

    	return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedJdbcTemplate(DataSource dataSource) {
       	NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	return jdbcTemplate;
    }
}
