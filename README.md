# Misr Digital Factory - Assignment

### João Paulo Ribeiro da Silva - [joaop.ribs@gmail.com](mailto:joaop.ribs@gmail.com)

## Setup

### Software

To be able to run this small app, you will need to install the following on your environment:
* Java 8: https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html
  

* MySQL Community Server 8.0.29: https://dev.mysql.com/downloads/mysql/
```
# On my operating system (MacOS Catalina 10.15.7), I installed MySQL using homebrew:
> brew install mysql
> mysql_secure_installation
```

### Database

On the project's root directory, there is a SQL script `database.sql`. This script creates the database for this app, the tables, the seed data, and the user that the app will use.

To run the script, first make sure MySQL is running. You need to connect to MySQL using the root user and load the SQL file:

```
# Starting MySQL (this varies per system)
> brew services start mysql 

# This will ask for the password for root user
> mysql -u root -p

# This will execute the SQL script
mysql> source <path_to_sql_script>/database.sql
```

### Email alerts

For sending the email alerts when the app cannot communicate with the sensors, I created the Gmail account [misr.assignment@gmail.com](mailto:misr.assignment@gmail.com), and set the appropriate SMTP configurations to send messages from it.

### Configurations

The configurations are all set on the `application.properties` file. You can change the values as you need.


* `server.port`: The port on which the server will run. Set to `8085`.

  
* `spring.datasource.url`: The datasource URL. This assumes that MySQL is running on `localhost`, port `3306`


* `spring.mail`: These are the configurations to send emails from Gmail. You can change these if you wish not use Gmail.


* `alert-recipient`: The email address to send the alerts. Set to `joaop.ribs@gmail.com`, but you can change it to any other email address you wish, in order to see alerts coming.


* `max-number-of-attempts`: Number of attempts to try to send requests to sensor. Set to `5`.


* `number-of-attempts-until-sensor-is-successful`: This is the number of tries until the sensor returns successfully. This is useful for mocking the sensor. If you set it for a value greater than `max-number-of-attempts`, then it will not succeed, and an alert will be sent.


## Execution

Make sure MySQL is running on port `3306`. Then install the app and run Spring Boot using the provided Maven wrapper:

`./mvnw clean install -U`

`./mvnw spring-boot:run`

## Models

### Area Type

This represents the type of agriculture for the plots. Each record has id and name (String).

### Plot Of Land

Each Plot of Land has the following fields: id, name (String), amount_of_water (Integer), time_slots (Integer), area_type and status (String, "IDLE" or "IRRIGATING").

## API Endpoints

All endpoints consume/produce text/JSON. On the root directory of the project, there is a Postman collection `Misr Assignment.postman_collection.json` with a sample request for all these API endpoints.

### Area types

* **Create Area Type**: `POST http://localhost:8085/area_types`

Request body sample:
```
{
    "name": "Corn"
}
```

* **Get Area Type by ID**: `GET http://localhost:8085/area_types/{id}`

  
* **List all Area Types**: `GET http://localhost:8085/area_types`


* **List Plots of Land of Area Type**: `GET http://localhost:8085/area_types/{id}/plots_of_land`


* **Update Area Type**: `PUT http://localhost:8085/area_types/{id}`

Request body sample:
```
{
    "name": "Rice"
}
``` 

* **Delete Area Type**: `DELETE http://localhost:8085/area_types/{id}`


* **Predict Ratio**: This is the bonus item mentioned in the assignment. It calculates (slots time / amount of water) based on the given area type. `GET http://localhost:8085/area_types/{id}/predict_ratio`

### Plots of Land

* **Create Plot of Land**: `POST http://localhost:8085/area_types/{areaTypeId}/plots_of_land`

Request body sample:
```
{
    "name": "Field of Rice",
    "amountOfWater": 3000,
    "timeSlots": 10
}
```

* **Get Plot of Land by ID**: `GET http://localhost:8085/plots_of_land/{id}`


* **List all Plots of Land**: `GET http://localhost:8085/plots_of_land`


* **Update Plot of Land**: `PUT http://localhost:8085/plots_of_land/{id}`

Request body sample:
```
{
    "name": "First plot of land",
    "amountOfWater": 3000,
    "timeSlots": 10
}
```

* **Delete Plot of Land**: `DELETE http://localhost:8085/plots_of_land/{id}`


### Irrigation System

* **Start Irrigation**: `POST http://localhost:8085/irrigation/{plotOfLandId}/start`


* **Stop Irrigation**: `POST http://localhost:8085/irrigation/{plotOfLandId}/stop`


## Unit Tests

There are 30 unit tests, giving a coverage of 93% classes, 93% lines covered. To run the tests, use this command: `./mvnw test`

## How to communicate with the IoT devices

The idea of how the IoT devices would communicate with the system is that for each plot of land, the system would send the amount of water and time slots. This can be achieved using the Start/Stop Irrigation API endpoints, which take the Plot of Land ID as a parameter. 