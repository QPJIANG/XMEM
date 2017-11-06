package demo.pqjiang.conf.scheduler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 *  job run while system start up
 *  spring scheduler
 *
 * */

@Component
public class SpringScehdulerExampleJob {
    public final static long ONE_Minute =  60 * 1000;
    private Logger logger = LogManager.getLogger(SpringScehdulerExampleJob.class);

    @Scheduled(fixedDelay=ONE_Minute)
    public void Job1(){
        logger.info(new Date().toString()+":Job1执行....");
    }

    @Scheduled(fixedRate=ONE_Minute)
    public void Job2(){
        logger.info(new Date().toString()+":Job2执行....");
    }

    @Scheduled(cron="0 15 3 * * ?")
    public void cronJob(){
        logger.info(new Date().toString()+" : cronJob执行....");
    }
}