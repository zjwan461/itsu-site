CREATE TABLE `td_account`
(
    `account_id`      bigint(20) NOT NULL PRIMARY KEY,
    `username`        varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
    `password`        char(32) COLLATE utf8mb4_unicode_ci    NOT NULL,
    `salt`            char(32) COLLATE utf8mb4_unicode_ci    NOT NULL,
    `name`            varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `last_login_time` datetime                               DEFAULT NULL
);

CREATE TABLE `td_account_role`
(
    `id`         bigint(20) NOT NULL PRIMARY KEY,
    `role_id`    bigint(20) DEFAULT NULL,
    `account_id` bigint(20) DEFAULT NULL
);

CREATE TABLE `td_role`
(
    `role_id` bigint(20) NOT NULL PRIMARY KEY,
    `name`    varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL
);

CREATE TABLE `td_role_permission`
(
    `id`            bigint(20) NOT NULL PRIMARY KEY,
    `role_id`       bigint(20) DEFAULT NULL,
    `permission_id` bigint(20) DEFAULT NULL
);

CREATE TABLE `td_permission`
(
    `permission_id` bigint(20) NOT NULL PRIMARY KEY,
    `name`          varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL
);