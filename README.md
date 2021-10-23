# 🤺 chat-java
###채팅 Java Spring boot 로 구현
해당 프로젝트는 Restful API를 준수하며, TDD 방식으로 개발을 할 예정입니다.

해당 화면과 이어지는 Front 화면

Git : https://github.com/GoDeokHwan/chat-front

***
## 📜스팩

- java11
- Spring boot 2.5.6
- Spring Security
- JPA
- Mysql
- H2

***
## 📜 폴더구조

```
chat-java
   ㄴ api : API 패키지 폴더
       ㄴ src
          ㄴ java 
           ㄴ config   : Spring 설정
           ㄴ domain   : 보안 정책을 받으면서 사용 될 API 패키지
           ㄴ external : 보안 정책 없이 외부에서 들어 올 수 있는 패키지
           ㄴ supoort  : 외부 라이브러리를 이용해서 도움되는 패키지 
           ㄴ util     : 사용중에 공통으로 도움이 되는 패키지
         ㄴ resources
            ㄴ logging : 로그 설정 
            application.yml : 전체적인 Spring 설정 yml            
```

***
## 📜 변경 사항
