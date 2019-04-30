alter table train_info add ava_ticket int NOT NULL default 100 comment '剩余车票';
insert into ticket(userName, ticketNum) VALUES ('admin',20);

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
                          `userId` int(11) NOT NULL auto_increment,
                          `userName` varchar(20) default NULL,
                          `password` varchar(20) default NULL,
                          PRIMARY KEY  (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET= utf8;