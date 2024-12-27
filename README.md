# Spring Boot Web Flux Products API 
<img src="https://media.licdn.com/dms/image/v2/D5612AQGhS83VpBxWoA/article-cover_image-shrink_720_1280/article-cover_image-shrink_720_1280/0/1720550988047?e=1736380800&v=beta&t=gRNkoFT6IqPYo1rg9U_WOEM_bHeLv5KUup3UgtBR9s8" alt="Spring Boot Logo" width="200" height="100"/>




* **This API is created for educational purposes.**
* **What exactly is this API?**
    * This API is essentially a CRUD API for managing products, inspired in plataforms 
    like Amazon, Mercado Livre etc.
    * Users can be stores admin( Can add and modify products from his store ), normal users( users can see product etc. but can't 
    change informations ) and admin user( 
    have access in some admin enpoints like create a product for any store  )
    * For the authentication uses Spring security combined with JWT to generate tokens. 
  * The API stores products in MongoDB Atlas and uses MINIO to store the product images.

* **It’s free to use! 😉**

---

# Dependencies Used

Here’s a list of the key dependencies used in this project:

| Dependency                     | Version  |
|--------------------------------|----------|
| **Spring Boot Web Flux**       | 3.3.3    |
| **Spring Data mongo Reactive** | 3.3.5    |
| **Spring cloud config**        | 2023.0.3 |
| **JDK LTS**                    | 17       |
| **JUnit**                      | 5        |
| **Lombok**                     | 1.18     |
| **Spring security**            | 6.3.4    |
| **Java JWT**                   | 4.4.0    |
| **Testcontainers**             | 1.19.8   |
| **MINIO java SDK**             | 8.5.7    |
| **Opendoc API**                | 2.7.0    |


---
# Database Used

**This API originally works with MongoDB Atlas, but
you can configure  it to run with other
MongoDB servers.**

**To storage the products images this API uses MINIO, like s3 buckets ( for more informations: https://min.io/docs/minio/container/index.html ).**

---

# How to Run

To run the API, you need to start the MINIO docker container, cloud config server and the API.

*To run the MINIO docker just use the docker compose send in this repository. 
Use this code to run and stop de docker container:

```bash
#to run
docker-compose up -d 

#to stop 
docker-compose stop
```


*To run the API:
```bash
./gradlew bootRun
```

---

# Documentation 

* This API uses opendoc API and swagger. 
To see documentation run the application and type this url in your browser: 

```bash
http://localhost:8080/webjars/swagger-ui/index.html#/
```

---

# Cloud config Config

* This is example of config to get mongo configs in your cloud config: 

```bash
spring:
  data:
    mongo:
    mongodb:
      uri: EXAMPLE URI
      database: EXAMPLE DATABASE

api:
  base:
    url: EXAMPLE URL
  key: EXAMPLE KEY
  
minio:
  access:
    key: admin
  secret:
    key: password
  url: http://localhost:9000
  bucket:
    name: product-images

```


