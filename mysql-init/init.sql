CREATE DATABASE `data`;
USE data;
CREATE TABLE `POINT_LOG` (
                             `SEQ` bigint NOT NULL AUTO_INCREMENT,
                             `CREATE_DT` datetime NOT NULL,
                             `REASON` varchar(255)    DEFAULT NULL,
                             `REVIEW_ID` varchar(36)   DEFAULT NULL,
                             `TOTAL_POINT` bigint DEFAULT NULL,
                             `USER_ID` varchar(36)  DEFAULT NULL,
                             PRIMARY KEY (`SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



CREATE TABLE `REVIEW` (
                          `REVIEW_ID` varchar(36)  NOT NULL,
                          `CREATE_DT` datetime NOT NULL,
                          `UPDATE_DT` datetime NOT NULL,
                          `ATTACHED_PHOTO_IDS` varchar(1000)  DEFAULT NULL,
                          `CONTENT` varchar(1000)  DEFAULT NULL,
                          `PLACE_ID` varchar(36)  DEFAULT NULL,
                          `USER_ID` varchar(36)  DEFAULT NULL,
                          PRIMARY KEY (`REVIEW_ID`),
                          KEY `USER_ID_PLACE_ID_IDX` (`PLACE_ID`,`USER_ID`) USING BTREE,
                          KEY `PLACE_ID_IDX` (`PLACE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
