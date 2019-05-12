package io.nutz.nutzsite.module.dynamic.controller;

import io.nutz.nutzsite.module.dynamic.services.DynamicFormService;
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

    @At("")
    @Ok("th:/dynamic/form/form.html")
    @RequiresPermissions("dynamic:form:view")
    public void index(HttpServletRequest req) {

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
                       HttpServletRequest req) {
        Cnd cnd = Cnd.NEW();
        if (!Strings.isBlank(name)){
            //cnd.and("name", "like", "%" + name +"%");
        }
        return dynamicFormService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
    }


}
