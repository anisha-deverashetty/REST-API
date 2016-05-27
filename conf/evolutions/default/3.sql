# Invoice Payment fact schema

# --- !Ups

CREATE TABLE f_payments (  
    id SERIAL,
    payment_id varchar(64) PRIMARY KEY,
    invoice_id varchar(64) NOT NULL,
    payment_datetime datetime NOT NULL, 
    payment_type varchar(12),
    INDEX invoice_index (invoice_id)
);

# --- !Downs

DROP TABLE f_payments;  