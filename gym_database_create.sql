create table address (id bigint not null auto_increment, building_identifier varchar(255), county varchar(255), post_code varchar(255), street varchar(255), primary key (id)) engine=InnoDB;
create table gym (id bigint not null auto_increment, about varchar(255), gym_name varchar(255), address_id bigint, primary key (id)) engine=InnoDB;
create table gym_trainers (gym_id bigint not null, trainers_id bigint not null, primary key (gym_id, trainers_id)) engine=InnoDB;
create table member (id bigint not null auto_increment, image longblob, name varchar(255), tel_no varchar(255), date_of_birth date, memberid varchar(255), training_goals varchar(255), primary key (id)) engine=InnoDB;
create table member_membership (member_id bigint not null, membership_id bigint not null, primary key (member_id, membership_id)) engine=InnoDB;
create table member_visit (member_id bigint not null, visit_id bigint not null, primary key (member_id, visit_id)) engine=InnoDB;
create table membership (id bigint not null auto_increment, active bit, end date, start date, membership_type_id bigint, primary key (id)) engine=InnoDB;
create table membershiptype (id bigint not null auto_increment, description varchar(255), type varchar(255), primary key (id)) engine=InnoDB;
create table trainer (id bigint not null auto_increment, image longblob, name varchar(255), tel_no varchar(255), biography varchar(255), employee_number varchar(255), primary key (id)) engine=InnoDB;
create table trainer_speciality (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB;
create table trainer_trainer_speciality (trainer_id bigint not null, speciality_id bigint not null, primary key (trainer_id, speciality_id)) engine=InnoDB;
create table visit (id bigint not null auto_increment, purpose varchar(255), visit_dt datetime, primary key (id)) engine=InnoDB;
alter table gym_trainers add constraint UK_q8tdkfvpfr2brj77txy6epe6v unique (trainers_id);
alter table member_membership add constraint UK_6vv929e1sokx56fhdmfbbou4p unique (membership_id);
alter table member_visit add constraint UK_h15o64eemimxe89ta3kraixvp unique (visit_id);
alter table trainer add constraint UK_7jn7t48c9a5k4pmj88y5xdi7w unique (employee_number);
alter table gym add constraint FKqx0mcgm16201m4wbyuemi2qce foreign key (address_id) references address (id);
alter table gym_trainers add constraint FKf3ttspdrqclmxrnja8ybe6l12 foreign key (trainers_id) references trainer (id);
alter table gym_trainers add constraint FKta1313wv2gah2tpgj84slvpij foreign key (gym_id) references gym (id);
alter table member_membership add constraint FK94sdil1wge2u9iv9o7xsobfxq foreign key (membership_id) references membership (id);
alter table member_membership add constraint FKnxgnytw1m61mnjycs2u70kjjq foreign key (member_id) references member (id);
alter table member_visit add constraint FK5i2kr5cry9tp1bbs57atbb397 foreign key (visit_id) references visit (id);
alter table member_visit add constraint FK1w4gmq408ejux767skw0x7gh5 foreign key (member_id) references member (id);
alter table membership add constraint FKarljsttpye9k7dlb4nq9m9l9m foreign key (membership_type_id) references membershiptype (id);
alter table trainer_trainer_speciality add constraint FKlk3q6o2a4kffxcy1ojtoaygge foreign key (speciality_id) references trainer_speciality (id);
alter table trainer_trainer_speciality add constraint FK14e66ti5wtmp0v3pauahtifc3 foreign key (trainer_id) references trainer (id);
create table address (id bigint not null auto_increment, building_identifier varchar(255), county varchar(255), post_code varchar(255), street varchar(255), primary key (id)) engine=InnoDB
create table gym (id bigint not null auto_increment, about varchar(255), gym_name varchar(255), address_id bigint, primary key (id)) engine=InnoDB
create table gym_trainers (gym_id bigint not null, trainers_id bigint not null, primary key (gym_id, trainers_id)) engine=InnoDB
create table member (id bigint not null auto_increment, image longblob, name varchar(255), tel_no varchar(255), date_of_birth date, memberid varchar(255), training_goals varchar(255), primary key (id)) engine=InnoDB
create table member_membership (member_id bigint not null, membership_id bigint not null, primary key (member_id, membership_id)) engine=InnoDB
create table member_visit (member_id bigint not null, visit_id bigint not null, primary key (member_id, visit_id)) engine=InnoDB
create table membership (id bigint not null auto_increment, active bit, end date, start date, membership_type_id bigint, primary key (id)) engine=InnoDB
create table membershiptype (id bigint not null auto_increment, description varchar(255), type varchar(255), primary key (id)) engine=InnoDB
create table trainer (id bigint not null auto_increment, image longblob, name varchar(255), tel_no varchar(255), biography varchar(255), employee_number varchar(255), primary key (id)) engine=InnoDB
create table trainer_speciality (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table trainer_trainer_speciality (trainer_id bigint not null, speciality_id bigint not null, primary key (trainer_id, speciality_id)) engine=InnoDB
create table visit (id bigint not null auto_increment, purpose varchar(255), visit_dt datetime, primary key (id)) engine=InnoDB
alter table gym_trainers add constraint UK_q8tdkfvpfr2brj77txy6epe6v unique (trainers_id)
alter table member_membership add constraint UK_6vv929e1sokx56fhdmfbbou4p unique (membership_id)
alter table member_visit add constraint UK_h15o64eemimxe89ta3kraixvp unique (visit_id)
alter table trainer add constraint UK_7jn7t48c9a5k4pmj88y5xdi7w unique (employee_number)
alter table gym add constraint FKqx0mcgm16201m4wbyuemi2qce foreign key (address_id) references address (id)
alter table gym_trainers add constraint FKf3ttspdrqclmxrnja8ybe6l12 foreign key (trainers_id) references trainer (id)
alter table gym_trainers add constraint FKta1313wv2gah2tpgj84slvpij foreign key (gym_id) references gym (id)
alter table member_membership add constraint FK94sdil1wge2u9iv9o7xsobfxq foreign key (membership_id) references membership (id)
alter table member_membership add constraint FKnxgnytw1m61mnjycs2u70kjjq foreign key (member_id) references member (id)
alter table member_visit add constraint FK5i2kr5cry9tp1bbs57atbb397 foreign key (visit_id) references visit (id)
alter table member_visit add constraint FK1w4gmq408ejux767skw0x7gh5 foreign key (member_id) references member (id)
alter table membership add constraint FKarljsttpye9k7dlb4nq9m9l9m foreign key (membership_type_id) references membershiptype (id)
alter table trainer_trainer_speciality add constraint FKlk3q6o2a4kffxcy1ojtoaygge foreign key (speciality_id) references trainer_speciality (id)
alter table trainer_trainer_speciality add constraint FK14e66ti5wtmp0v3pauahtifc3 foreign key (trainer_id) references trainer (id)
create table address (id bigint not null auto_increment, building_identifier varchar(255), county varchar(255), post_code varchar(255), street varchar(255), primary key (id)) engine=InnoDB
create table gym (id bigint not null auto_increment, about varchar(255), gym_name varchar(255), address_id bigint, primary key (id)) engine=InnoDB
create table gym_trainers (gym_id bigint not null, trainers_id bigint not null, primary key (gym_id, trainers_id)) engine=InnoDB
create table member (id bigint not null auto_increment, image longblob, name varchar(255), tel_no varchar(255), date_of_birth date, memberid varchar(255), training_goals varchar(255), primary key (id)) engine=InnoDB
create table member_membership (member_id bigint not null, membership_id bigint not null, primary key (member_id, membership_id)) engine=InnoDB
create table member_visit (member_id bigint not null, visit_id bigint not null, primary key (member_id, visit_id)) engine=InnoDB
create table membership (id bigint not null auto_increment, active bit, end date, start date, membership_type_id bigint, primary key (id)) engine=InnoDB
create table membershiptype (id bigint not null auto_increment, description varchar(255), type varchar(255), primary key (id)) engine=InnoDB
create table trainer (id bigint not null auto_increment, image longblob, name varchar(255), tel_no varchar(255), biography varchar(255), employee_number varchar(255), primary key (id)) engine=InnoDB
create table trainer_speciality (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table trainer_trainer_speciality (trainer_id bigint not null, speciality_id bigint not null, primary key (trainer_id, speciality_id)) engine=InnoDB
create table visit (id bigint not null auto_increment, purpose varchar(255), visit_dt datetime, primary key (id)) engine=InnoDB
alter table gym_trainers add constraint UK_q8tdkfvpfr2brj77txy6epe6v unique (trainers_id)
alter table member_membership add constraint UK_6vv929e1sokx56fhdmfbbou4p unique (membership_id)
alter table member_visit add constraint UK_h15o64eemimxe89ta3kraixvp unique (visit_id)
alter table trainer add constraint UK_7jn7t48c9a5k4pmj88y5xdi7w unique (employee_number)
alter table gym add constraint FKqx0mcgm16201m4wbyuemi2qce foreign key (address_id) references address (id)
alter table gym_trainers add constraint FKf3ttspdrqclmxrnja8ybe6l12 foreign key (trainers_id) references trainer (id)
alter table gym_trainers add constraint FKta1313wv2gah2tpgj84slvpij foreign key (gym_id) references gym (id)
alter table member_membership add constraint FK94sdil1wge2u9iv9o7xsobfxq foreign key (membership_id) references membership (id)
alter table member_membership add constraint FKnxgnytw1m61mnjycs2u70kjjq foreign key (member_id) references member (id)
alter table member_visit add constraint FK5i2kr5cry9tp1bbs57atbb397 foreign key (visit_id) references visit (id)
alter table member_visit add constraint FK1w4gmq408ejux767skw0x7gh5 foreign key (member_id) references member (id)
alter table membership add constraint FKarljsttpye9k7dlb4nq9m9l9m foreign key (membership_type_id) references membershiptype (id)
alter table trainer_trainer_speciality add constraint FKlk3q6o2a4kffxcy1ojtoaygge foreign key (speciality_id) references trainer_speciality (id)
alter table trainer_trainer_speciality add constraint FK14e66ti5wtmp0v3pauahtifc3 foreign key (trainer_id) references trainer (id)
