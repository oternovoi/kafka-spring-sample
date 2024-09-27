## Пример модульного (интеграционного?) тестирования kafka с использованием embedded kafka
Из особенностей:
- не используем SpringBootTest, судя по логам тащит за собой весь спринговый контекст. Вместо него SpringExtension
с явным указанием загружаемых бинов.
- спринговые Producer и Consumer проверяем независимо в паре Consumer и Producer соответсвенно из на pure java сlient    
- у EmbeddedKafka не указываем порты чтобы запустилось на случайном
- вместо определения бина JsonMessageConverter явно пропишем trusted packages
### Поднимаем kafka в контейнере
```shell
docker run -p 9092:9092 -itd --name kafka apache/kafka:3.8.0
```
нужно исключительно для POST запроса. Тесты без него работают. 
### Запрос
```shell
curl --location --request POST 'localhost:8080/send/111/aaa' --data ''
```
### Доки
<https://github.com/spring-projects/spring-kafka/tree/main/samples/sample-01>  
<https://www.geeksforgeeks.org/sending-message-to-kafka-using-java>  
<https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-kafka-extra-props>  
