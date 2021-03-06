# π€Ί chat-java
###μ±ν Java Spring boot λ‘ κ΅¬ν
ν΄λΉ νλ‘μ νΈλ Restful APIλ₯Ό μ€μνλ©°, TDD λ°©μμΌλ‘ κ°λ°μ ν  μμ μλλ€.

ν΄λΉ νλ©΄κ³Ό μ΄μ΄μ§λ Front νλ©΄

Git : https://github.com/GoDeokHwan/chat-front

***
## πμ€ν©

- java11
- Spring boot 2.5.6
- Spring Security
- JPA
- Mysql
- H2
- Junit5

***
## π ν΄λκ΅¬μ‘°

```
chat-java
   γ΄ api : API ν¨ν€μ§ ν΄λ
       γ΄ src
          γ΄ java 
           γ΄ config   : Spring μ€μ 
           γ΄ domain   : λ³΄μ μ μ±μ λ°μΌλ©΄μ μ¬μ© λ  API ν¨ν€μ§
           γ΄ entity   : DB Entity ν¨ν€μ§ 
           γ΄ external : λ³΄μ μ μ± μμ΄ μΈλΆμμ λ€μ΄ μ¬ μ μλ ν¨ν€μ§
           γ΄ supoort  : μΈλΆ λΌμ΄λΈλ¬λ¦¬λ₯Ό μ΄μ©ν΄μ λμλλ ν¨ν€μ§ 
           γ΄ util     : μ¬μ©μ€μ κ³΅ν΅μΌλ‘ λμμ΄ λλ ν¨ν€μ§
         γ΄ resources
            γ΄ logging : λ‘κ·Έ μ€μ  
            application.yml : μ μ²΄μ μΈ Spring μ€μ  yml            
```
***
## π DB ERD
![img.png](img.png)

1. νμ΄λΈ μ€λͺ
- user : μ¬μ©μ μ λ³΄
- chat_room : μ±ν
- chat_message : μ±ν λ©μμ§
- chat_room_user_mapping : μ±νμ λ€μ΄μ€λ μ¬μ©μ

2. νμ΄λΈ κ΄λ¦¬ ν΄λΉ λ¬Έμλ‘ κ΄λ¦¬ ν©λλ€.
```
resource
  γ΄ chat_erd.mwb
  γ΄ schema-create.sql
```


***
## π λ³κ²½ μ¬ν­
- 21.10.31
1. μ±νλ°© μ€ν API

- 21.10.30
1. Cors μλ¬ ν΄κ²° μμΈ ( Security μͺ½μμλ νμ΄μΌλλ€. )

- 21.10.28
1. ChatRoom μμ±
2. μ¬μ©μμ ChatRoom μ‘°ν 

β» μ΄μ μ¬ν­

- Junitμμ Mapping Tableμ λͺ» μ½μ νμ ν΄κ²°
- @OneToManyμ Casecade.ALL μΌ κ²½μ° save μ€λ₯ λ°μ ν΄κ²° 



- 21.10.26
1. Spring boot Test H2 λ©λͺ¨λ¦¬ DBλ‘ μ°κ²° 
2. ChatRoom μμ± Service μΆκ° 

- 21.10.25
1. GlobalException μΆκ°
2. Rest API Connection Config μ€μ  

- 21.10.24
1. μ¬μ©μ μΆκ° μΌμ΄μ€ μΆκ° (νμκ°μμ λ°λ‘ κ΅¬νμ νμ§ μμ μμ μλλ€.)
2. Security μ μ© 
3. λ°ν ApiResult ν΅μΌ 

- 21.10.23
1. νλ‘μΈμ€ νμΈ Rest API μΆκ°
2. Controller Test Case μΆκ° νμ€νΈ 
3. Mysql DB μ°κ²°
4. Mysql ERD μ€κ³ μλ£ 
5. Jpa μ μ λμ νμΈ