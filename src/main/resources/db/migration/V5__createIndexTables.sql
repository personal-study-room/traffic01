create index idx_user_id on article (user_id);
create index idx_board_id on article (board_id);
create index idx_token on jwt_blacklist (token);
create index idx_email on user (email);