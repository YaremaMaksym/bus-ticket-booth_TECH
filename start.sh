#!/bin/bash

docker-compose up -d postgres-service

echo "Waiting for Postgres to be ready..."
until docker exec some-postgres-container pg_isready -U postgres; do
  sleep 1
done

cat ./init_db.sql | docker exec -i some-postgres-container psql -U postgres
cat ./insert_dummy_data.sql | docker exec -i some-postgres-container psql -U postgres

docker-compose up -d bus-ticket-booth-service

echo "All services are up and running!"
