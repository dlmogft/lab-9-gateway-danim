# lab-9-gateway-danim

This is a Spring Cloud GAteway client project that is used to centralize the call to other services, applying filters and predicates to call them<br>
This project uses the config server **lab-3-server-danim**, the Eureka server **lab-4-eureka-server-danim** to register the services, the **lab-5-word-server-danim** to start the different executions of the word services,
and the **lab-6-sentence-server-danim** to use the word services to mount a sentence
This project acts as a **gateway** that allowa to manipulate and filter the service executions started in  **lab-5-word-server-danim** and the one started in **lab-6-sentence-server-danim**

# Starting this client repository

The steps to used it are:
- Start the **lab-3-server-danim** repo, which loads the configuration files from the **spring-cloud-server-config-danim** Git repository
- Start the **lab-4-eureka-server-danim** repo and check it's started correctly in http://localhost:8010
- Start the different executions of **lab-5-word-server-danim** ([See README from lab-5-word-server-danim](https://github.com/dlmogft/lab-5-word-server-danim/blob/main/README.md))
- Start the **lab-6-sentence-server-danim** repo and check it's started correctly in http://localhost:8020/sentence
- Start the class annotated with @SpringBootApplication
- Check that the word services work in http://localhost:8080/services/subject, http://localhost:8080/services/verb, http://localhost:8080/services/article, http://localhost:8080/services/adjective, and http://localhost:8080/services/noun
- and that the sentence is shown in the URL http://localhost:8080

# Dependencies

Spring Boot Starter Webflux, Config Client, Spring Boot Starter Gateway, Cloud LoadBalancer, Eureka Client

# Tips

- The **application.yml** config file specifies the URI of the Config Server repo **lab-3-server-danim** that loads the configuration files from the **spring-cloud-server-config-danim** Git repository<br>
- The **application.yml** specifies the different gateway routes, every one with the corresponding id, uri, predicates ald filters, to manipulate the URLs
