drop schema IF EXISTS gobet;
CREATE SCHEMA `gobet` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
use gobet;

/* Metadata */  
CREATE TABLE metadata (
  id int(11) NOT NULL AUTO_INCREMENT,
  lookupCode varchar(50) NOT NULL,
  lookupCodeId varchar(45) NOT NULL,
  lang varchar(5) NOT NULL default 'vn',
  value varchar(100) NOT NULL,
  orderBy varchar(5) NOT NULL default '1',
  PRIMARY KEY (id)
  );

/* User */
CREATE  TABLE users (
  username VARCHAR(50) NOT NULL ,
  password VARCHAR(500) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  master_username VARCHAR(50),
  designation_id int(11),
  PRIMARY KEY (username));
  
/* User Role */  
CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
  
  /* Client */
CREATE  TABLE client (
  id int(11) NOT NULL AUTO_INCREMENT,
  fullname VARCHAR(150) NOT NULL ,
  email VARCHAR(50) ,
  phoneNumber VARCHAR(20) ,
  gender TINYINT NOT NULL DEFAULT 1 ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  client_serve_user VARCHAR(50) NOT NULL,
  client_config VARCHAR(500) NOT NULL,
  PRIMARY KEY (id));
  
  
  
/* Insert data */   
INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_CHANNEL','mb', 'vn', 'Miền Bắc', '1');
INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_CHANNEL','hn', 'vn', 'Miền Bắc', '1');
INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_CHANNEL','hanoi', 'vn', 'Miền Bắc', '1');

INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_CHANNEL','hcm', 'vn', 'Hồ Chí Minh', '2');
INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_CHANNEL','tp', 'vn', 'Hồ Chí Minh', '2');

INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_DESIGNATION_CODE','admin', 'vn', 'admin', '0');
INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_DESIGNATION_CODE','agent', 'vn', 'agent', '1');

INSERT INTO metadata(lookupCode, lookupCodeId, lang, value, orderBy)
VALUES ('LOOKUP_CHANNEL','dt', 'vn', 'Đồng Tháp', '3');


INSERT INTO users(username,password,enabled)
VALUES ('admin','$2a$10$iGbH55CZksYYeRt99nuQ4.mI2mxdZxkPt9jS87AVm/CHqiVddmo7y', true);
INSERT INTO users(username,password,enabled)
VALUES ('mrbossi','$2a$10$UumZW/WzyJ0BlTK3NgISAe8BgPpWQIwbS9uwPaQW988LSP.oF4tHK', true);

INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('mrbossi', 'ROLE_USER');
