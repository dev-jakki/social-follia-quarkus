CREATE TABLE POSTS (
   id bigserial not null primary key,
   post_text varchar(250) not null,
   created_at timestamp not null,
   user_id bigint not null references USERS(id)
);