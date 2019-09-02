package org.study.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@Configuration
//@MapperScan(basePackages = {"org.study.mybatis.dao.mapper"},sqlSessionTemplateRef = "funSqlSessionTemplate")
public class DbConfig {

    @Autowired
    private Environment env;

    @Bean(name = "fun")
    @ConfigurationProperties(prefix = "spring.datasource.fun")
    public DataSource funDataSource() {
        return DataSourceBuilder.create().build();
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

}
