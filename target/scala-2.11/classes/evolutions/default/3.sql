# Invoice Payment fact schema

# --- !Ups

CREATE TABLE f_payments (  
    id SERIAL,
    payment_id varchar(64) PRIMARY KEY,
    invoice_id varchar(64) NOT NULL,
    payment_datetime datetime NOT NULL, 
    payment_type varchar(64)
);

# --- !Downs

DROP TABLE f_payments;  