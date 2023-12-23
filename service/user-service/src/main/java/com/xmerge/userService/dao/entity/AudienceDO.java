package com.xmerge.userService.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 观影人表
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-23
 */
@Getter
@Setter
@TableName("tb_audience")
public class AudienceDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private String id;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 性别
     */
    @TableField("gender")
    private Byte gender;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 绑定账号
     */
    @TableField("bind_account")
    private String bindAccount;

    /**
     * 地区
     */
    @TableField("region")
    private String region;

    /**
     * 证件类型
     */
    @TableField("id_type")
    private Byte idType;

    /**
     * 证件号码
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 手机号码
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 注销标志 0-未注销 1-已注销
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 注销时间
     */
    @TableField("deletion_time")
    private Date deletionTime;
}
