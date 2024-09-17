alter table article
add column is_deleted TINYINT(1) not null default 0;