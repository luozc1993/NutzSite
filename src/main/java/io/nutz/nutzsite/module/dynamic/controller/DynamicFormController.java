package io.nutz.nutzsite.module.dynamic.controller;

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
    @At
    @Ok("json")
    public Object list(@Param("pageNum")int pageNum,
                       @Param("pageSize")int pageSize,
                       @Param("name") String name,
                       @Param("orderByColumn") String orderByColumn,
                       @Param("isAsc") String isAsc,
                       @Param("table_id") String table_id,
                       HttpServletRequest req) {
        Cnd cnd = Cnd.NEW();
        if (!Strings.isBlank(name)){
            //cnd.and("name", "like", "%" + name +"%");
        }
        cnd.and("table_id","=",table_id);
        return dynamicFormService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
    }


}
