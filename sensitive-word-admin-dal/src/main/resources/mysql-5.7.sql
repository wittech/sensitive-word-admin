create database sensitive_word;
use sensitive_word;

-- ------------------------------
-- ---------- MVP
-- 1. 用户管理
-- 2. 审批流程
-- 3. 操作日志
-- 4. 变更日志
-- 5. 定时生效
-- 6. 导入导出
-- 7. 敏感词规则（实时生效）
-- 9. 部署服务
-- 10. 数据大盘

-- 对外接口（GateWay）
-- 核心：提供对应的服务即可。
-- 外部应用的统一管理
-- 接口的权限控制
-- 接口的限次限流
-- ------------------------------

-- ------------ 核心表
-- tag 标签
-- word 单词
-- word_log 单词操作日志
-- 考虑后期支持敏感词级别 分类等

create table word
(
    id int unsigned auto_increment comment '应用自增主键' primary key,
    word varchar(128) not null comment '单词',
    type varchar(8) not null comment '敏感词类型。ALLOW:允许;DENY:禁止;',
    status char(1) not null default 'S' comment '单词状态。S:启用;F:禁用',
    remark varchar(64) not null comment '配置描述' default '',
    operator_id varchar(64) not null default 'system' comment '操作员名称',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间戳',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间戳'
) comment '敏感词表' ENGINE=Innodb default charset=UTF8 auto_increment=1;
create unique index uk_word on word (word) comment '唯一索引';

create table word_log
(
    id int unsigned auto_increment comment '应用自增主键' primary key,
    batch_id varchar(128) not null comment '批次号',
    word varchar(128) not null comment '单词',
    type varchar(8) not null comment '敏感词类型。ALLOW:允许;DENY:禁止;',
    status char(1) not null default 'S' comment '单词状态。S:启用;F:禁用',
    remark varchar(64) not null comment '配置描述' default '',
    operator_id varchar(64) not null default 'system' comment '操作员名称',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间戳',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间戳'
) comment '敏感词操作日志表' ENGINE=Innodb default charset=UTF8 auto_increment=1;
create index ix_word on word_log (word) comment '单词普通索引';
create index ix_batch_id on word_log (batch_id) comment '批次号普通索引';

create table tag
(
    id int unsigned auto_increment comment '应用自增主键' primary key,
    tag_code varchar(128) not null comment '标签编码',
    tag_label varchar(128) not null comment '标签描述',
    status char(1) not null default 'S' comment '单词状态。S:启用;F:禁用',
    remark varchar(64) not null comment '配置描述' default '',
    operator_id varchar(64) not null default 'system' comment '操作员名称',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间戳',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间戳'
) comment '标签表' ENGINE=Innodb default charset=UTF8 auto_increment=1;
create unique index uk_tag_code on tag (tag_code) comment '标签标识唯一索引';

create table word_tag_mapping
(
    id int unsigned auto_increment comment '应用自增主键' primary key,
    word varchar(128) not null comment '单词信息',
    tag_code varchar(128) not null comment '标签编码',
    operator_id varchar(64) not null default 'system' comment '操作员名称',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间戳',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间戳'
) comment '标签单词映射表' ENGINE=Innodb default charset=UTF8 auto_increment=1;
create unique index uk_word_tag_mapping on word_tag_mapping (word, tag_code) comment '标签单词映射唯一索引';







