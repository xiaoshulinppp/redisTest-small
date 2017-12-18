-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT ,
  `name` varchar(255) NOT NULL DEFAULT '' ,
  `password` varchar(255) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12059 DEFAULT CHARSET=utf8 ;

