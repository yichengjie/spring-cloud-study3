create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256)  ,
  access_token_validity INTEGER  COMMENT '令牌有效期',
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BLOB,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication BLOB
);

create table oauth_code (
  code VARCHAR(256), authentication BLOB
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt DATETIME,
	lastModifiedAt DATETIME
);



--1. 客户端信息添加到数据库中
INSERT INTO oauth_client_details(
	client_id,resource_ids,client_secret,
	scope,authorized_grant_types,web_server_redirect_uri,
	authorities,access_token_validity,refresh_token_validity,
	additional_information,autoapprove)
VALUES('orderApp','order-server','$2a$10$Ge3r21Bckgy2LKkf29DGouE9WKmfSrWXUQ5l2Dh/toBOChNwe8Yay',
   'read,write', 'password', NULL,
   NULL, 3600, NULL,
   NULL,NULL
) ;

INSERT INTO oauth_client_details(
	client_id,resource_ids,client_secret,
	scope,authorized_grant_types,web_server_redirect_uri,
	authorities,access_token_validity,refresh_token_validity,
	additional_information,autoapprove)
VALUES('orderService','order-server','$2a$10$Zv1iXEcI/PiX8H0./PkYEOUAs109qBdWyqgemFnP9LA/rxgEmmTeu',
   'read', 'password', NULL,
   NULL, 3600, NULL,
   NULL,NULL
) ;


INSERT INTO oauth_client_details(
	client_id,resource_ids,client_secret,
	scope,authorized_grant_types,web_server_redirect_uri,
	authorities,access_token_validity,refresh_token_validity,
	additional_information,autoapprove)
VALUES('gateway','order-server','$2a$10$Zv1iXEcI/PiX8H0./PkYEOUAs109qBdWyqgemFnP9LA/rxgEmmTeu',
   'read,write', 'password', NULL,
   NULL, 3600, NULL,
   NULL,NULL
) ;
