# Invoices fact schema

# --- !Ups

CREATE TABLE f_invoices (  
    id SERIAL PRIMARY KEY,
    invoice_id varchar(64) NOT NULL,
    line_item_id varchar(64) NOT NULL,
    customer_id varchar(64) NOT NULL,
    product_id varchar(64) NOT NULL,
    product_quantity decimal(13,4) NOT NULL,
    net_amount decimal(13,4),
    vat_amount decimal(13,4),
    total_amount decimal(13,4) NOT NULL,
    invoice_datetime datetime NOT NULL, 
    warehouse_id varchar(64),
  	supplier_id varchar(64),
  	salesperson_id varchar(64),
  	cancel_id varchar(64) 	
);

# --- !Downs

DROP TABLE f_invoices;  