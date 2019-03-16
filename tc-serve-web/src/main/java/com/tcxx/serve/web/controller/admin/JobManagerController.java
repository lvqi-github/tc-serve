package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.core.utils.SpringContextHelper;
import com.tcxx.serve.service.enumtype.JobStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RestController
@RequestMapping("/admin/jobManager")
public class JobManagerController {

    @Autowired
    private SpringContextHelper springContextHelper;

    private static ConcurrentMap<String, Integer> jobStatusMap = new ConcurrentHashMap<>();

    @AdminLoginTokenValidation
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Result<Boolean> start(String schedulerName) {
        try {
            Object bean = springContextHelper.getBean(schedulerName);

            if (Objects.isNull(bean) || !(bean instanceof Scheduler)){
                return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "schedulerName无效");
            }

            Scheduler scheduler = (Scheduler) bean;
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
            jobStatusMap.put(schedulerName, JobStatusEnum.JOB_STATUS_RUNNING.getStatus());

            Result<Boolean> result = ResultBuild.wrapSuccess();
            result.setValue(true);
            return result;
        }catch (SchedulerException e){
            log.error("JobManagerController#start error", e);
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR);
        }
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public Result<Boolean> stop(String schedulerName) {
        try {
            Object bean = springContextHelper.getBean(schedulerName);

            if (Objects.isNull(bean) || !(bean instanceof Scheduler)){
                return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "schedulerName无效");
            }

            Scheduler scheduler = (Scheduler) bean;
            if (scheduler.isStarted()) {
                scheduler.standby();
            }
            jobStatusMap.put(schedulerName, JobStatusEnum.JOB_STATUS_STOP.getStatus());

            Result<Boolean> result = ResultBuild.wrapSuccess();
            result.setValue(true);
            return result;
        }catch (SchedulerException e){
            log.error("JobManagerController#stop error", e);
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR);
        }
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getAllJobStatus")
    public Result<Map<String, Object>> getAllJobStatus() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(jobStatusMap);

        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValue(map);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/runOnce", method = RequestMethod.POST)
    public Result<Boolean> runOnce(String jobDetailName) {
        try {
            Object bean = springContextHelper.getBean(jobDetailName);
            if (Objects.isNull(bean)){
                return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "jobDetailName无效");
            }

            JobDetail jobDetail = (JobDetail) bean;
            MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = (MethodInvokingJobDetailFactoryBean)
                    jobDetail.getJobDataMap().get("methodInvoker");
            Method method = jobDetailFactoryBean.getPreparedMethod();
            Object targetObj = jobDetailFactoryBean.getTargetObject();
            method.invoke(targetObj);

            Result<Boolean> result = ResultBuild.wrapSuccess();
            result.setValue(true);
            return result;
        }catch (Exception e){
            log.error("JobManagerController#runonce error", e);
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR);
        }
    }

}
