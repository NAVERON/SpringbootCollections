package quartz.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import quartz.jobs.DemoJobQuartzBean;

@Configuration  
public class ScheduleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);
    
    // JobDetail Trigger 的bean实例化 
    // 必须定义 jobdetailBVean 和 trigger 的bean
    //@Bean(name = {"demojob"}) 
    public JobDetail demoJobDetail() {
        return JobBuilder.newJob(DemoJobQuartzBean.class)
                .withIdentity("demojob")
                .storeDurably(true)  // 默认为false 如果没有关联trigger 是否连带删除job  这里这样设置会导致找不到关联trigger 无法创建
                .build();
    }
    
    //@Bean(name = {"demotrigger"}) 
    public Trigger demoJobTrigger(@Qualifier(value = "demojob") JobDetail demoJob) {
        return TriggerBuilder.newTrigger()
                .forJob(demoJob)
                .withIdentity("demotrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever())
                .build();
    }
    
    
}




