package io.nutz.nutzsite.module.oa.controller;

import com.alibaba.fastjson.JSONArray;
import io.nutz.nutzsite.common.page.TableDataInfo;
import io.nutz.nutzsite.common.utils.ShiroUtils;
import io.nutz.nutzsite.module.sys.services.UserService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean
@At("/oa/vacation")
@Ok("json:full")
public class OAVacationController {

    @Inject
    TaskService taskService;
    @Inject
    RepositoryService repositoryService;
    @Inject
    RuntimeService runtimeService;

    @Inject
    UserService userService;

    @Inject
    IdentityService identityService;


    @RequiresPermissions("oa:vacation:view")
    @At("")
    @Ok("th:/oa/vacation/vacation.html")
    public void index(HttpServletRequest req) {

    }

    @At
    public Object list(){
        List<Task> list = taskService.createTaskQuery().list();
        JSONArray arr = new JSONArray();
        for (Task task:list) {
            NutMap map = new NutMap();
            map.addv("name",task.getName())
                    .addv("assignee",task.getAssignee())
                    .addv("delegationState",task.getDelegationState())
                    .addv("createTime",task.getCreateTime())
                    .addv("user",userService.fetch(task.getAssignee()));

            arr.add(map);
        }


        return new TableDataInfo(arr, arr.size());
    }

    @At
    public Object addVacation(){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("days", new Integer(4));
        variables.put("startUserId",  ShiroUtils.getSysUserId());
        variables.put("jlId",  10);
        variables.put("zjId",  11);
        variables.put("hrId",  12);
        variables.put("userId", ShiroUtils.getSysUserId());
        //流程发起前设置发起人，记录在流程历史中
        Deployment deployment = repositoryService.createDeployment().name("请假流程").addClasspathResource("process/Vacation.bpmn").deploy();

       ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Vacation", variables);
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        System.err.println(task.getName());
        taskService.complete(task.getId(),variables);

        return "";
    }
}
