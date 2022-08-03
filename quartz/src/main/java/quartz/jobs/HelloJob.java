package quartz.jobs;

import java.util.concurrent.atomic.AtomicInteger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 直接实现JOb接口创建任务 
 * @author wangy
 *
 */
public class HelloJob implements Job {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloJob.class);
    private static final AtomicInteger index = new AtomicInteger();
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("<HelloJob> 任务名称 : {}, index --> {}", context.getJobDetail().getKey(), index.addAndGet(1));
        LOGGER.info("job datat map --> {}", context.getJobDetail().getJobDataMap().getWrappedMap());
    }

}
