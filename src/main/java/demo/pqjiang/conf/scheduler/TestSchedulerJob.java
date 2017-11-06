package demo.pqjiang.conf.scheduler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.Date;

/**
 * Test job scheduler
 */

public class TestSchedulerJob implements Runnable {
    private Logger logger = LogManager.getLogger(TestSchedulerJob.class);

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        logger.info("current id:" + ct.getId());
        logger.info("current name:" + ct.getName());
    }

    private ConcurrentTaskScheduler tpts = new ConcurrentTaskScheduler();

    private void start() {
        tpts.schedule(this, new Date());
    }

    public static void main(String[] args) {
        new TestSchedulerJob().start();
    }
}
