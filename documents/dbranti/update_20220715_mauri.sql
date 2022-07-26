-- user: mauricio
-- created_at: 20220630 20:48

ALTER TABLE `ranti`.`user` ADD COLUMN `refresh_token` VARCHAR(300) AFTER `provider_user_id`; 
