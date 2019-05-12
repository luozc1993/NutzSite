package io.nutz.nutzsite.module.dynamic.controller;

import io.nutz.nutzsite.common.base.Result;
import io.nutz.nutzsite.common.utils.ShiroUtils;
import io.nutz.nutzsite.module.dynamic.models.DynamicTable;
import io.nutz.nutzsite.module.dynamic.services.DynamicTableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;

;import java.util.Date;

/**
 * 动态表处理
 * 
 * @author zhicong
 * @date 2019-05-11
 */
@IocBean
@At("/dynamic/table")
public class DynamicTableController {
	private static final Log log = Logs.get();

	@Inject
	private DynamicTableService dynamicTableService;


	@RequiresPermissions("dynamic:table:view")
	@At("")
	@Ok("th:/dynamic/table/table.html")
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
		cnd.and("del_flag","=",false);
		return dynamicTableService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 新增表格页面
	 */
	@At("/add")
	@Ok("th:/dynamic/table/add.html")
	public void add( HttpServletRequest req) { }


	/**
	 * 新增保存表
	 */
	@RequiresPermissions("dynamic:table:add")
	@At
	@POST
	@Ok("json")
	@Slog(tag="动态表", after="新增保存动态表id=${args[0].id}")
	public Object addDo(@Param("..") DynamicTable dynamicTable, HttpServletRequest req) {
		try {
			dynamicTableService.insert(dynamicTable);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改字典
	 */
	@At("/edit/?")
	@Ok("th://dynamic/table/edit.html")
	public void edit(String id, HttpServletRequest req) {
		DynamicTable dynamicTable = dynamicTableService.fetch(id);
		req.setAttribute("dynamicTable",dynamicTable);
	}

	/**
	 * 修改保存动态表
	 */
	@RequiresPermissions("dynamic:table:edit")
	@At
	@POST
	@Ok("json")
	@Slog(tag="动态表", after="修改保存动态表")
	public Object editDo(@Param("..") DynamicTable dynamicTable,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(dynamicTable)){
				dynamicTable.setUpdateBy(ShiroUtils.getSysUserId());
				dynamicTable.setUpdateTime(new Date());
				dynamicTableService.update(dynamicTable);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除动态表
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("dynamic:table:remove")
	@Slog(tag ="动态表", after= "删除动态表:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			dynamicTableService.vDelete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
