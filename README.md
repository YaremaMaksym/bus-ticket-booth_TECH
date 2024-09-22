# Bus Ticket Booth
This project implements a Java web app that represents bus ticket booth.

Here are some of the main features of the app:
* Create schedules(specific route that a given bus will travel at a given time)
* Purchase and refund tickets according to the schedule.
* Search for the cheapest schedule to a particular stop.

## Table of Contents

- [Demo](#demo)
- [Technologies](#technologies)
- [Installation](#installation)
- [Configuration](#configuration)

## Demo
Demonstration of schedules page
![NVIDIA_Share_tFEmhmCcmb](https://github.com/YaremaMaksym/bus-ticket-booth_TECH/assets/31901135/29d822c2-c04a-4285-baff-fbd032232d51)

## Technologies

The project uses the following technologies and frameworks:

- Java
- Maven (for project management)
- Spring (Framework, Boot, MVC, Data JPA)
- REST APIs
- Thymeleaf
- HTML
- Postgres
- Lombok (for boilerplate reduction)
- Mokito (for testing)
- Docker (containerization platform)
- Git (for version control)

## Installation

### Using Docker
To run the project in docker, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/bus-ticket-booth_TECH.git
   ```

2. Run `start.sh` script. It will run containers, init db and insert dummy data

The application should now be running on [http://localhost:9090](http://localhost:9090).

### Using IDE
To run the project in IDE, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/bus-ticket-booth_TECH.git
   ```

2. Open the project in your preferred IDE.

3. Set up the database:

   * Install and configure PostgreSQL on your system.
   * run `init_db.sql` to create db
   * run `insert_dummy_data.sql` to add some data to db
   * Update the `application.properties` file (see [Configuration](#configuration)) with your PostgreSQL credentials.

4. Run the application

The application should now be running on [http://localhost:8080](http://localhost:8080).


## Configuration
The project uses the `application.properties` file to configure the database connection locally. Here is an example of the file contents:

```
spring:
    datasource:
        url: jdbc:postgresql://localhost:5432/bus_ticket_booth
        username: postgres
        password: pass
    mvc:
        hiddenmethod:
            filter:
                enabled: true
```

Make sure to replace `postgres` and `pass` with your actual PostgreSQL database credentials.
