# Bus Ticket Booth
This project implements a Java web app that represents bus ticket booth.

Here are some of the main features of the app:
* Create schedules(specific route that a given bus will travel at a given time)
* Purchase and refund tickets according to the schedule.
* Search for the cheapest schedule to a particular stop.

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)

## Installation

To run the project locally, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/bus-ticket-booth_TECH.git
   ```

2. Open the project in your preferred IDE.

3. Set up the database:

   * Install and configure PostgreSQL on your system.
   * download 2 scripts from my gist(https://gist.github.com/YaremaMaksym/a18c90d51817db7b75533f5c68e91baf)
      or from this repository
   * run `db_script_btb.sql` to create db
   * run `dummy_data_btb.sql` to add some data to db
   * Update the `application.properties` file (see [Configuration](#configuration)) with your PostgreSQL credentials.

4. Run the application

The application should now be running on [http://localhost:8080](http://localhost:8080).

## Configuration
The project uses the `application.properties` file to configure the database connection. Here is an example of the file contents:

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