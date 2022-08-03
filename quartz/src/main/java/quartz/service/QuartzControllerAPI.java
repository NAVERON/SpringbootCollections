package quartz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quartz.config.QuartzUtils;
import quartz.jobs.DemoJobQuartzBean;

@RestController 
@RequestMapping(path = {"api/quartz"})
public class QuartzControllerAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzControllerAPI.class);
    private final QuartzUtils quartzUtils;
    
    public QuartzControllerAPI(QuartzUtils quartzUtils) {
        this.quartzUtils = quartzUtils;
    }
    
    @GetMapping(path = {"mannually"})
    public ResponseEntity<Object> startMannuallyJob() {
        
        this.quartzUtils.scheduledMannually("hello", "eron");
        return ResponseEntity.ok("successful : mannually create mode");
    }
    
    @GetMapping(path = {"mannually/stop"})
    public ResponseEntity<Object> stopMannuallyJob() {
        this.quartzUtils.stopMannually("hello", "eron");
        return ResponseEntity.ok("successful : mannually stop ...");
    }
    
    @GetMapping(path = {"create/{group}/{name}"})
    public ResponseEntity<Object> createDemoSchedule(@RequestParam(value = "interval") Long time, 
            @PathVariable(value = "group") String group, @PathVariable(value = "name") String name) {
        Map<String, String> data = new HashMap<>(){{
            put("NULL", "NULL");
        }};
        this.quartzUtils.createJob(DemoJobQuartzBean.class, name, group, time, TimeUnit.SECONDS, data);
        
        return ResponseEntity.ok("successful : createDemoSchedule");
    }
    
    @GetMapping(path = {"reschedule/{group}/{name}"})
    public ResponseEntity<Object> rescheduleDemoJob(@RequestParam(value = "interval") Long time, 
            @PathVariable(value = "group") String group, @PathVariable(value = "name") String name) {
        this.quartzUtils.reScheduleJob(name, group, time, TimeUnit.SECONDS);
        
        return ResponseEntity.ok("successful : rescheduleDemoJob");
    }
    
    @GetMapping(path = {"pause/{group}/{name}"})
    public ResponseEntity<Object> pauseDemoJob(@PathVariable(value = "group") String group, 
            @PathVariable(value = "name") String name) {
        this.quartzUtils.pauseJob(name, group);
        
        return ResponseEntity.ok("successful : pauseDemoJob");
    }
    
    @GetMapping(path = {"resume/{group}/{name}"})
    public ResponseEntity<Object> resumeDemoJob(@PathVariable(value = "group") String group, 
            @PathVariable(value = "name") String name) {
        this.quartzUtils.resumeJob(name, group);
        
        return ResponseEntity.ok("successful : resumeDemoJob");
    }
    
    @GetMapping(path = {"delete/{group}/{name}"})
    public ResponseEntity<Object> deleteDemoJob(@PathVariable(value = "group") String group, 
            @PathVariable(value = "name") String name) {
        this.quartzUtils.deleteJob(name, group);
        
        return ResponseEntity.ok("successful : deleteDemoJob");
    }
    
    @GetMapping(path = {"run/{group}/{name}"})
    public ResponseEntity<Object> runOnceDemoJob(@PathVariable(value = "group") String group, 
            @PathVariable(value = "name") String name) {
        this.quartzUtils.runJobNowAndOnce(DemoJobQuartzBean.class, name, group);
        
        return ResponseEntity.ok("successful : runOnceDemoJob");
    }
    
    @GetMapping(path = {"jobs"})
    public ResponseEntity<Object> queryAllJobs() {
        List<Map<String, Object>> result = this.quartzUtils.queryAllJobs();
        LOGGER.info("all jobs --> {}", result.toString());
        
        return ResponseEntity.ok("successful : queryAllJobs");
    }
    
    @GetMapping(path = {"jobs/status"})
    public ResponseEntity<Object> queryAllRunningJobs(@RequestParam(value = "status") String status) {
        List<Map<String, Object>> result = this.quartzUtils.queryRunningJobs();
        LOGGER.info("all running jobs --> {}", result.toString());
        
        return ResponseEntity.ok("successful : queryAllRunningJobs");
    }
    
    @GetMapping(path = {"jobs/action"})
    public ResponseEntity<Object> jobsWithAction(@RequestParam(value = "action") String action) {
        this.quartzUtils.pauseAllJobs();
        
        return ResponseEntity.ok("successful : jobsWithAction");
    }
    
    
    
}






