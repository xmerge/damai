# 观影人数据表
DROP TABLE IF EXISTS tb_audience;
CREATE TABLE tb_audience (
     id VARCHAR(255) NOT NULL COMMENT 'id',
     real_name VARCHAR(255) COMMENT '真实姓名',
     gender TINYINT COMMENT '性别',
     birthday DATETIME COMMENT '出生日期',
     bind_account JSON COMMENT '绑定账号',
     region VARCHAR(255) COMMENT '地区',
     id_type TINYINT COMMENT '证件类型',
     id_card VARCHAR(255) COMMENT '证件号码',
     phone_number VARCHAR(255) COMMENT '手机号码',
     user_type INT COMMENT '用户类型',
     del_flag INT DEFAULT 0 COMMENT '注销标志 0-未注销 1-已注销',
     create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     deletion_time DATETIME DEFAULT NULL COMMENT '注销时间',
     PRIMARY KEY  (id),
     UNIQUE KEY UK_id (id)
)  CHARACTER SET utf8mb4 COMMENT '观影人表';
