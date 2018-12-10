ALTER TABLE `job_offer`.`user_authorization` ADD INDEX `userId_i_ua` (`userId`);
ALTER TABLE `job_offer`.`user_authorization` ADD CONSTRAINT `fk_uid_ua` FOREIGN KEY (`userId`) REFERENCES `job_offer`.`users`(`userId`);

ALTER TABLE `job_offer`.`user_login_history` ADD CONSTRAINT `fk_uid_ulh` FOREIGN KEY (`userId`) REFERENCES `job_offer`.`users`(`userId`);
ALTER TABLE `job_offer`.`user_login_history` ADD INDEX `userId_i_ulh` (`userId`);

ALTER TABLE `job_offer`.`user_label` ADD INDEX `userId_i_ul` (`userId`);
ALTER TABLE `job_offer`.`user_label` ADD CONSTRAINT `fk_uid_ul` FOREIGN KEY (`userId`) REFERENCES `job_offer`.`users`(`userId`);

ALTER TABLE `job_offer`.`jobs` ADD INDEX `enterpriseId_i` (`enterpriseId`);
ALTER TABLE `job_offer`.`jobs` ADD CONSTRAINT `fk_eid_j` FOREIGN KEY (`enterpriseId`) REFERENCES `job_offer`.`enterprises`(`enterpriseId`);


ALTER TABLE `job_offer`.`enterprises` ADD INDEX `founder_uid_i` (`founderUserId`);

ALTER TABLE `job_offer`.`friend_request`
ADD CONSTRAINT `fk_fuid_fq` FOREIGN KEY (`fromUserId`)
 REFERENCES `job_offer`.`users`(`userId`),
ADD CONSTRAINT `fk_tuid_fq` FOREIGN KEY (`toUserId`) REFERENCES `job_offer`.`users`(`userId`);
ALTER TABLE `job_offer`.`friend_request` ADD INDEX `toUserId_i_fq` (`toUserId`);

ALTER TABLE `job_offer`.`user_job_post_record`
ADD CONSTRAINT `fk_uid_ujpr` FOREIGN KEY (`userId`)
REFERENCES `job_offer`.`users`(`userId`), ADD CONSTRAINT
`fk_jid_ujpr` FOREIGN KEY (`jobId`) REFERENCES `job_offer`.`jobs`(`jobId`);


ALTER TABLE `job_offer`.`enterprise_image` ADD INDEX `enterpriseId_i` (`enterpriseId`);
ALTER TABLE `job_offer`.`enterprise_image` ADD CONSTRAINT `fk_enterpriseId` FOREIGN KEY (`enterpriseId`) REFERENCES `job_offer`.`enterprises`(`enterpriseId`);


ALTER TABLE `job_offer`.`resume` ADD UNIQUE INDEX `userId_uni` (`userId`);

ALTER TABLE `job_offer`.`resume` ADD CONSTRAINT `fk_uid` FOREIGN KEY (`userId`) REFERENCES `job_offer`.`users`(`userId`);








