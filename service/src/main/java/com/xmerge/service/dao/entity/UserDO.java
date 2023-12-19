package com.xmerge.service.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-19
 */
@Getter
@Setter
@TableName("tb_user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 地区
     */
    @TableField("region")
    private String region;

    /**
     * 证件类型
     */
    @TableField("id_type")
    private Integer idType;

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
     * 邮箱
     */
    @TableField("mail")
    private String mail;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 认证状态
     */
    @TableField("verify_status")
    private Integer verifyStatus;

    /**
     * 删除标志 0-未删除 1-已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 删除时间
     */
    @TableField("deletion_time")
    private Date deletionTime;

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
}
