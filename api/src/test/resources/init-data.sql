insert into chatdb.user(id, login_id, password, name) values (null, 'user01', '1234', '사용자1');
insert into chatdb.user(id, login_id, password, name) values (null, 'user02', '1234', '사용자2');
insert into chatdb.user(id, login_id, password, name) values (null, 'user03', '1234', '사용자3');
insert into chatdb.user(id, login_id, password, name) values (null, 'user04', '1234', '사용자4');

insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 1, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:19:29.014107', 1, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:19:30.014107', 1, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 2, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 2, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 3, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 3, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 4, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 4, null, 'ACTIVE');
insert into chatdb.chat_room(id, create_date, create_user_id, end_date, status) values (null, '2021-10-27T20:09:29.014107', 4, null, 'ACTIVE');

insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (1, 1);
insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (2, 1);

insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (1, 2);

insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (1, 3);

insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (2, 4);

insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (2, 5);
insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (3, 6);
insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (3, 7);
insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (4, 8);
insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (4, 9);
insert into chatdb.chat_room_user_mapping(user_id, chat_room_id) values (4, 10);

insert into chatdb.chat_message(id, send_time, send_user_id, context, chat_room_id) values (null, '2021-10-27T20:09:29.014107', 1, '{"text":"Open 메시지"}', 1);