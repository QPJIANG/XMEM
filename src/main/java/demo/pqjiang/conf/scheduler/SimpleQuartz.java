package demo.pqjiang.conf.scheduler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleQuartz implements Job {

    private Logger logger = LogManager.getLogger(SimpleQuartz.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("SimpleQuartz run ...............");
        String time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a", Locale.ENGLISH).format(System.currentTimeMillis());
        logger.info("time:" + time);
    }
}
