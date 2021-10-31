DROP TABLE `chatdb`.`chat_message`;
DROP TABLE `chatdb`.`user`;
DROP TABLE `chatdb`.`chat_room`;
DROP TABLE `chatdb`.`chat_room_user_mapping`;

-- -----------------------------------------------------
-- Table `chatdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatdb`.`user` (
                                               `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                               `login_id` VARCHAR(45) NOT NULL COMMENT '로그인 ID',
                                               `password` VARCHAR(200) NOT NULL COMMENT '비밀번호 ',
                                               `token` VARCHAR(500) NULL COMMENT '토큰',
                                               `name` VARCHAR(45) NULL COMMENT '이름 ',
                                               PRIMARY KEY (`id`),
                                               UNIQUE INDEX `login_id_UNIQUE` (`login_id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatdb`.`chat_room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatdb`.`chat_room` (
                                                    `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                                    `create_user_id` INT NULL,
                                                    `create_date` DATETIME NULL,
                                                    `status` VARCHAR(10) NULL,
                                                    `end_date` DATETIME NULL,
                                                    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatdb`.`chat_room_user_mapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatdb`.`chat_room_user_mapping` (
                                                                 `user_id` INT NOT NULL,
                                                                 `chat_room_id` INT NOT NULL,
                                                                 PRIMARY KEY (`user_id`, `chat_room_id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatdb`.`chat_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatdb`.`chat_message` (
                                                       `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                                       `send_time` DATETIME NULL COMMENT '보내는 시간 ',
                                                       `send_user_id` INT NULL COMMENT '보내는자ID',
                                                       `context` TEXT NULL COMMENT '메시지 내용',
                                                       `chat_room_id` INT NOT NULL,
                                                       PRIMARY KEY (`id`),
                                                       INDEX `fk_chat_message_chat_room_idx` (`chat_room_id` ASC) VISIBLE,
                                                       CONSTRAINT `fk_chat_message_chat_room`
                                                           FOREIGN KEY (`chat_room_id`)
                                                               REFERENCES `chatdb`.`chat_room` (`id`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION)
    ENGINE = InnoDB;

