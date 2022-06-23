# Triple Backend HomeWork

## Compatibility

| -           | -                     |
|-------------|-----------------------|
| Spring boot | 2.6.7                 |
| Java        | 11                    |
| Build tool  | maven                 |
| Database    | mysql:8.0.29 (latest) |
| Docker      | 20.10.10              |

## Installation

- Docker 최신 버전이 설치된 가정하에 실행합니다.
- Mysql 포트는 3306을 사용합니다. (쓰고 있다면 잠시 중지)
1. tripleHomework 디렉토리로 이동합니다.
2. start.sh 을 실행합니다. (Mysql 이미지를 생성하고 table을 넣어주는 작업입니다.)
```
$sh start.sh
```
3. maven 빌드를 합니다.
   1. ```
      mvn clean install -f pom.xml
      ```
      Error 발생 시 2-2 명령어 실행
   2. ```
      mvn clean install -DskipTests=true -f pom.xml
      ```
3. tripleHomework 스프링 프로젝트의 ClubApplication App을 실행합니다. (Default Profile)
4. [http://localhost:1111/](http://localhost:1111/) 로 api 통신을 합니다.

## API List
```json
1. 리뷰 관련 이벤트 API
POST/ {{API서버}}/events
{
    "type": "REVIEW",   //event의 타입입니다.
    "action": "ADD",     //리뷰의 action 타입입니다. ADD-생성, MOD-수정, DELETE-삭제
    "reviewId": "45fef7f2-f8e7-4798-8352-4c341d1af8c5",      //리뷰의 고유 ID 입니다 (UUID)
    "content": "this is content",       //리뷰 내용입니다.
    "attachedPhotoIds": ["344cadf8-da47-4f65-957b-109755ddc310","344cadf8-da47-4f65-957b-109755ddc311"],    //리뷰에 들어가는 사진 리스트입니다. (UUID)
    "userId": "f39c8237-a550-433c-94df-80c952529788",                  //유저 ID입니다. (UUID)
    "placeId": "521cbaf7-aa70-4c6c-a62e-23c1b4579ff1"     //장소 ID입니다. (UUID)
}

{
    "type": "REVIEW",   //event의 타입입니다.
    "action": "MOD",     //리뷰의 action 타입입니다. ADD-생성, MOD-수정, DELETE-삭제
    "reviewId": "45fef7f2-f8e7-4798-8352-4c341d1af8c5",      //리뷰의 고유 ID 입니다 (UUID)
    "content": "this is modified content"       //리뷰 내용입니다.
    "attachedPhotoIds": ["df1d6w6q-f8e7-4995-1353-a1f6d8c9z4x5", "21a6s5d6-zv5d-4358-9795-y5y6n8i9i4y5"]        //리뷰 이미지 ID 입니다. (UUID)
}

{
    "type": "REVIEW",   //event의 타입입니다.
    "action": "DELETE",     //리뷰의 action 타입입니다. ADD-생성, MOD-수정, DELETE-삭제
    "reviewId": "45fef7f2-f8e7-4798-8352-4c341d1af8c5",      //리뷰의 고유 ID 입니다 (UUID)
}

2. 유저 포인트 조회 API
GET/ {{API서버}}/total-point/{userId}
```
##Exception List
| Exception API Name | Exception Code | Exception Content  | Remark |
| --- | --- | --- | --- |
| ALREADY_EXIST_REVIEW | 100 | 해당 장소에 이미 리뷰가 존재합니다. | 해당 place에 이미 review를 작성된 경우 |
| NOT_EXIST_REVIEW | 101 | 리뷰가 존재하지 않습니다. | MOD/DELETE 요청에 해당하는 ReviewId가 없을 경우 |
| NOT_EXIST_USER | 102 | 유저가 존재하지 않습니다. | 총점(/getTotalPoint)조회 할 때 UserId가 없는 경우 |
| NOT_EXIST_EVENT_TYPE | 600 | 존재하지 않는 type 입니다. | /events 의 type이 Review가 아닐경우 |
| NOT_EXIST_EVENT_ACTION_TYPE | 601 | 존재하지 않는 action 입니다. | /events 의 action이 ADD/MOD/DELETE가 아닌경우 |
| SYSTEM_ERROR | 599 | 시스템 에러 | 전체적인 시스템 에러 |

## SQL QUERY

```
-- `data`.POINT_LOG definition

CREATE TABLE `POINT_LOG` (
  `SEQ` bigint NOT NULL AUTO_INCREMENT,
  `CREATE_DT` datetime NOT NULL,
  `REASON` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `REVIEW_ID` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `TOTAL_POINT` bigint DEFAULT NULL,
  `USER_ID` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`SEQ`),
  KEY `USER_ID_IDX` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- `data`.REVIEW definition

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;```
