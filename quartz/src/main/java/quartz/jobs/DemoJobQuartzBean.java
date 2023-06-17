package quartz.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 
 * 注解  @DisallowConcurrentExecution 表示是否可以并发的执行同一个 JobDetail 
 * 因为每次执行job都会创建新的实例 
 * @author wangy
 * 具体参考 : http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-03.html 
 * JobKey  JobDataMap 
 * 
 * Spring中 继承 QuartzJobBean 或者 实现Job接口创建 任务 
 */
public class DemoJobQuartzBean extends QuartzJobBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoJobQuartzBean.class);
    private static final AtomicInteger index = new AtomicInteger();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("<DemoJobQuartzBean> 任务名称 : {}, index --> {}", context.getJobDetail().getKey(), index.addAndGet(2));
        LOGGER.info("获取job data map --> {}", context.getJobDetail().getJobDataMap().getWrappedMap());
    }

}
