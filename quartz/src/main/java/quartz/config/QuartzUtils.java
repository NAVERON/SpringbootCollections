package quartz.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import quartz.jobs.HelloJob;



@Service 
public class QuartzUtils {  // 或者 QuartzService

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzUtils.class);
    private final Scheduler scheduler;  // quartz 自动装配的bean实现 
    
    // 传入的参数会自动从容器中获取 如果没有,直接报错, 保证注入不为null 
    public QuartzUtils(Scheduler scheduler) {
        this.scheduler = scheduler;
        Assert.notNull(scheduler, "Scheduler must not NULL!!!");
    }
    
    @PostConstruct 
    public void init() {
        try {
            /**
             * The misfire/recovery process will be started, 
             * if it is the initial callto this method on this scheduler instance. 
             */
            this.scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    // 自行创建jobdetail trigger scheduler 
    public void scheduledMannually(String jobName, String jobGroup) {
        SimpleScheduleBuilder.simpleSchedule().repeatForever().build();
        
        Map<String, String> jobData = new HashMap<>() {{
            put("test", "helloJob");
        }};
        
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity(new JobKey(jobName, jobGroup)) 
                .usingJobData("mannually", "test")  // 单独设置 
                .usingJobData(new JobDataMap(jobData))  // map批量设置 
                // .storeDurably(false) // 如果没有关联trigger 会创建失败 
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(new TriggerKey(jobName, jobGroup))
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(4))  // 设置调度周期 
                .startAt(DateBuilder.futureDate(5, IntervalUnit.SECOND))  // 5秒延迟 
                .build();
        
        try {
            // Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler(); // 获取到的是RAM 调度器 
            
            this.scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.error("schedule 错误 --> {}", e);
        }
    }
    
    public void stopMannually(String jobName, String jobGroup) {
        // 停止 scheduledMannually 创建的定时任务 
        try {
            this.scheduler.deleteJob(new JobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 创建周期任务 定时任务 
     * @param jobClazz 继承quartzjobbean的实现类 
     * @param jobName 任务名称 
     * @param jobGroup 任务组 
     * @param jobTime 每过多长时间执行一次 固定延时执行 
     * @param timeUnit 时间单位, 函数内部转换成统一单位  
     * @param jobData 附带job数据 map格式 
     * @return 
     */
    public void createJob(Class<? extends QuartzJobBean> jobClazz, 
            String jobName, String jobGroup, Long jobTime, TimeUnit timeUnit, 
            Map<String, String> jobData) {
        JobDetail jobdetail = JobBuilder.newJob(jobClazz)
                .withIdentity(new JobKey(jobName, jobGroup))
                .usingJobData(new JobDataMap(jobData))  // job data map 不能为 null 
                .build();
        
        Long jobTimesInMilli = timeUnit.toMillis(jobTime);  // 转换成毫秒 
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(new TriggerKey(jobName, jobGroup))
                .withSchedule(
                    SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(jobTimesInMilli)
                    .repeatForever()
                )
                .build();
        
        try {
            Date current = this.scheduler.scheduleJob(jobdetail, trigger);
            LOGGER.info("出发事件date --> {}", current.toString());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public void reScheduleJob(String jobName, String jobGroup, Long jobTime, TimeUnit timeUnit) {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        Long jobTimesInMilli = timeUnit.toMillis(jobTime);
        try {
            SimpleTrigger trigger = (SimpleTrigger) this.scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder()
            .withIdentity(triggerKey)
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(jobTimesInMilli)
                .repeatForever()
            )
            .build();
            
            this.scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public void pauseJob(String jobName, String jobGroup) {
        try {
            this.scheduler.pauseJob(new JobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public void resumeJob(String jobName, String jobGroup) {
        try {
            this.scheduler.resumeJob(new JobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteJob(String jobName, String jobGroup) {
        try {
            this.scheduler.deleteJob(new JobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 只有触发的时候执行一次 
     * @param jobClazz job class 
     * @param jobName 任务名称 
     * @param jobGroup 任务分组 
     */
    public void runJobNowAndOnce(Class<? extends QuartzJobBean> jobClazz, String jobName, String jobGroup) {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobdetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobKey)
                .storeDurably(true)
                .build();
        
        try {
            this.scheduler.addJob(jobdetail, true);
            this.scheduler.triggerJob(jobKey);
            LOGGER.info("单词任务执行成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public List<Map<String, Object>> queryAllJobs(){
        List<Map<String, Object>> allJobs = new ArrayList<>();
        
        try {
            Set<JobKey> jobKeys = this.scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            jobKeys.stream().forEach(jobKey -> {
                Map<String, Object> infos = new HashMap<>();
                infos.put("JobKey", jobKey.toString());
                try {
                    List<? extends Trigger> triggers = this.scheduler.getTriggersOfJob(jobKey);
                    triggers.stream().forEach(trigger -> {
                        infos.put("TriggerKey", trigger.toString());
                        try {
                            TriggerState triggerState = this.scheduler.getTriggerState(trigger.getKey());
                            infos.put("TriggerState", triggerState.toString());
                        } catch (SchedulerException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                
                allJobs.add(infos);
            });
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        
        return allJobs;
    }
    
    public List<Map<String, Object>> queryRunningJobs(){
        List<Map<String, Object>> runningJobs = new ArrayList<>();
        
        try {
            List<JobExecutionContext> executions = this.scheduler.getCurrentlyExecutingJobs();
            executions.stream().forEach(execution -> {
                Map<String, Object> infos = new HashMap<>();
                JobDetail jobDetail = execution.getJobDetail();
                Trigger trigger = execution.getTrigger();
                
                infos.put("JobKey", jobDetail.getKey().toString());
                infos.put("TriggerKey", trigger.getKey().toString());
                TriggerState triggerState;
                try {
                    triggerState = this.scheduler.getTriggerState(trigger.getKey());
                    infos.put("TriggerState", triggerState.toString());
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                
                runningJobs.add(infos);
            });
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        
        return runningJobs;
    }
    
    public void pauseAllJobs() {
        try {
            this.scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
    
}







