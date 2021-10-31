CREATE SCHEMA IF NOT EXISTS chatdb;
-- -----------------------------------------------------
-- Table `chatdb`.`user`
-- -----------------------------------------------------
CREATE TABLE chatdb.user
(
    `id`       INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `login_id` VARCHAR(45)  NOT NULL UNIQUE COMMENT '로그인 ID',
    `password` VARCHAR(200) NOT NULL COMMENT '비밀번호 ',
    `token`    VARCHAR(500) NULL COMMENT '토큰',
    `name`     VARCHAR(45)  NULL COMMENT '이름 '
);

ALTER TABLE chatdb.user add primary key (`id`);

-- -----------------------------------------------------
-- Table `chatdb`.`chat_room`
-- -----------------------------------------------------
CREATE TABLE chatdb.chat_room
(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `create_user_id` INT NULL,
    `create_date` DATETIME NULL,
    `status` VARCHAR(10) NULL,
    `end_date` DATETIME NULL
);

ALTER TABLE chatdb.chat_room add primary key (`id`);

-- -----------------------------------------------------
-- Table `chatdb`.`chat_room_user_mapping`
-- -----------------------------------------------------
CREATE TABLE chatdb.chat_room_user_mapping
(
     `user_id` INT NOT NULL,
     `chat_room_id` INT NOT NULL
);

ALTER TABLE chatdb.chat_room_user_mapping add primary key (`user_id`, `chat_room_id`);
-- -----------------------------------------------------
-- Table `chatdb`.`chat_message`
-- -----------------------------------------------------
CREATE TABLE chatdb.chat_message (
       `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
       `send_time` DATETIME NULL COMMENT '보내는 시간 ',
       `send_user_id` INT NULL COMMENT '보내는자ID',
       `context` TEXT NULL COMMENT '메시지 내용',
       `chat_room_id` INT NOT NULL);

ALTER TABLE chatdb.chat_message add primary key (`id`);
ALTER TABLE chatdb.chat_message add FOREIGN KEY (`chat_room_id`) REFERENCES chatdb.chat_room(`id`);

