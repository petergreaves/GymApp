create table address (id bigint generated by default as identity, building_identifier varchar(255), county varchar(255), post_code varchar(255), street varchar(255), primary key (id))
create table gym (id bigint generated by default as identity, about varchar(255), gym_name varchar(255), address_id bigint, primary key (id))
create table gym_trainers (gym_id bigint not null, trainers_id bigint not null, primary key (gym_id, trainers_id))
create table member (id bigint generated by default as identity, name varchar(255), tel_no varchar(255), memberid varchar(255), primary key (id))
create table member_membership (member_id bigint not null, membership_id bigint not null, primary key (member_id, membership_id))
create table membership (id bigint generated by default as identity, active boolean, end date, start date, membership_type_id bigint, primary key (id))
create table membershiptype (id bigint generated by default as identity, description varchar(255), primary key (id))
create table trainer (id bigint generated by default as identity, name varchar(255), tel_no varchar(255), employee_number varchar(255), primary key (id))
create table trainer_speciality (id bigint generated by default as identity, description varchar(255), primary key (id))
create table trainer_trainer_speciality (trainer_id bigint not null, speciality_id bigint not null, primary key (trainer_id, speciality_id))
alter table gym_trainers add constraint UK_q8tdkfvpfr2brj77txy6epe6v unique (trainers_id)
alter table member_membership add constraint UK_6vv929e1sokx56fhdmfbbou4p unique (membership_id)
alter table gym add constraint FKqx0mcgm16201m4wbyuemi2qce foreign key (address_id) references address
alter table gym_trainers add constraint FKf3ttspdrqclmxrnja8ybe6l12 foreign key (trainers_id) references trainer
alter table gym_trainers add constraint FKta1313wv2gah2tpgj84slvpij foreign key (gym_id) references gym
alter table member_membership add constraint FK94sdil1wge2u9iv9o7xsobfxq foreign key (membership_id) references membership
alter table member_membership add constraint FKnxgnytw1m61mnjycs2u70kjjq foreign key (member_id) references member
alter table membership add constraint FKarljsttpye9k7dlb4nq9m9l9m foreign key (membership_type_id) references membershiptype
alter table trainer_trainer_speciality add constraint FKlk3q6o2a4kffxcy1ojtoaygge foreign key (speciality_id) references trainer_speciality
alter table trainer_trainer_speciality add constraint FK14e66ti5wtmp0v3pauahtifc3 foreign key (trainer_id) references trainer