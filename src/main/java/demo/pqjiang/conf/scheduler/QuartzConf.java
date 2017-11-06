package demo.pqjiang.conf.scheduler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz scheduler conf
 * <p>
 * task : task implements org.quartz.Job and override execute()
 */

@Configuration
@EnableScheduling
public class QuartzConf {
    private Logger logger = LogManager.getLogger(QuartzConf.class);

    @Bean
    SchedulerFactoryBean quartzScheduler() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        logger.info("quartz scheduler init finished !");
        return scheduler;
    }
}
