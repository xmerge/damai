# 用户数据表
DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
     /** id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键', */
     id VARCHAR(255) NOT NULL COMMENT 'id',
     username VARCHAR(255) NOT NULL COMMENT '用户名',
     password VARCHAR(255) NOT NULL COMMENT '密码',
     real_name VARCHAR(255) COMMENT '真实姓名',
     region VARCHAR(255) COMMENT '地区',
     id_type TINYINT COMMENT '证件类型',
     id_card VARCHAR(255) COMMENT '证件号码',
     phone_number VARCHAR(255) COMMENT '手机号码',
     mail VARCHAR(255) COMMENT '邮箱',
     user_type INT COMMENT '用户类型',
     verify_status INT COMMENT '认证状态',
     del_flag INT DEFAULT 0 COMMENT '注销标志 0-未注销 1-已注销',
     create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     deletion_time DATETIME DEFAULT NULL COMMENT '注销时间',
     PRIMARY KEY  (id),
     UNIQUE KEY UK_username (username)
)  CHARACTER SET utf8mb4 COMMENT '用户表';
