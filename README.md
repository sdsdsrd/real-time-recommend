## real-time-recommend

실시간 추천 서비스



- project flow

  

![flow](C:\Users\SoYeon\Documents\SK브로드밴드\real-time-recommend\flow.png)





### Table of Contents

---

- Examples
- Install
- Database
- Usage



### Examples

---





### Install

---

##### flask-server : http://54.180.30.116/5000

```python
pip install flask
pip install flask_restful
pip install pandas
pip install sklearn
```

##### spring-boot-server : http://54.180.30.116/8080

The main project is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/).

```java
mvn compile
```

##### 

### Database

---

Install MySQL

```yml
url: jdbc:mysql://db-1.cx9greozla0d.ap-northeast-2.rds.amazonaws.com:3306
```

```mysql
create database btvdb;
```

