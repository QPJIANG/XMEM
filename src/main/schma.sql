
-- user base info
CREATE  TABLE user_info(
  user_name varchar(30),
  user_password varchar(60)
);
-- security config
CREATE  TABLE security_role(
   uri varchar(128),
   role VARCHAR(30)
);
-- user role info
CREATE  TABLE user_role(
   user_name varchar(30),
   user_role VARCHAR(30)
);