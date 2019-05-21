package io.nutz.nutzsite.module.dynamic.controller;

import io.nutz.nutzsite.common.base.Result;
import io.nutz.nutzsite.module.dynamic.models.DynamicForm;
import io.nutz.nutzsite.module.dynamic.models.DynamicTable;
import io.nutz.nutzsite.module.dynamic.services.DynamicFormService;
import io.nutz.nutzsite.module.dynamic.services.DynamicTableService;
import io.nutz.nutzsite.module.sys.services.DeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import javax.servlet.http.HttpServletRequest;

/**
 * 部门控制类
 * @author haiming
 */
@IocBean
@At("/dynamic/form")
public class DynamicFormController {
    private static final Log log = Logs.get();

    @Inject
    DynamicFormService dynamicFormService;
    @Inject
    DynamicTableService dynamicTableService;

    /**
     * 新增表格页面
     */
    @At("/add/?")
    @Ok("th:/dynamic/form/add.html")
    public void add(String table_id,HttpServletRequest req) {
        req.setAttribute("table_id",table_id);
    }

    /**
     * 修改字典
     */
    @At("/edit/?")
    @Ok("th://dynamic/form/edit.html")
    public void edit(String id, HttpServletRequest req) {
        DynamicTable dynamicTable = dynamicTableService.fetch(id);
        req.setAttribute("dynamicTable",dynamicTable);
    }


    @At("/report/?")
    @Ok("th:/dynamic/form/form.html")
    @RequiresPermissions("dynamic:form:view")
    public void index(String table_id,HttpServletRequest req) {
        DynamicTable dynamicTable = dynamicTableService.fetch(table_id);
        req.setAttribute("table_id",table_id);
    }

    /**
     * 查询动态表格列表
     */
    @RequiresPermissions("dynamic:table:list")
    @At("/list/?")
    @Ok("json")
    public Object list(@Param("table_id") String table_id,
                        @Param("pageNum")int pageNum,
                       @Param("pageSize")int pageSize,
                       @Param("name") String name,
                       @Param("orderByColumn") String orderByColumn,
                       @Param("isAsc") String isAsc,

                       HttpServletRequest req) {
        Cnd cnd = Cnd.NEW();
        if (!Strings.isBlank(name)){
            //cnd.and("name", "like", "%" + name +"%");
        }
        cnd.and("table_id","=",table_id);
        return dynamicFormService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
    }

    /**
     * 添加
     */
    @RequiresPermissions("dynamic:table:add")
    @At("/add")
    @Ok("json")
    public Object add(DynamicForm dynamicForm,HttpServletRequest req) {
        DynamicForm data = dynamicFormService.insert(dynamicForm);
        if(data!=null){
            return Result.success("system.success", data);
        }
        return Result.error("system.error");
    }

}
