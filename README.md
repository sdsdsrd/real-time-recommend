# real-time-recommend

실시간 추천 서비스



- project flow

  

![flow](flow.png)





## Table of Contents

- Examples
- Prerequisites
- Install
  - flask-server
  - spring-boot-server
  - database
- Rest API



## Examples

http://54.180.30.116:8080/genre/2/20200825090000.020

```json
[
    {
        "genre": "키즈",
        "runningTime": 3772
    },
    {
        "genre": "동요/율동",
        "runningTime": 300
    }
]
```



http://54.180.30.116:8080/epsd/1/20200815093000.237

```json
[
    "CE1000363308",
    "CE1000348834",
    "CE1000361657",
    "CE1000348490",
    "CE1000348720",
    "CE1000348716",
    "CE0001438483",
    "CE0000013770"
]
```





## Prerequisites

- Python > 3.6
- Java 11



## Install

### flask-server 

#### Base URL : http://54.180.30.116/5000

```python
pip install flask
pip install flask_restful
pip install pandas
pip install sklearn
```



### spring-boot-server

#### Base URL : http://54.180.30.116/8080

The main project is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/).

```java
mvn compile
```

Plugin for [IntelliJ IDEA](http://plugins.jetbrains.com/plugin/6317-lombok-plugin) to support [Lombok](https://projectlombok.org/) annotations.



### Database

Install MySQL

```yml
## application.yml
url: jdbc:mysql://db-1.cx9greozla0d.ap-northeast-2.rds.amazonaws.com:3306
```

```mysql
create database btvdb;
```





## Rest API

| METHOD | PATH                   | DESCRIPTION                                                 |
| ------ | ---------------------- | ----------------------------------------------------------- |
| GET    | /log                   | Get all log data from database                              |
| GET    | /genre/{stbId}/{now}   | Get top 2 most watched genres at the same time for 2 weeks  |
| GET    | /content/{stbId}/{now} | Get the most watched contentId at the same time for 2 weeks |
| GET    | /epsdids/{contentId}   | Find epsdId list corresponding to contentId                 |
| GET    | /epsd/{stbId}/{now}    | Get epsdId list of VOD recommendation                       |