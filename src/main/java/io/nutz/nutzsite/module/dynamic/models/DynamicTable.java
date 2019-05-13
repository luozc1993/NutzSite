package io.nutz.nutzsite.module.dynamic.models;

import io.nutz.nutzsite.common.base.BaseModel;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * 动态表
 * @author zhicong
 */
@org.nutz.dao.entity.annotation.Table("dynamic_table")
public class DynamicTable extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    @Name
    @Comment("ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @Prev(els = {@EL("uuid()")})
    private String id;

    /**
     * 表名称
     */
    @Column("table_name")
    @Comment("表格名称")
    private String tableName;

    /**
     * 表名称
     */
    @Column("name")
    @Comment("名称")
    private String name;

    /**
     * 表说明
     */
    @Column("remarks")
    @Comment("表格名称")
    private String remarks;


    /**
     * 删除标记
     */
    @Column("del_flag")
    @Comment("删除标记 ")
    private boolean delFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }
}
