SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for community_post
-- ----------------------------
DROP TABLE IF EXISTS `community_post`;
CREATE TABLE `community_post` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `author_id` varchar(36) NOT NULL COMMENT '作者ID',
  `author_name` varchar(100) DEFAULT NULL COMMENT '作者名称',
  `topic_id` varchar(36) DEFAULT NULL COMMENT '话题ID',
  `topic_name` varchar(100) DEFAULT NULL COMMENT '话题名称',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图',
  `images` text COMMENT '图片列表',
  `video_url` varchar(255) DEFAULT NULL COMMENT '视频链接',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览数',
  `type` tinyint(1) DEFAULT '1' COMMENT '类型(1-普通帖子,2-投票帖,3-问答帖)',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态(0-待审核,1-已发布,2-已拒绝)',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '是否置顶(0-否,1-是)',
  `is_essence` tinyint(1) DEFAULT '0' COMMENT '是否加精(0-否,1-是)',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_author_id` (`author_id`) USING BTREE,
  KEY `idx_topic_id` (`topic_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='社区帖子';

-- ----------------------------
-- Table structure for community_comment
-- ----------------------------
DROP TABLE IF EXISTS `community_comment`;
CREATE TABLE `community_comment` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `post_id` varchar(36) NOT NULL COMMENT '帖子ID',
  `content` text NOT NULL COMMENT '评论内容',
  `user_id` varchar(36) NOT NULL COMMENT '评论人ID',
  `user_name` varchar(100) DEFAULT NULL COMMENT '评论人名称',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父评论ID',
  `reply_id` varchar(36) DEFAULT NULL COMMENT '回复的评论ID',
  `reply_user_id` varchar(36) DEFAULT NULL COMMENT '回复的用户ID',
  `reply_user_name` varchar(100) DEFAULT NULL COMMENT '回复的用户名称',
  `images` text COMMENT '图片列表',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态(0-待审核,1-已发布,2-已拒绝)',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_post_id` (`post_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='社区评论';

-- ----------------------------
-- Table structure for community_topic
-- ----------------------------
DROP TABLE IF EXISTS `community_topic`;
CREATE TABLE `community_topic` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '话题名称',
  `description` varchar(500) DEFAULT NULL COMMENT '话题描述',
  `icon` varchar(255) DEFAULT NULL COMMENT '话题图标',
  `bg_image` varchar(255) DEFAULT NULL COMMENT '背景图片',
  `post_count` int(11) DEFAULT '0' COMMENT '帖子数量',
  `follow_count` int(11) DEFAULT '0' COMMENT '关注数量',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态(0-禁用,1-启用)',
  `is_recommend` tinyint(1) DEFAULT '0' COMMENT '是否推荐(0-否,1-是)',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_sort` (`sort`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='社区话题';

-- ----------------------------
-- Table structure for community_user_follow
-- ----------------------------
DROP TABLE IF EXISTS `community_user_follow`;
CREATE TABLE `community_user_follow` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  `follow_user_id` varchar(36) NOT NULL COMMENT '关注的用户ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_follow` (`user_id`,`follow_user_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户关注';

-- ----------------------------
-- Table structure for community_like
-- ----------------------------
DROP TABLE IF EXISTS `community_like`;
CREATE TABLE `community_like` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  `type` tinyint(1) NOT NULL COMMENT '点赞类型(1-帖子,2-评论)',
  `target_id` varchar(36) NOT NULL COMMENT '点赞对象ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_like` (`user_id`,`type`,`target_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='点赞记录';

-- ----------------------------
-- 初始化数据字典
-- ----------------------------
DELETE FROM sys_dict WHERE dict_code IN ('post_type', 'post_status', 'comment_status', 'topic_status');
DELETE FROM sys_dict_item WHERE dict_id IN (SELECT id FROM sys_dict WHERE dict_code IN ('post_type', 'post_status', 'comment_status', 'topic_status'));

-- 帖子类型字典
INSERT INTO sys_dict(id, dict_name, dict_code, description, del_flag, create_by, create_time, update_by, update_time, type)
VALUES ('1741234567890101', '帖子类型', 'post_type', '帖子类型', 0, 'admin', '2024-02-05 00:00:00', NULL, NULL, 0);

INSERT INTO sys_dict_item(id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time)
VALUES 
('1741234567890102', '1741234567890101', '普通帖子', '1', '普通帖子', 1, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL),
('1741234567890103', '1741234567890101', '投票帖', '2', '投票帖', 2, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL),
('1741234567890104', '1741234567890101', '问答帖', '3', '问答帖', 3, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL);

-- 帖子状态字典
INSERT INTO sys_dict(id, dict_name, dict_code, description, del_flag, create_by, create_time, update_by, update_time, type)
VALUES ('1741234567890105', '帖子状态', 'post_status', '帖子状态', 0, 'admin', '2024-02-05 00:00:00', NULL, NULL, 0);

INSERT INTO sys_dict_item(id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time)
VALUES 
('1741234567890106', '1741234567890105', '待审核', '0', '待审核', 1, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL),
('1741234567890107', '1741234567890105', '已发布', '1', '已发布', 2, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL),
('1741234567890108', '1741234567890105', '已拒绝', '2', '已拒绝', 3, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL);

-- 评论状态字典
INSERT INTO sys_dict(id, dict_name, dict_code, description, del_flag, create_by, create_time, update_by, update_time, type)
VALUES ('1741234567890109', '评论状态', 'comment_status', '评论状态', 0, 'admin', '2024-02-05 00:00:00', NULL, NULL, 0);

INSERT INTO sys_dict_item(id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time)
VALUES 
('1741234567890110', '1741234567890109', '待审核', '0', '待审核', 1, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL),
('1741234567890111', '1741234567890109', '已发布', '1', '已发布', 2, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL),
('1741234567890112', '1741234567890109', '已拒绝', '2', '已拒绝', 3, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL);

-- 话题状态字典
INSERT INTO sys_dict(id, dict_name, dict_code, description, del_flag, create_by, create_time, update_by, update_time, type)
VALUES ('1741234567890113', '话题状态', 'topic_status', '话题状态', 0, 'admin', '2024-02-05 00:00:00', NULL, NULL, 0);

INSERT INTO sys_dict_item(id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time)
VALUES 
('1741234567890114', '1741234567890113', '禁用', '0', '禁用', 1, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL),
('1741234567890115', '1741234567890113', '启用', '1', '启用', 2, 1, 'admin', '2024-02-05 00:00:00', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1; 