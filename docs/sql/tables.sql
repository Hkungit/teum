-- ----------------------------
-- 1. 商城模块表结构
-- ----------------------------

-- 商品分类表
CREATE TABLE mall_category (
    id BIGINT NOT NULL COMMENT '主键ID',
    parent_id BIGINT COMMENT '父级ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    icon VARCHAR(100) COMMENT '分类图标',
    sort_no INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态(1-正常,0-禁用)',
    description VARCHAR(255) COMMENT '描述',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    sys_org_code VARCHAR(64) COMMENT '所属部门',
    PRIMARY KEY (id)
) COMMENT='商品分类表';

-- 商品信息表
CREATE TABLE mall_goods (
    id BIGINT NOT NULL COMMENT '主键ID',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    subtitle VARCHAR(200) COMMENT '副标题',
    main_image VARCHAR(500) COMMENT '主图',
    sub_images TEXT COMMENT '子图JSON数组',
    detail TEXT COMMENT '商品详情',
    price DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    stock INT NOT NULL COMMENT '库存数量',
    sale_count INT DEFAULT 0 COMMENT '销量',
    unit VARCHAR(20) COMMENT '单位',
    weight DECIMAL(10,2) COMMENT '重量(kg)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-下架,1-上架)',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热门(0-否,1-是)',
    is_new TINYINT DEFAULT 0 COMMENT '是否新品(0-否,1-是)',
    is_recommend TINYINT DEFAULT 0 COMMENT '是否推荐(0-否,1-是)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    sys_org_code VARCHAR(64) COMMENT '所属部门',
    PRIMARY KEY (id)
) COMMENT='商品信息表';

-- 商品规格表
CREATE TABLE mall_goods_specs (
    id BIGINT NOT NULL COMMENT '主键ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    spec_name VARCHAR(50) NOT NULL COMMENT '规格名称',
    spec_values TEXT NOT NULL COMMENT '规格值(JSON数组)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='商品规格表';

-- 商品SKU表
CREATE TABLE mall_goods_sku (
    id BIGINT NOT NULL COMMENT '主键ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    spec_map TEXT NOT NULL COMMENT '规格JSON键值对',
    price DECIMAL(10,2) NOT NULL COMMENT 'SKU价格',
    stock INT NOT NULL COMMENT 'SKU库存',
    sku_code VARCHAR(50) COMMENT 'SKU编码',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='商品SKU表';

-- 购物车表
CREATE TABLE mall_cart (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    sku_id BIGINT COMMENT 'SKU ID',
    quantity INT NOT NULL COMMENT '数量',
    checked TINYINT DEFAULT 1 COMMENT '是否选中(0-否,1-是)',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='购物车表';

-- 订单表
CREATE TABLE mall_order (
    id BIGINT NOT NULL COMMENT '主键ID',
    order_no VARCHAR(32) NOT NULL COMMENT '订单编号',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总额',
    pay_amount DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    freight_amount DECIMAL(10,2) COMMENT '运费',
    promotion_amount DECIMAL(10,2) COMMENT '促销优惠金额',
    coupon_amount DECIMAL(10,2) COMMENT '优惠券抵扣金额',
    pay_type TINYINT COMMENT '支付方式(1-微信,2-支付宝)',
    status TINYINT NOT NULL COMMENT '订单状态(0-已取消,1-待付款,2-待发货,3-待收货,4-已完成)',
    payment_time DATETIME COMMENT '支付时间',
    delivery_time DATETIME COMMENT '发货时间',
    receive_time DATETIME COMMENT '收货时间',
    comment_time DATETIME COMMENT '评价时间',
    receiver_name VARCHAR(32) NOT NULL COMMENT '收货人姓名',
    receiver_phone VARCHAR(32) NOT NULL COMMENT '收货人电话',
    receiver_province VARCHAR(32) COMMENT '省份',
    receiver_city VARCHAR(32) COMMENT '城市',
    receiver_district VARCHAR(32) COMMENT '区县',
    receiver_address VARCHAR(200) COMMENT '详细地址',
    note VARCHAR(500) COMMENT '订单备注',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_no (order_no)
) COMMENT='订单表';

-- 订单明细表
CREATE TABLE mall_order_item (
    id BIGINT NOT NULL COMMENT '主键ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    order_no VARCHAR(32) NOT NULL COMMENT '订单编号',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    sku_id BIGINT COMMENT 'SKU ID',
    goods_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    sku_specs TEXT COMMENT 'SKU规格JSON',
    goods_image VARCHAR(500) COMMENT '商品图片',
    price DECIMAL(10,2) NOT NULL COMMENT '商品单价',
    quantity INT NOT NULL COMMENT '购买数量',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '商品总价',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='订单明细表';

-- 优惠券表
CREATE TABLE mall_coupon (
    id BIGINT NOT NULL COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    type TINYINT NOT NULL COMMENT '优惠券类型(1-满减券,2-折扣券)',
    amount DECIMAL(10,2) COMMENT '金额/折扣',
    min_point DECIMAL(10,2) COMMENT '使用门槛',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    total INT COMMENT '发行数量',
    used INT DEFAULT 0 COMMENT '已使用数量',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='优惠券表';

-- ----------------------------
-- 2. 社区模块表结构
-- ----------------------------

-- 帖子表
CREATE TABLE community_post (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    images TEXT COMMENT '图片JSON数组',
    topic_id BIGINT COMMENT '话题ID',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    status TINYINT DEFAULT 1 COMMENT '状态(0-待审核,1-已发布,2-已删除)',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶(0-否,1-是)',
    is_essence TINYINT DEFAULT 0 COMMENT '是否精华(0-否,1-是)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='帖子表';

-- 话题表
CREATE TABLE community_topic (
    id BIGINT NOT NULL COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '话题名称',
    description VARCHAR(500) COMMENT '话题描述',
    icon VARCHAR(255) COMMENT '话题图标',
    post_count INT DEFAULT 0 COMMENT '帖子数',
    follow_count INT DEFAULT 0 COMMENT '关注数',
    sort_no INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='话题表';

-- 评论表
CREATE TABLE community_comment (
    id BIGINT NOT NULL COMMENT '主键ID',
    post_id BIGINT NOT NULL COMMENT '帖子ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    content TEXT NOT NULL COMMENT '评论内容',
    parent_id BIGINT COMMENT '父评论ID',
    reply_user_id VARCHAR(32) COMMENT '回复用户ID',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    status TINYINT DEFAULT 1 COMMENT '状态(0-待审核,1-已发布,2-已删除)',
    create_by VARCHAR(32) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    update_by VARCHAR(32) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT='评论表';

-- 用户关系表
CREATE TABLE community_user_relation (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    follow_user_id VARCHAR(32) NOT NULL COMMENT '关注的用户ID',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_follow (user_id, follow_user_id)
) COMMENT='用户关系表';

-- 点赞记录表
CREATE TABLE community_like_record (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    type TINYINT NOT NULL COMMENT '点赞类型(1-帖子,2-评论)',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_target (user_id, target_id, type)
) COMMENT='点赞记录表';

-- 收藏记录表
CREATE TABLE community_collect_record (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    post_id BIGINT NOT NULL COMMENT '帖子ID',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_post (user_id, post_id)
) COMMENT='收藏记录表';

-- 消息表
CREATE TABLE community_message (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '接收用户ID',
    sender_id VARCHAR(32) COMMENT '发送用户ID',
    type TINYINT NOT NULL COMMENT '消息类型(1-系统消息,2-点赞,3-评论,4-关注)',
    target_id BIGINT COMMENT '目标ID',
    content TEXT NOT NULL COMMENT '消息内容',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读(0-未读,1-已读)',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
) COMMENT='消息表';

-- 举报记录表
CREATE TABLE community_report (
    id BIGINT NOT NULL COMMENT '主键ID',
    user_id VARCHAR(32) NOT NULL COMMENT '举报用户ID',
    target_id BIGINT NOT NULL COMMENT '举报目标ID',
    type TINYINT NOT NULL COMMENT '举报类型(1-帖子,2-评论,3-用户)',
    reason VARCHAR(500) NOT NULL COMMENT '举报原因',
    status TINYINT DEFAULT 0 COMMENT '处理状态(0-待处理,1-已处理,2-已驳回)',
    handle_note VARCHAR(500) COMMENT '处理备注',
    handle_time DATETIME COMMENT '处理时间',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
) COMMENT='举报记录表'; 