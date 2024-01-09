FROM openjdk:17-oracle
ADD target/bus-ticket-booth_TECH.jar bus-ticket-booth.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/bus-ticket-booth.jar"]