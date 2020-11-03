# twitter-app

SpringBoot application to get portfolio users and retrieve twitter timeline

```
Steps to get application up:

./gradlew bootRun

NOTE: You should have created a JAVA_HOME variable for Java 11.

Steps to clean build app:
./gradlew clean build
``` 

Technology used to build this application:
 - Java 11.
 - VAVR v0.9. (FP Framework)
 - Spring Boot.
 - Spring Data JPA.
 - Spring Boot Test.
 - Java Bean Validation.
 - Spring Social Twitter.
 - Lombok.
 - JQuery.
 - Gradle.
 
```
Web pages to retrieve data:
http://localhost:8080/index.html (Get All Users's Portfolio)
http://localhost:8080/user.html?id=1 (Get User's Portfolio Detail)

Services:

Get All Users:
GET: localhost:8080/api/user

Response:
{
    "users": [
        {
            "profileInfo": {
                "idPortfolio": int,
                "description": "String",
                "imageUrl": "String",
                "twitterUserName": "String",
                "title": "String"
            },
            "tweetList": null
        }
    ]
}

Get User Portfolio:
GET: localhost:8080/api/user/{idPortfolio}

Response:
{
    "profileInfo": {
        "idPortfolio": int,
        "description": "String",
        "imageUrl": "String",
        "twitterUserName": "String",
        "title": "String",
        "tittle": "String"
    },
    "tweetList": [
        {
            "id": int,
            "idStr": "String",
            "text": "String",
            "createdAt": "DateTime",
            "fromUser": "String",
            "profileImageUrl": "String",
            "toUserId": int,
            "inReplyToScreenName": "String",
            "fromUserId": int,
            "languageCode": "String",
            "source": "String",
            "retweetCount": int,
            "retweeted": false
        }
    ]
}

Update User Portfolio:
PUT: localhost:8080/api/user/{idPortfolio}

{
    "description": "String",
    "imageUrl": "String",
    "twitterUserName": "String",
    "title": "String",
    "tittle": "String"
}

Response:
{
    "success": true
}
```