FROM openjdk:8-jdk-alpine
ADD target/pizza.product-0.0.1-SNAPSHOT.jar docker-pizza-product.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","docker-pizza-product.jar"]
