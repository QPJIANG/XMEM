package demo.pqjiang.conf;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class MyBatisScannerConfig {
    private Logger logger = LoggerFactory.getLogger(MyBatisScannerConfig.class);

    private String mapperbase=null;

    @Bean
    public MapperScannerConfigurer MapperScannerConfigurer() {
        logger.info("init MapperScannerConfigurer");

        Properties ps = new Properties();
        try {
            InputStream in = null;
            in = SysInfo.class.getClassLoader().getResourceAsStream("application.properties");
            ps.load(in);
            in.close();
            mapperbase=ps.getProperty("mybatis.mapperbase");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.debug("failed to get mybatis mapper package path,use the default: demo.pqjiang.mapper");
            mapperbase="demo.pqjiang.mapper";
        }

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(mapperbase);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
}
