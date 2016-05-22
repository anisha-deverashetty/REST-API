# Customers dimension schema

# --- !Ups

CREATE TABLE d_customers (  
    id SERIAL PRIMARY KEY,
    customer_id varchar(64) NOT NULL,
    customer_firstname varchar(32) NOT NULL,
    customer_lastname varchar(32),
    customer_email varchar(32),
  	customer_phone varchar(12),
  	customer_address varchar(64),
  	customer_zipcode varchar(6),
  	customer_city varchar(12),
  	customer_country varchar(12)
);

# --- !Downs

DROP TABLE d_customers;  