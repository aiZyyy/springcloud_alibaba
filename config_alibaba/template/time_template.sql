-- 创建时间和修改时间自动变化
ALTER TABLE `gateway_route` ADD COLUMN `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
ALTER TABLE `gateway_route` ADD COLUMN `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间';


ALTER TABLE `gateway_route` MODIFY COLUMN `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
ALTER TABLE `gateway_route` MODIFY COLUMN `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间';

-- 字符集矫正
show variables like 'character_set_%';
show variables like 'collation_%';

set character_set_database =utf8;
set character_set_results =utf8;
set character_set_server =utf8;
set character_set_system =utf8;

SET collation_server = utf8_general_ci
SET collation_database = utf8_general_ci

ALTER TABLE common_keys CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;