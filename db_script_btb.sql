CREATE DATABASE bus_ticket_booth;

\c bus_ticket_booth

-- Creating the Ticket status Enum Type
CREATE TYPE ticket_status_type AS ENUM ('booked', 'refunded');

-- Creating the Buses Table
CREATE TABLE buses (
    bus_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bus_serial_number VARCHAR(50) UNIQUE,
    seat_capacity INT CHECK (seat_capacity >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating the Stops Table
CREATE TABLE stops (
    stop_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    stop_name VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating the Routes Table
CREATE TABLE routes (
    route_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    route_name VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating Routes_Stops Associative Table
CREATE TABLE routes_stops (
    route_id INT REFERENCES routes(route_id) ON DELETE CASCADE,
    stop_id INT REFERENCES stops(stop_id) ON DELETE CASCADE,
    sequence_number INT, -- To indicate the order of stops in the route
    departure_minutes_offset INT, -- Time in minutes from start route(allows multiple buses use the same route)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (route_id, stop_id)
);

-- Creating the Schedule Table
CREATE TABLE schedule (
    schedule_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    route_id INT REFERENCES routes(route_id) ON DELETE CASCADE,
    bus_id INT REFERENCES buses(bus_id) ON DELETE CASCADE,
    departure_datetime timestamp,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating the Tickets Table
CREATE TABLE tickets (
    ticket_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    schedule_id INT REFERENCES schedule(schedule_id) ON DELETE SET NULL,
    stop_id INT REFERENCES stops(stop_id) ON DELETE SET NULL,
    seat_number INT CHECK (seat_number>= 0),
    ticket_status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



-- Creating the Trigger Function that will update updated_at field in Tables
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Creating a Function to Apply Triggers to Multiple Tables
CREATE OR REPLACE FUNCTION create_update_triggers() RETURNS VOID AS $$
DECLARE
    table_name TEXT;
BEGIN
    FOR table_name IN
        SELECT unnest(ARRAY['buses', 'routes', 'stops', 'routes_stops', 'schedule', 'tickets'])
    LOOP
        EXECUTE format('
            CREATE TRIGGER update_%1$s_modtime 
            BEFORE UPDATE ON %1$I 
            FOR EACH ROW 
            EXECUTE FUNCTION update_modified_column();', 
            table_name);
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Run the Function to Create Triggers
SELECT create_update_triggers();