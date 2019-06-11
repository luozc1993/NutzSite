package io.nutz.nutzsite.module.oa.controller;

import io.nutz.nutzsite.common.base.Result;
import io.nutz.nutzsite.common.page.TableDataInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@IocBean
@At("/oa/flow")
@Ok("json:full")
public class FlowController {
    @Inject
    RepositoryService repositoryService;

    @RequiresPermissions("oa:flow:view")
    @At("")
    @Ok("th:/oa/flow/flow.html")
    public void index(HttpServletRequest req) {

    }


    @At
    public Object list(String name,int pageSize,int pageNum){
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        if(Strings.isNotBlank(name)){
            //使用流程定义的名称模糊查询
            definitionQuery.processDefinitionNameLike(name==null?"":name);
        }
        List<ProcessDefinition> list = definitionQuery.listPage((pageNum-1)*pageSize, pageNum*pageSize);//分页查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
               // .processDefinitionKey("Vacation")//使用流程定义的KEY查询

                //.list();//返回一个集合列表，封装流程定义
                //.singleResult();//返回唯一结果集
                //.count();//返回结果集数量

        return  new TableDataInfo(list, list.size());
    }
}
