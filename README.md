# Sohu Pizza Ordering Website
## Product Service
This service provides APIs for product service that can be use to get product and product card information from database.
## API contract
See the [wiki](https://github.com/Sunfl4wer/sohu-pizza-product-service/wiki) of this repository for the API contract.
## How to run
1. Access API from Heroku
* The service is currently being host on Heroku, you can access the API using these server name
    1. Directly to the service: `https://sohu-pizza-product-service.herokuapp.com`
    2. Through an NGINX gateway: `https://sohu-pizza-backend.herokuapp.com`
2. Build and run docker image
    1. In the application-prod.properties
        * Comment this [line](https://github.com/Sunfl4wer/sohu-pizza-product-service/blob/360d7f9eea5d8593731724b0571815832920b8ce/src/main/resources/application-prod.properties#L10)
        * Uncomment this [line](https://github.com/Sunfl4wer/sohu-pizza-product-service/blob/360d7f9eea5d8593731724b0571815832920b8ce/src/main/resources/application-prod.properties#L9)
