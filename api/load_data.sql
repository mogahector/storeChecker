USE store_checker;

INSERT INTO `user` VALUES (1,'admin@email.com','admin','admin','$2a$12$fd/bqzlH6XVuHz2WChx/UOtV944nKd1WaEaykcAnDA.m/PHFrrGWS','admin');
INSERT INTO `role` VALUES (1,'ADMIN');
INSERT INTO `role` VALUES (2,'OWNER');
INSERT INTO `role` VALUES (3,'DISTRICT_MANAGER');
INSERT INTO `role` VALUES (4,'MANAGER');
INSERT INTO `user_role` VALUES (1,1);
