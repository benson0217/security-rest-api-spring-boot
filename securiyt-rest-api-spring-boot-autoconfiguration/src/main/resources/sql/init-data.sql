insert into users(username, password, enabled) values ('nuser', 'password', 1);
insert into users(username, password, enabled) values ('auser', 'password', 1);

insert into groups(id, group_name) values(1, 'Admins');
insert into groups(id, group_name) values(2, 'User');
insert into group_authorities(group_id, authority) values (2, 'ROLE_USER');
insert into group_authorities(group_id, authority) values (1, 'ROLE_ADMIN');
insert into group_authorities(group_id, authority) values (1, 'ROLE_USER');
insert into group_members(id, username, group_id) values(1, 'nuser', 2);
insert into group_members(id, username, group_id) values(2, 'auser', 1);
