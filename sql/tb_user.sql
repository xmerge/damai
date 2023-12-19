DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
     id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
     username VARCHAR(255) NOT NULL COMMENT '用户名',
     password VARCHAR(255) NOT NULL COMMENT '密码',
     real_name VARCHAR(255) COMMENT '真实姓名',
     region VARCHAR(255) COMMENT '地区',
     id_type INT COMMENT '证件类型',
     id_card VARCHAR(255) COMMENT '证件号码',
     phone_number VARCHAR(255) COMMENT '手机号码',
     mail VARCHAR(255) COMMENT '邮箱',
     user_type INT COMMENT '用户类型',
     verify_status INT COMMENT '认证状态',
     del_flag INT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
     deletion_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
     create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (id),
     UNIQUE KEY UK_username (username)
)  CHARACTER SET utf8mb4 COMMENT '用户表';
