package demo.pqjiang.controller;

import demo.pqjiang.base.BaseController;
import demo.pqjiang.conf.scheduler.SimpleQuartz;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@Scope("prototype")
@RequestMapping("scheduler")
public class SchedulerController extends BaseController {

    public static int id = 0;
    private Logger logger = LogManager.getLogger(SchedulerController.class);

    @Autowired
    StdScheduler quartzScheduler;

    @RequestMapping("/")
    public String index(Model model) {
        return "scheduler/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Map<String, Object>> listJobs() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            // get all job_groupName
            for (String groupName : quartzScheduler.getJobGroupNames()) {
                //taskManager.getTriggerGroupNames()  // get all  trigger_groupName
                //taskManager.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName)); //  get all trigger by trigger_groupName

                logger.info("groupName:" + groupName);
                for (JobKey jobKey : quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    logger.info("jobkey:" + jobKey.toString());
                    Map<String, Object> result = new HashMap<String, Object>();
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) quartzScheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    String key = triggers.get(0).getJobKey().toString();
                    result.put("jobName", jobName);
                    result.put("jobGroup", jobGroup);
                    result.put("key", key);
                    result.put("nextFireTime", nextFireTime);
                    list.add(result);
                }
            }
        } catch (Exception e) {
            logger.info("exception:" + e.getMessage());
            return list;
        }
        return list;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map addJob() {
        Map m = new HashMap<String, Object>();

        Scheduler scheduler = quartzScheduler;
        try {
            String jobId = "1000" + SchedulerController.id;
            String jobName = "job_name";
            String jobGourp = "job_group";
            String jobCron = "0/5 * * * * ?";
            String jobDesc = "job_dec_string";

            TriggerKey triggerKey = TriggerKey.triggerKey(jobId, jobGourp);
            //  triggerKey: jobGourp.jobid
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {

                JobDetail jobDetail = JobBuilder
                        .newJob(SimpleQuartz.class)
                        .withIdentity("task_" + jobId, "group_" + jobId).build();

                jobDetail.getJobDataMap().put("scheduleJob", triggerKey);
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobCron);

                trigger = TriggerBuilder.newTrigger().withIdentity("task_" + jobId, "group_" + jobId)
                        .withSchedule(scheduleBuilder).build();

                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // trigger已存在，则更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobCron);
                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        return m;
    }

    @RequestMapping("/stop")
    @ResponseBody
    public Map stopJob() {
        Map m = new HashMap<String, Object>();
        return m;
    }

    @RequestMapping("/stopall")
    @ResponseBody
    public Map stopAllJob() {
        Map m = new HashMap<String, Object>();
        try {
            quartzScheduler.pauseAll();
            m.put("success", true);
        } catch (Exception e) {
            m.put("success", false);
            m.put("exception", e.getMessage());
        }
        return m;
    }

    @RequestMapping("/resume")
    @ResponseBody
    public Map resumeJob() {
        Map m = new HashMap<String, Object>();
        return m;
    }

    @RequestMapping("/resumeall")
    @ResponseBody
    public Map resumeAllJob() {
        Map m = new HashMap<String, Object>();
        try {
            quartzScheduler.resumeAll();
            m.put("success", true);
        } catch (Exception e) {
            m.put("success", false);
            m.put("exception", e.getMessage());
        }
        return m;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Map removeJob() {
        Map m = new HashMap<String, Object>();
        return m;
    }

    @RequestMapping("/removeall")
    @ResponseBody
    public Map removeAllJob() {
        Map m = new HashMap<String, Object>();
        try {
            for (String groupName : quartzScheduler.getJobGroupNames()) {
                for (JobKey jobKey : quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
//                    JobDetail job = quartzScheduler.getJobDetail(jobKey);
//                    List<Trigger> triggers = (List<Trigger>) quartzScheduler.getTriggersOfJob(jobKey);
                    quartzScheduler.deleteJob(jobKey);
                }
            }
            m.put("success", true);
        } catch (Exception e) {
            m.put("success", false);
            m.put("exception", e.getMessage());
        }
        return m;
    }


}
