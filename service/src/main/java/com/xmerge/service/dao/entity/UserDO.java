package com.xmerge.service.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("tb_user")
public class UserDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

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
     * 注销标志 0-未注销 1-已注销
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 注销时间
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

    @Override
    public String toString() {
        return "UserDO{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", realName=" + realName +
        ", region=" + region +
        ", idType=" + idType +
        ", idCard=" + idCard +
        ", phoneNumber=" + phoneNumber +
        ", mail=" + mail +
        ", userType=" + userType +
        ", verifyStatus=" + verifyStatus;
    }
}
