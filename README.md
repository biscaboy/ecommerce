# eCommerce Application
### eCommerce Server - Udacity Java Developer Nano Degree - Project 4

## Introduction
This simple eCommerce project requires the student to take an existing Spring REST API application and add the following:
 * Security using Java Web Tokens (JWT)
 * Testing with JUnit at 80% code coverage
 * Continuous Integreation/Continuous Deployment (CI/CD) pipline using GitHub, a cloud server (AWS) and Jenkins
 * Reporting and Monitoring of the system using Splunk
    
## Required Screenshots

Screenshots of the Jenkins Server running on AWS can be found under ```/Jenkins-Screenshots```. 

Screenshots of Splunk searches, dashboards, alerts and triggered alerts can be found under ```/Splunk-Screenshots```.  

## Building and Running this Project in on the command line

#### To **Build** 
```
    ./mvnw clean package
```    
#### To **Run**
```
    java -jar ./target/eCommerceAuthProject-0.0.1-SNAPSHOT-spring-boot.jar
```
### Accessing the API

The api runs on `port 8081` by default and can be access on your local device at: 
```
    http://localhost:8081/login
```
#### Submitting requests to the REST API

To submit requests via POSTMAN, please import [this collection of commands](https://github.com/biscaboy/ecommerce/blob/main/src/test/resources/Ecommerce.postman_collection.json).

You may also download, build and execute the [eCommerce API Client](https://github.com/biscaboy/ecommerce-api-client-runner).  This client runs against the server to create log files in real time.  Please see the [readme](https://github.com/biscaboy/ecommerce-api-client-runner/blob/main/README.md) for directions.

#### API Documentation 

The [Swagger UI](http://localhost:8081/swagger-ui.html) has been installed for visualization of the eCommerceAPI.  Also see the [API-Docs](http://localhost:8081/v2/api-docs).

#### API Requsts

##### Create a new user: 
*Passwords require one uppercase letter, one lowercase letter, one number, one special character and a length of 10 characters to be valid.*
```
POST /api/user/create
{
    "username": "newUserName",
    "password": "P@ssw0rd123",
    "confirmPassword": "P@ssw0rd123"
}
```

##### Login - Token Creation
Upon successful login an Authorization header will be returned with the Java Web Token to be used for the rest of the session.  Insert this token into the Authorizaiton header of each subsequent request.
```
POST /login
{
    "username": "newUserName",
    "password": "P@ssw0rd123"
}
```

##### Find User by Name
```
GET /api/user/<username>
```

##### Show All Users
```
GET /api/user/list
```

##### Add an Item to the Cart
Items with ids of 0 and 1 are prepopulated in the database at startup.
```
POST /api/cart/addToCart
{
    "username" : "<username>",
    "itemId": 1,
    "quantity": 1
}
```

##### Create an Order from a User's Cart
```
POST /api/order/submit/<username>
```

##### Show Order History
```
GET /api/order/history/<username>
```
   
##### List Available Items
```
GET /api/item
```

##### Find Item By Name
```
GET /api/item/name?name=<item name>
```
## Resources
I'm grateful to these websites and authors who helped me complete this project and learn this subject matter.  I also want to give a shout out to those who curate the [Splunk Enterprise](https://docs.splunk.com/Documentation/Splunk) and [Jenkins Documentation](https://www.jenkins.io/doc/) sites as I found them both very easy to navigate and full of useful examples and tutorials.
 * https://www.javaprogramto.com/2020/08/how-to-make-delay-in-java-thread-sleep.html
 * https://www.baeldung.com/java-generating-random-numbers-in-range
 * https://www.baeldung.com/spring-request-param
 * https://mkyong.com/webservices/jax-rs/restful-java-client-with-apache-httpclient/
 * https://alvinalexander.com/java/java-apache-httpclient-restful-client-examples/
 * https://stackoverflow.com/questions/18186722/how-to-setup-spring-logs-for-tomcat
 * https://www.baeldung.com/spring-boot-configure-tomcat
 * https://tomcat.apache.org/tomcat-9.0-doc/config/valve.html
 * https://blog.behrang.org/2019/10/26/spring-boot-tomcat-log4j2.html
 * https://www.tutorialspoint.com/log4j/log4j_patternlayout.htm
 * https://howtodoinjava.com/log4j/log4j-rolling-file-appender/
 * https://howtodoinjava.com/spring-boot2/spring-rest-request-validation
 * https://stackoverflow.com/questions/23869207/what-is-the-significance-of-log4j-rootlogger-property-in-log4j-properties-file
 * https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference
 * https://www.baeldung.com/maven-goals-phases
 * https://www.baeldung.com/slf4j-classpath-multiple-bindings
 * https://logging.apache.org/log4j/2.x/manual/configuration.html#ConfigurationSyntax
 * https://logging.apache.org/log4j/2.x/manual/configuration.html#ConfigurationSyntax
 * https://mkyong.com/logging/log4j-log4j-properties-examples/
 * https://docs.spring.io/spring-boot/docs/1.2.3.RELEASE/reference/htmlsingle/#boot-features-custom-log-levels
 * https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
 * https://dzone.com/articles/spring-boot-rest-api-unit-testing-with-junit
 * https://www.baeldung.com/parameterized-tests-junit-5
 * https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2Client-auth-grant-support
 * https://bezkoder.com/jwt-json-web-token/
 * https://bezkoder.com/spring-boot-jwt-mysql-spring-security-architecture/
 * https://www.javadevjournal.com/spring/spring-security-userdetailsservice/
 * https://jwt.io/introduction
 * https://www.baeldung.com/spring-response-header
 * https://code-held.com/2019/05/09/custom-authentication-with-spring-security/
 * https://www.baeldung.com/spring-response-entity
 * https://www.baeldung.com/spring-security-csrf
 * https://www.baeldung.com/spring-cors
 * https://spring.io/guides/topicals/spring-security-architecture/
 * https://spring.io/guides/topicals/spring-security-architecture/#web-security
 * https://github.com/spring-projects/spring-security/blob/master/web/src/main/java/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.java
 * https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/
 * https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
 * https://scotch.io/tutorials/the-ins-and-outs-of-token-based-authentication
 * https://www.baeldung.com/spring-security-method-security
 * https://www.baeldung.com/spring-security-integration-tests
 * https://docs.splunk.com/Documentation/Splunk/8.1.1/SearchTutorial
 * https://github.com/junit-team/junit5-samples/tree/r5.7.0/junit5-jupiter-starter-maven
 * http://maven.apache.org/shared/maven-archiver/examples/classpath.html
 * https://stackoverflow.com/questions/29920434/maven-adding-mainclass-in-pom-xml-with-the-right-folder-path
 * https://www.baeldung.com/executable-jar-with-maven
 * https://wiki.jenkins.io/display/JENKINS/Builds+failing+with+OutOfMemoryErrors
 * https://stackoverflow.com/questions/37671125/how-to-configure-spring-security-to-allow-swagger-url-to-be-accessed-without-aut


## Udacity Instructor's Project Instructions and Requirements
*The sections below are the instructions that were distributed with [the base code](https://github.com/udacity/nd035-c4-Security-and-DevOps/blob/master/README.md).  They detail what the project entails and what was expected from the student's submission.*  

### Introduction
In this project, you'll have an opportunity to demonstrate the security and DevOps skills that you learned in this lesson by completing an eCommerce application. You'll start with a template for the complete application, and your goal will be to take this template and add proper authentication and authorization controls so users can only access their data, and that data can only be accessed in a secure way. 

### Project Template
First, you'll want to get set up with the template. The template is written in Java using Spring Boot, Hibernate ORM, and the H2 database. H2 is an in memory database, so if you need to retry something, every application startup is a fresh copy.

To use the template, import it in the IDE of your choice as a Spring Boot application. Where required, this readme assumes the eclipse IDE.

Once the project is set up, you will see 5 packages:

* demo - this package contains the main method which runs the application

* model.persistence - this package contains the data models that Hibernate persists to H2. There are 4 models: Cart, for holding a User's items; Item , for defining new items; User, to hold user account information; and UserOrder, to hold information about submitted orders. Looking back at the application “demo” class, you'll see the `@EntityScan` annotation, telling Spring that this package contains our data models

* model.persistence.repositories - these contain a `JpaRepository` interface for each of our models. This allows Hibernate to connect them with our database so we can access data in the code, as well as define certain convenience methods. Look through them and see the methods that have been declared. Looking at the application “demo” class, you’ll see the `@EnableJpaRepositories` annotation, telling Spring that this package contains our data repositories.

* model.requests - this package contains the request models. The request models will be transformed by Jackson from JSON to these models as requests are made. Note the `Json` annotations, telling Jackson to include and ignore certain fields of the requests. You can also see these annotations on the models themselves.

* controllers - these contain the api endpoints for our app, 1 per model. Note they all have the `@RestController` annotation to allow Spring to understand that they are a part of a REST API

In resources, you'll see the application configuration that sets up our database and Hibernate, It also contains a data.sql file with a couple of items to populate the database with. Spring will run this file every time the application starts

In eclipse, you can right click the project and click  “run as” and select Spring Boot application. The application should tell you it’s starting in the console view. Once started, using a REST client, such as Postman, explore the APIs.

Some examples are as below:
To create a new user for example, you would send a POST request to:
http://localhost:8080/api/user/create with an example body like 

```
{
    "username": "test"
}
```


and this would return
```
{
    "id" 1,
    "username": "test"
}
```


Exercise:
Once you've created a user, try  to add items to cart (see the `ModifyCartRequest` class) and submit an order. 

## Adding Authentication and Authorization
We need to add proper authentication and authorization controls so users can only access their data, and that data can only be accessed in a secure way. We will do this using a combination of usernames and passwords for authentication, as well as JSON Web Tokens (JWT) to handle the authorization.

As stated prior, we will implement a password based authentication scheme. To do this, we need to store the users' passwords in a secure way. This needs to be done with hashing, and it's this hash which should be stored. Additionally when viewing their user information, the user's hash should not be returned to them in the response, You should also add some requirements and validation, as well as a confirm field in the request, to make sure they didn't make a typo. 

1. Add spring security dependencies: 
   * Spring-boot-starter-security
1. JWT does not ship as a part of spring security, so you will have to add the 
   * java-jwt dependency to your project. 
1. Spring Boot ships with an automatically configured security module that must be disabled, as we will be implementing our own. This must be done in the Application class.
2. Create password for the user
3. Once that is disabled, you will need to implement 4 classes (at minimum, you can break it down however you like):
   * a subclass of `UsernamePasswordAuthenticationFilter` for taking the username and password from a login request and logging in. This, upon successful authentication, should hand back a valid JWT in the `Authorization` header
   * a subclass of `BasicAuthenticationFilter`. 
   * an implementation of the `UserDetailsService` interface. This should take a username and return a userdetails User instance with the user's username and hashed password.
   *  a subclass of `WebSecurityConfigurerAdapter`. This should attach your user details service implementation to Spring's `AuthenticationManager`. It also handles session management and what endpoints are secured. For us, we manage the session so session management should be disabled. Your filters should be added to the authentication chain and every endpoint but 1 should have security required. The one that should not is the one responsible for creating new users.


Once all this is setup, you can use Spring's default /login endpoint to login like so

```
POST /login 
{
    "username": "test",
    "password": "somepassword"
}
```

and that should, if those are valid credentials, return a 200 OK with an Authorization header which looks like "Bearer <data>" this "Bearer <data>" is a JWT and must be sent as a Authorization header for all other rqeuests. If it's not present, endpoints should return 401 Unauthorized. If it's present and valid, the endpoints should function as normal.

### Testing
You must implement unit tests demonstrating at least 80% code coverage.

### CI/CD

This project is linked to a Jenkins pipeline on an AWS Server.

