-- ----------------------------
-- Table structure for mall_goods_review
-- ----------------------------
DROP TABLE IF EXISTS `mall_goods_review`;
CREATE TABLE `mall_goods_review` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `rating` tinyint(1) NOT NULL COMMENT '评分(1-5)',
  `content` text COMMENT '评价内容',
  `images` text COMMENT '评价图片',
  `reply` text COMMENT '商家回复',
  `anonymous` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否匿名(0-否,1-是)',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(0-待审核,1-已通过,2-已拒绝)',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_goods_id` (`goods_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评价';

-- ----------------------------
-- Records of mall_goods_review
-- ----------------------------

-- ----------------------------
-- 数据字典
-- ----------------------------
INSERT INTO `sys_dict`(`id`, `dict_name`, `dict_code`, `description`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `type`) 
VALUES ('1234567890', '评价匿名状态', 'review_anonymous', '评价匿名状态', 0, 'admin', '2024-01-01 00:00:00', NULL, NULL, 0);

INSERT INTO `sys_dict_item`(`id`, `dict_id`, `item_text`, `item_value`, `description`, `sort_order`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) 
VALUES 
('1234567891', '1234567890', '不匿名', '0', '不匿名', 1, 1, 'admin', '2024-01-01 00:00:00', NULL, NULL),
('1234567892', '1234567890', '匿名', '1', '匿名', 2, 1, 'admin', '2024-01-01 00:00:00', NULL, NULL);

INSERT INTO `sys_dict`(`id`, `dict_name`, `dict_code`, `description`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `type`) 
VALUES ('1234567893', '评价状态', 'review_status', '评价状态', 0, 'admin', '2024-01-01 00:00:00', NULL, NULL, 0);

INSERT INTO `sys_dict_item`(`id`, `dict_id`, `item_text`, `item_value`, `description`, `sort_order`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) 
VALUES 
('1234567894', '1234567893', '待审核', '0', '待审核', 1, 1, 'admin', '2024-01-01 00:00:00', NULL, NULL),
('1234567895', '1234567893', '已通过', '1', '已通过', 2, 1, 'admin', '2024-01-01 00:00:00', NULL, NULL),
('1234567896', '1234567893', '已拒绝', '2', '已拒绝', 3, 1, 'admin', '2024-01-01 00:00:00', NULL, NULL); 