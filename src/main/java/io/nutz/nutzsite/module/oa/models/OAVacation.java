package io.nutz.nutzsite.module.oa.models;

import io.nutz.nutzsite.common.base.BaseModel;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@Table("oa_vacation")
public class OAVacation extends BaseModel implements Serializable {

    @Name
    @Column("id")
    @Comment("编号 ")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @Prev(els = {@EL("uuid()")})
    private String id;


    /**
     * 栏目模块
     */
    @Column("module")
    @Comment("栏目模块 ")
    private String module;

}
