CREATE TABLE `users` (
  `id` smallint  NOT NULL AUTO_INCREMENT,
  `first_name` text  DEFAULT NULL,
  `last_name` text DEFAULT NULL,
  `username` varchar(255) unique DEFAULT NULL,
  `password` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) 

CREATE TABLE `todos` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `description` text DEFAULT NULL,
  `is_done` bit(1) NOT NULL,
  `target_date` datetime(6) DEFAULT NULL,
  `username` varchar(255) unique DEFAULT NULL,
  `title` text DEFAULT NULL,
  PRIMARY KEY (`id`)
)
