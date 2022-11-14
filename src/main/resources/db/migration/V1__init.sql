CREATE TABLE IF NOT EXISTS `worker_node` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
   `host_name` VARCHAR(64) NOT NULL COMMENT 'host name',
   `port` VARCHAR(64) NOT NULL COMMENT 'port',
   `type` INT(11) NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
   `launch_date` DATE NOT NULL COMMENT 'launch date',
   `modified` DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'modified time',
   `created` DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'created time',

   PRIMARY KEY(`id`)
) COMMENT='DB WorkerID Assigner for UID Generator', ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `link` (
    `id` bigint NOT NULL PRIMARY KEY,
    `merchant_id` bigint NOT NULL,
    `name` varchar(255) NOT NULL,
    `description` text NOT NULL,
    -- according to: https://stackoverflow.com/questions/46658847/crypto-currency-mysql-datatypes
    `amount` DECIMAL(27,18),
    `currency` varchar(255) NOT NULL,
    `require_customer_name` boolean NOT NULL,
    `require_customer_email` boolean NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `link_order` (
    `id` bigint NOT NULL PRIMARY KEY,
    `link_id` bigint NOT NULL,
    `merchant_id` bigint NOT NULL,
    `amount` DECIMAL(27,18),
    `currency` varchar(255) NOT NULL,
    `customer_name` varchar(255),
    `customer_email` varchar(255),
    `order_meta` varchar(255) NOT NULL,
    `state` varchar(255) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `merchant` (
    `id` bigint NOT NULL PRIMARY KEY,
    `name` varchar(255) NOT NULL,
    `address` varchar(255) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP
);

-- auth
create table if not exists  oauth_client_details (
    client_id varchar(255) not null,
    client_secret varchar(255) not null,
    web_server_redirect_uri varchar(2048) default null,
    scope varchar(255) default null,
    access_token_validity int(11) default null,
    refresh_token_validity int(11) default null,
    resource_ids varchar(1024) default null,
    authorized_grant_types varchar(1024) default null,
    authorities varchar(1024) default null,
    additional_information varchar(4096) default null,
    autoapprove varchar(255) default null,
    primary key (client_id)
) engine=innodb ;

create table if not exists `user` (
    id int(11) not null auto_increment,
    email varchar(1024) not null,
    password varchar(1024) not null,
    merchant_id BIGINT not null,
    `state` varchar(255) not null,
    `authorities` varchar(1024) not null DEFAULT 'ROLE_USER',
    `created_at`  datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
    `updated_at`  datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
    primary key (id),
    unique key email (email)
) engine=innodb ;

create table if not exists oauth_access_token (
    token_id VARCHAR(256),
    token LONG VARBINARY,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name VARCHAR(256),
    client_id VARCHAR(256),
    authentication LONG VARBINARY,
    refresh_token VARCHAR(256)
);

create table if not exists oauth_refresh_token (
    token_id VARCHAR(256),
    token LONG VARBINARY,
    authentication LONG VARBINARY
);
