-- ----------------------------
-- 1. 跨境商品相关表
-- ----------------------------

-- 商品国际化信息表
CREATE TABLE mall_goods_i18n (
    id BIGINT NOT NULL COMMENT '主键ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    language VARCHAR(10) NOT NULL COMMENT '语言代码(zh-CN,en-US等)',
    name VARCHAR(200) NOT NULL COMMENT '商品名称',
    subtitle VARCHAR(500) COMMENT '副标题',
    description TEXT COMMENT '商品描述',
    detail TEXT COMMENT '商品详情',
    cultural_story TEXT COMMENT '文化故事',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_goods_lang (goods_id, language)
) COMMENT='商品国际化信息表';

-- 商品供应链信息表
CREATE TABLE mall_goods_supply_chain (
    id BIGINT NOT NULL COMMENT '主键ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    supplier_id BIGINT NOT NULL COMMENT '供应商ID',
    origin_country VARCHAR(50) NOT NULL COMMENT '原产国',
    production_date DATE COMMENT '生产日期',
    shelf_life INT COMMENT '保质期(天)',
    customs_code VARCHAR(50) COMMENT '海关编码',
    certification_info TEXT COMMENT '认证信息JSON',
    logistics_info TEXT COMMENT '物流信息JSON',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='商品供应链信息表';

-- 供应商信息表
CREATE TABLE mall_supplier (
    id BIGINT NOT NULL COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '供应商名称',
    country VARCHAR(50) NOT NULL COMMENT '所在国家',
    contact_name VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    contact_email VARCHAR(100) COMMENT '联系邮箱',
    business_license VARCHAR(100) COMMENT '营业执照',
    qualification TEXT COMMENT '资质证书JSON',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='供应商信息表';

-- 国际物流表
CREATE TABLE mall_international_shipping (
    id BIGINT NOT NULL COMMENT '主键ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    tracking_number VARCHAR(50) COMMENT '物流跟踪号',
    shipping_method VARCHAR(50) COMMENT '物流方式',
    shipping_company VARCHAR(100) COMMENT '物流公司',
    from_country VARCHAR(50) COMMENT '起始国家',
    to_country VARCHAR(50) COMMENT '目的国家',
    customs_status TINYINT COMMENT '清关状态(0-未清关,1-清关中,2-已清关,3-清关失败)',
    customs_declaration_no VARCHAR(50) COMMENT '报关单号',
    estimated_delivery_time DATETIME COMMENT '预计送达时间',
    actual_delivery_time DATETIME COMMENT '实际送达时间',
    shipping_status TINYINT COMMENT '物流状态(1-待发货,2-已发货,3-运输中,4-已送达)',
    tracking_info TEXT COMMENT '物流跟踪信息JSON',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='国际物流表';

-- 跨境支付记录表
CREATE TABLE mall_cross_border_payment (
    id BIGINT NOT NULL COMMENT '主键ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    payment_no VARCHAR(32) NOT NULL COMMENT '支付流水号',
    payment_method VARCHAR(20) NOT NULL COMMENT '支付方式(PAYPAL,STRIPE等)',
    currency VARCHAR(10) NOT NULL COMMENT '支付货币',
    amount DECIMAL(10,2) NOT NULL COMMENT '支付金额',
    exchange_rate DECIMAL(10,4) COMMENT '汇率',
    status TINYINT NOT NULL COMMENT '支付状态(0-待支付,1-支付中,2-支付成功,3-支付失败)',
    payment_time DATETIME COMMENT '支付时间',
    callback_info TEXT COMMENT '回调信息',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_payment_no (payment_no)
) COMMENT='跨境支付记录表';

-- ----------------------------
-- 2. 社区国际化相关表
-- ----------------------------

-- 话题国际化表
CREATE TABLE community_topic_i18n (
    id BIGINT NOT NULL COMMENT '主键ID',
    topic_id BIGINT NOT NULL COMMENT '话题ID',
    language VARCHAR(10) NOT NULL COMMENT '语言代码',
    name VARCHAR(100) NOT NULL COMMENT '话题名称',
    description VARCHAR(500) COMMENT '话题描述',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_topic_lang (topic_id, language)
) COMMENT='话题国际化表';

-- 内容翻译表
CREATE TABLE community_content_translation (
    id BIGINT NOT NULL COMMENT '主键ID',
    content_id BIGINT NOT NULL COMMENT '内容ID',
    content_type TINYINT NOT NULL COMMENT '内容类型(1-帖子,2-评论)',
    language VARCHAR(10) NOT NULL COMMENT '语言代码',
    translated_content TEXT NOT NULL COMMENT '翻译内容',
    translator_type TINYINT COMMENT '翻译类型(1-机器翻译,2-人工翻译)',
    translator VARCHAR(32) COMMENT '翻译者',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_content_lang (content_id, content_type, language)
) COMMENT='内容翻译表';

-- KOL信息表
CREATE TABLE community_kol (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    nick_name VARCHAR(50) NOT NULL COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像',
    country VARCHAR(50) COMMENT '所在国家',
    languages VARCHAR(100) COMMENT '擅长语言',
    influence_areas VARCHAR(200) COMMENT '影响领域',
    fans_count INT DEFAULT 0 COMMENT '粉丝数',
    post_count INT DEFAULT 0 COMMENT '发帖数',
    avg_engagement_rate DECIMAL(5,2) COMMENT '平均互动率',
    cooperation_count INT DEFAULT 0 COMMENT '合作次数',
    rating DECIMAL(3,1) DEFAULT 5.0 COMMENT '评分',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_id (user_id)
) COMMENT='KOL信息表';

-- KOL合作记录表
CREATE TABLE community_kol_cooperation (
    id BIGINT NOT NULL COMMENT '主键ID',
    kol_id BIGINT NOT NULL COMMENT 'KOL ID',
    campaign_name VARCHAR(100) NOT NULL COMMENT '活动名称',
    cooperation_type TINYINT NOT NULL COMMENT '合作类型(1-带货,2-种草,3-直播)',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    budget DECIMAL(10,2) COMMENT '预算',
    performance_targets TEXT COMMENT '绩效目标JSON',
    actual_performance TEXT COMMENT '实际绩效JSON',
    status TINYINT DEFAULT 0 COMMENT '状态(0-待开始,1-进行中,2-已完成,3-已取消)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='KOL合作记录表';

-- 用户文化偏好表
CREATE TABLE user_cultural_preference (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    preferred_language VARCHAR(10) NOT NULL COMMENT '偏好语言',
    interested_countries VARCHAR(500) COMMENT '感兴趣的国家JSON',
    interested_categories VARCHAR(500) COMMENT '感兴趣的品类JSON',
    cultural_tags VARCHAR(500) COMMENT '文化标签JSON',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_id (user_id)
) COMMENT='用户文化偏好表';

-- 用户积分流水表
CREATE TABLE user_points_record (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    points INT NOT NULL COMMENT '积分变动值',
    type TINYINT NOT NULL COMMENT '类型(1-购物奖励,2-评价奖励,3-签到,4-邀请奖励)',
    description VARCHAR(200) COMMENT '描述',
    related_id VARCHAR(32) COMMENT '关联ID',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
) COMMENT='用户积分流水表';

-- 邀请记录表
CREATE TABLE user_invitation_record (
    id BIGINT NOT NULL COMMENT '主键ID',
    inviter_id VARCHAR(32) NOT NULL COMMENT '邀请人ID',
    invitee_id VARCHAR(32) NOT NULL COMMENT '被邀请人ID',
    invitation_code VARCHAR(20) NOT NULL COMMENT '邀请码',
    reward_status TINYINT DEFAULT 0 COMMENT '奖励状态(0-未奖励,1-已奖励)',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_invitee_id (invitee_id)
) COMMENT='邀请记录表'; 