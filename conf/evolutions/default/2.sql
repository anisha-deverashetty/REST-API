# Invoices fact schema

# --- !Ups

CREATE TABLE f_invoices (  
    id SERIAL,
    invoice_id varchar(64) PRIMARY KEY,
    customer_id varchar(64) NOT NULL,
    net_amount decimal(13,4),
    vat_amount decimal(13,4),
    total_amount decimal(13,4) NOT NULL,
    invoice_datetime datetime NOT NULL, 
    warehouse_id varchar(64),
  	supplier_id varchar(64),
  	salesperson_id varchar(64)
);

# --- !Downs

DROP TABLE f_invoices;  