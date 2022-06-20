# Triple Backend HomeWork

트리플 백엔드 과제입니다.

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
2. start.sh 을 실행합니다.
```
$sh start.sh
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
    "content": "this is modified content",       //리뷰 내용입니다.
}

{
    "type": "REVIEW",   //event의 타입입니다.
    "action": "DELETE",     //리뷰의 action 타입입니다. ADD-생성, MOD-수정, DELETE-삭제
    "reviewId": "45fef7f2-f8e7-4798-8352-4c341d1af8c5",      //리뷰의 고유 ID 입니다 (UUID)
}

2. 유저 포인트 조회 API
GET/ {{API서버}}/total-point/{userId}
```

## SQL QUERY

```
CREATE TABLE `POINT_LOG` (
                             `SEQ` bigint NOT NULL AUTO_INCREMENT,
                             `CREATE_DT` datetime NOT NULL,
                             `REASON` varchar(255) DEFAULT NULL,
                             `REVIEW_ID` varchar(36)  DEFAULT NULL,
                             `TOTAL_POINT` bigint DEFAULT NULL,
                             `USER_ID` varchar(36)  DEFAULT NULL,
                             PRIMARY KEY (`SEQ`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `REVIEW` (
                          `REVIEW_ID` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `CREATE_DT` datetime NOT NULL,
                          `UPDATE_DT` datetime NOT NULL,
                          `ATTACHED_PHOTO_IDS` varchar(1000)  DEFAULT NULL,
                          `CONTENT` varchar(1000)  DEFAULT NULL,
                          `PLACE_ID` varchar(36)  DEFAULT NULL,
                          `USER_ID` varchar(36)  DEFAULT NULL,
                          PRIMARY KEY (`REVIEW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
