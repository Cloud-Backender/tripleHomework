CREATE DATABASE `data`;
USE data;

CREATE TABLE `POINT_LOG` (
                             `SEQ` bigint NOT NULL AUTO_INCREMENT,
                             `CREATE_DT` datetime NOT NULL,
                             `REASON` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `REVIEW_ID` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `TOTAL_POINT` bigint DEFAULT NULL,
                             `USER_ID` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             PRIMARY KEY (`SEQ`),
                             KEY `USER_ID_IDX` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `REVIEW` (
                          `REVIEW_ID` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
                          `CREATE_DT` datetime NOT NULL,
                          `UPDATE_DT` datetime NOT NULL,
                          `ATTACHED_PHOTO_IDS` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `CONTENT` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `PLACE_ID` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `USER_ID` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          PRIMARY KEY (`REVIEW_ID`),
                          KEY `USER_ID_PLACE_ID_IDX` (`PLACE_ID`,`USER_ID`) USING BTREE,
                          KEY `PLACE_ID_IDX` (`PLACE_ID`) USING BTREE,
                          KEY `USER_ID_IDX` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
