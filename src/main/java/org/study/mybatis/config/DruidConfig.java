package org.study.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@MapperScan(basePackages = {"org.study.mybatis.dao.mapper"},sqlSessionTemplateRef = "funSqlSessionTemplate")
public class DruidConfig {

    @Autowired
    private Environment env;
    @Autowired
    private DruidConfigProperties druidProperties;

    @Bean(name = "fun")
    public DataSource druid() {
        DruidDataSource dds = new DruidDataSource();
        dds.setUrl(druidProperties.getUrl());
        dds.setUsername(druidProperties.getUsername());
        dds.setPassword(druidProperties.getPassword());
        dds.setDriverClassName(druidProperties.getDriverClassName());

        dds.setInitialSize(druidProperties.getInitialSize());
        dds.setMinIdle(druidProperties.getMinIdle());
        dds.setMaxActive(druidProperties.getMaxActive());
        dds.setMaxWait(druidProperties.getMaxWait());
        dds.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        dds.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        dds.setValidationQuery(druidProperties.getValidationQuery());

        dds.setTestWhileIdle(druidProperties.getTestWhileIdle());
        dds.setTestOnBorrow(druidProperties.getTestOnBorrow());
        dds.setTestOnReturn(druidProperties.getTestOnReturn());
        dds.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
        dds.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());

        try {
            dds.setFilters(druidProperties.getFilters());
            dds.init();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return dds;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean("funSqlSessionFactory")
    public SqlSessionFactory funSqlSessionFactory(@Qualifier("fun") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);// 指定数据源(这个必须有，否则报错)
        fb.setTypeAliasesPackage(env.getProperty("mybatis.type.aliases.package.fun"));// 指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(env.getProperty("mybatis.mapper.locations.fun")));

        fb.setPlugins(new Interceptor[]{new MybatisInterceptor()});

        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean(name = "funTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("fun") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * session连接模板
     */
    @Bean(name = "funSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("funSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory); // 使用上面配置的Factory
        return template;
    }

    //配置Druid的监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");//默认就是允许所有访问
        initParams.put("deny","192.168.15.21");
        bean.setInitParameters(initParams);
        return bean;
    }

    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return  bean;
    }

}
