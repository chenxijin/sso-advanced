-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE if not exists `user`  (
                         `id` bigint(0) NOT NULL,
                         `create_id` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
                         `create_time` datetime(0) NOT NULL COMMENT '创建时间',
                         `update_id` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
                         `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                         `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
                         `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户',
                         `password` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
                         `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
                         `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
                         `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
                         `initial` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为内置',
                         `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用 1 启用 0 禁用',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '总后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`(`id`, `create_id`, `create_time`, `update_id`, `update_time`, `deleted`, `account`, `password`, `name`, `email`, `phone`, `initial`, `enable`) VALUES (1, 1, '2023-01-31 16:09:14', 1, '2023-01-31 16:09:19', b'0', 'ceshi', '$2a$10$0abCTZe0ysyclJ.41GtCreXvW8crUMGhp7yHUtXfsN9M4tdW3qv7.', '测试', NULL, NULL, b'0', b'1');
