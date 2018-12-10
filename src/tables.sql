CREATE TABLE `users`
(
    userId BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nickName VARCHAR(45) DEFAULT '' NOT NULL COMMENT '昵称',
    gender INT(4) NOT NULL DEFAULT 1 COMMENT '性别 1男 2女',
    description VARCHAR(500) DEFAULT '' NOT NULL COMMENT '个人描述',
    education VARCHAR(500) DEFAULT '暂无描述' NOT NULL COMMENT '教育经历',
    location VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '位置信息',
    headerThumb VARCHAR(200) DEFAULT '' NOT NULL COMMENT '头像',
    status INT(4) DEFAULT 0 NOT NULL COMMENT '用户状态 0正常',
    infoVerified tinyint(1) NOT NULL COMMENT '信息校验标记 0-未校验，1-已校验',
    userType INT(4) DEFAULT 0 NOT NULL COMMENT '用户类型 0 web用户',
    createdOn datetime NOT NULL DEFAULT '1970-1-1',
    modifiedBy bigint(20) NOT NULL DEFAULT '0',
    modifiedOn datetime NOT NULL DEFAULT '1970-1-1'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户表';


CREATE TABLE `user_authorization` (
  `userAuthorizationId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `userIdentity` varchar(50) NOT NULL,
  `tokenType` tinyint(4) NOT NULL DEFAULT '1' COMMENT '授权类别1.邮箱 2.QQ 3.微信',
  `token` varchar(200) NOT NULL DEFAULT '' COMMENT '授权取得的access_token',
  `createdBy` bigint(20) NOT NULL DEFAULT '0',
  `createdOn` datetime NOT NULL DEFAULT '1970-1-1',
  PRIMARY KEY (`userAuthorizationId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户授权表';

CREATE TABLE `user_login_history` (
  `userLoginHistoryRecordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `phoneType` tinyint(4) NOT NULL COMMENT '手机类型 1web 2 安卓 3ios',
  `version` varchar(255) NOT NULL COMMENT '版本',
  `deviceId` varchar(50) NOT NULL COMMENT '设备ID',
  `deviceType` varchar(255) NOT NULL COMMENT '设备类型',
  `ip` varchar(255) NOT NULL,
  `versionName` varchar(255) NOT NULL COMMENT '版本名称',
  `createdOn` datetime NOT NULL DEFAULT '1970-1-1',
  PRIMARY KEY (`userLoginHistoryRecordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录历史记录（仅保存上次）';

CREATE TABLE `user_label`
(
    userLabelId BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userId bigint(20) NOT NULL,
    labelNum INT(11) NOT NULL DEFAULT 1 COMMENT '标签编号',
    labelName VARCHAR(500) DEFAULT '' NOT NULL COMMENT '标签名字',
    status INT(4) DEFAULT 0 NOT NULL COMMENT ' 0正常 1删除',
    createdBy bigint(20) NOT NULL DEFAULT '0',
    createdOn datetime NOT NULL DEFAULT '1970-1-1',
    modifiedBy bigint(20) NOT NULL DEFAULT '0',
    modifiedOn datetime NOT NULL DEFAULT '1970-1-1'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户标签表';

CREATE TABLE `jobs`
(
    jobId BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    enterpriseId bigint(20) NOT NULL COMMENT '发布该职位的企业id',
    name VARCHAR (100) NOT NULL COMMENT '职位名称' ,
    location VARCHAR (100) NOT NULL COMMENT '职位位置',
    salary BIGINT(20) NOT NULL COMMENT '薪水描述',
    description VARCHAR (1000) NOT NULL COMMENT '职位描述',
    locationType int(4)DEFAULT 0 NOT NULL COMMENT ' 0不限，1 上海，2 北京 ，3广州，4 杭州 ',
    educationType int(4)DEFAULT 0 NOT NULL COMMENT ' 0不要求，1 大专，2 本科 ，3硕士，4 博士 ',
    fieldType int(4)DEFAULT 0 NOT NULL COMMENT ' 0不限，1 金融， 2 IT，3 行政， 4 文娱 ',
    experienceType int(4)DEFAULT 0 NOT NULL COMMENT ' 0不限，1 3年以下， 2 3-5年，3 5-10年， 4 10年以上 ',
    publishDate datetime NOT NULL DEFAULT '1970-1-1',
    status INT(4) DEFAULT 0 NOT NULL COMMENT ' 0正常 ',
    createdBy bigint(20) NOT NULL DEFAULT '0',
    createdOn datetime NOT NULL DEFAULT '1970-1-1',
    modifiedBy bigint(20) NOT NULL DEFAULT '0',
    modifiedOn datetime NOT NULL DEFAULT '1970-1-1'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='职位';

CREATE TABLE `enterprises`
(
    enterpriseId BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    icon VARCHAR (200) NOT NULL DEFAULT '' COMMENT '公司图标',
    name VARCHAR (100) NOT NULL DEFAULT '暂无描述' COMMENT '企业名称',
    location VARCHAR (100) NOT NULL DEFAULT '暂无描述' COMMENT '企业位置',
    description VARCHAR (1000) NOT NULL DEFAULT '暂无描述' COMMENT '企业描述',
    telphoneNum VARCHAR (50) NOT NULL DEFAULT '0000' COMMENT '电话号码',
    founderUserId bigint(20) NOT NULL COMMENT '创始人Id',
    infoVerified tinyint(1) NOT NULL COMMENT '信息校验标记 0-未校验，1-已校验',
    status INT(4) DEFAULT 0 NOT NULL COMMENT ' 0正常 ',
    createdBy bigint(20) NOT NULL DEFAULT '0',
    createdOn datetime NOT NULL DEFAULT '1970-1-1',
    modifiedBy bigint(20) NOT NULL DEFAULT '0',
    modifiedOn datetime NOT NULL DEFAULT '1970-1-1'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='企业';

CREATE TABLE `friend_request`
(
    friendRequestId BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    fromUserId bigint(20) NOT NULL COMMENT ' 发送方',
    toUserId bigint(20) NOT NULL COMMENT ' 接收方',
    status INT(4) DEFAULT 0 NOT NULL COMMENT ' 0正常 1同意 2忽略',
    createdBy bigint(20) NOT NULL DEFAULT '0',
    createdOn datetime NOT NULL DEFAULT '1970-1-1',
    modifiedBy bigint(20) NOT NULL DEFAULT '0',
    modifiedOn datetime NOT NULL DEFAULT '1970-1-1'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='好友请求表';

CREATE TABLE `user_job_post_record`
(
    userJobPostRecord BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userId bigint(20) NOT NULL COMMENT '投递人',
    jobId bigint(20) NOT NULL COMMENT '职位id',
    status INT(4) DEFAULT 0 NOT NULL COMMENT ' 1 初审中 2 通知面试 3 不合适 4面试通过 5 已过期 ',
    createdBy bigint(20) NOT NULL DEFAULT '0',
    createdOn datetime NOT NULL DEFAULT '1970-1-1',
    modifiedBy bigint(20) NOT NULL DEFAULT '0',
    modifiedOn datetime NOT NULL DEFAULT '1970-1-1'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='职位投递表';

CREATE TABLE `enterprise_image`
(
    enterpriseImageId BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    enterpriseId bigint(20) NOT NULL COMMENT '企业id',
    imageLocation VARCHAR(200) DEFAULT '' NOT NULL COMMENT '图片地址',
    status INT(4) DEFAULT 0 NOT NULL COMMENT '0正常 ',
    createdBy bigint(20) NOT NULL DEFAULT '0',
    createdOn datetime NOT NULL DEFAULT '1970-1-1',
    modifiedBy bigint(20) NOT NULL DEFAULT '0',
    modifiedOn datetime NOT NULL DEFAULT '1970-1-1'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='企业介绍图';

CREATE TABLE `resume`
(
    resumeId BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userId BIGINT(20) NOT NULL COMMENT '对应用户id',
    realName VARCHAR(100) NOT NULL COMMENT '真实姓名',
    birthDay VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '生日',
    employmentLength VARCHAR(200) DEFAULT '无工作经历' NOT NULL COMMENT '工作年限',
    phoneNum VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '电话号码',
    email VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '联系邮箱',
    schoolName VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '毕业学校',
    major VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '专业',
    schoolTime VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '学习时间',
    highestEducation VARCHAR(200) DEFAULT '暂无描述' NOT NULL COMMENT '最高学历',
    workExperience VARCHAR(500) DEFAULT '暂无描述' NOT NULL COMMENT '工作经历',
    projectExperience VARCHAR(500) DEFAULT '暂无描述' NOT NULL COMMENT '项目（学习）经历',
    certificate VARCHAR(500) DEFAULT '暂无描述' NOT NULL COMMENT '证书信息',
    description VARCHAR(500) DEFAULT '暂无描述' NOT NULL COMMENT '自我描述',
    STATUS INT(4) DEFAULT 0 NOT NULL COMMENT '0正常 ',
    createdBy BIGINT(20) NOT NULL DEFAULT '0',
    createdOn DATETIME NOT NULL DEFAULT '1970-1-1',
    modifiedBy BIGINT(20) NOT NULL DEFAULT '0',
    modifiedOn DATETIME NOT NULL DEFAULT '1970-1-1'
)ENGINE=INNODB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='简历信息';

CREATE TABLE `private_message_record` (
  `privateMessageRecordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fromUserId` bigint(20) NOT NULL COMMENT '发送方',
  `toUserId` bigint(20) NOT NULL COMMENT '接收方',
  `messageId` varchar(80) NOT NULL COMMENT '消息ID',
  `messageOn` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '消息发送时间',
  `content` varchar(255) DEFAULT '' COMMENT '发送的文本内容.',
  PRIMARY KEY (`privateMessageRecordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信流水';


-- CREATE TABLE `system_notification_record` (
--   `systemNotificationRecordId` BIGINT(20) NOT NULL AUTO_INCREMENT,
--   `toUserId` BIGINT(20) NOT NULL COMMENT '接收方',
--   `messageId` VARCHAR(80) NOT NULL COMMENT '消息ID',
--    STATUS INT(4) DEFAULT 0 NOT NULL COMMENT '0正常 ',
--   `messageOn` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '消息发送时间',
--   `content` VARCHAR(255) DEFAULT '' COMMENT '发送的文本内容.',
--    createdBy BIGINT(20) NOT NULL DEFAULT '0',
--     createdOn DATETIME NOT NULL DEFAULT '1970-1-1',
--     modifiedBy BIGINT(20) NOT NULL DEFAULT '0',
--     modifiedOn DATETIME NOT NULL DEFAULT '1970-1-1',
--   PRIMARY KEY (`systemNotificationRecordId`)
-- ) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='系统通知';