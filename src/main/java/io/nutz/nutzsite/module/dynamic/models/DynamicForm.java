package io.nutz.nutzsite.module.dynamic.models;

import io.nutz.nutzsite.common.base.BaseModel;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * 动态表
 * @author zhicong
 */
@Table("dynamic_form")
public class DynamicForm extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    @Name
    @Comment("ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @Prev(els = {@EL("uuid()")})
    private String id;

    /**
     * 字段标识
     */
    @Column("form_key")
    @Comment("字段标识")
    private String formKey;

    /**
     * 字段类型
     */
    @Column("form_type")
    @Comment("字段类型")
    private String formType;

    /**
     * 字段标签
     */
    @Column("label")
    @Comment("字段标签")
    private String label;

    /**
     * 字段占位符
     */
    @Column("tips")
    @Comment("字段占位符")
    private String tips;

    /**
     * 字段默认值
     */
    @Column("value")
    @Comment("字段默认值")
    private String value;

    /**
     * 字段行占比
     */
    @Column("字段行占比")
    @Comment("字段类型")
    private String col;

    /**
     * 字段权限0编辑1查看2隐藏
     */
    @Column("power")
    @Comment("字段权限0编辑1查看2隐藏")
    private String power;

    /**
     * 字段行偏移量
     */
    @Column("offset")
    @Comment("字段行偏移量")
    private String offset;

    /**
     * 是否必填
     */
    @Column("isRequired")
    @Comment("是否必填")
    private Boolean isRequired;

    /**
     * 选择值
     */
    @Column("options")
    @Comment("选择值")
    private String options;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
