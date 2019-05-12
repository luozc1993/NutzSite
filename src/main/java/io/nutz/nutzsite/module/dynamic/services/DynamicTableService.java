package io.nutz.nutzsite.module.dynamic.services;

import io.nutz.nutzsite.common.base.Service;
import io.nutz.nutzsite.module.dynamic.models.DynamicTable;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * 动态表管理
 *
 * @author zhicong
 * @date 2019-05-11
 */
@IocBean(args = {"refer:dao"})
public class DynamicTableService extends Service<DynamicTable> {
    public DynamicTableService(Dao dao) {
        super(dao);
    }
}
