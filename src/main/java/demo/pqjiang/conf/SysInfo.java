package demo.pqjiang.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * test bean for load
 *
 * **/

@Configuration
public class SysInfo {
    private Logger logger = LoggerFactory.getLogger(SysInfo.class);

    class SysInformation {
        public SysInformation() {
            logger.info("init sysInfo");
            Properties pps = System.getProperties();
//            pps.list(System.out);
            try{
                Properties ps = new Properties();
                InputStream in  = SysInfo.class.getClassLoader().getResourceAsStream("application.properties");
                ps.load(in);
                in.close();
            }catch (Exception e){
                logger.info("init sysInfo Excetion:"+e.getMessage());
            }
        }
    }

    @Bean
    public SysInformation sysInfoPrint() {
        return new SysInformation();
    }

}
