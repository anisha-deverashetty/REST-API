# REST-API
REST API using Scala Play Framework

## REST API

The REST API allows to add customer, invoice and payment for invoice. It also allows to retrieve all customer data including invoices 
and payments for each customer. The API is developed using Scala and Play Framework. HOST: http://hosturl/

## Customer [/customer]

The customer data can be added with POST request. It takes a JSON
array object containing one or many customer data. 

### Add Customers [POST]

+ Attributes (Customer)

+ Request (application/json)

        [
            {
            "customer_id" : "4",
            "customer_firstname" : "Alex",
            "customer_lastname" : "A",
            "customer_email" : "alex@gmail.com",
            "customer_phone" : "3584999999",
            "customer_address" : "kamppi",
            "customer_zipcode" : "00100",
            "customer_city" : "Helsinki",
            "customer_country" : "Finland"
            }
        ]

+ Response 200 (application/json)
    When request has been processed successfully this response is obtained
    
    + Headers

            Location: /customer

    + Body

            {
              "status": "OK",
              "message": "1 Customer(s) added"
            }

+ Response 400 (application/json)
    When required fields are missing this response is obtained
    
    + Headers

            Location: /customer

    + Body

            {
              "status": "Not OK",
              "message": {
                "obj[0].customer_firstname": [
                  {
                    "msg": [
                      "error.path.missing"
                    ],
                    "args": []
                  }
                ]
              }
            }

+ Response 500 (application/json)
    When dupicate entry is sent this response is obtained
    + Headers

            Location: /customer

    + Body

            {
              "status": "Not OK",
              "error": "Duplicate entry '19' for key 'PRIMARY'"
            }

## Get all Customer data [/customer/{id}]
   
    Retrieves all customer data including invoices and payments for given customer ID.

### Get all customer data [GET]

+ Parameters
    + id (required, string, `c01`) ... Customer ID
    
+ Response 200 (application/json)
        
        When request has been processed successfully this response is obtained
     
        {
          "status": "OK",
          "message": {
            "customer_id": "15",
            "customer_firstname": "Steve",
            "customer_lastname": "Jhon",
            "invoices": [
              {
                "invoice_id": "in_15",
                "customer_id": "15",
                "total_amount": 3,
                "invoice_datetime": "2016-05-24 23:09:33",
                "payment": {
                  "payment_id": "65",
                  "invoice_id": "in_15",
                  "payment_datetime": "2016-05-22 23:09:33"
                }
              },
              {
                "invoice_id": "in_16",
                "customer_id": "15",
                "total_amount": 3,
                "invoice_datetime": "2016-05-24 23:09:33"
              },
              {
                "invoice_id": "in_17",
                "customer_id": "15",
                "total_amount": 3,
                "invoice_datetime": "2016-05-24 23:09:33",
                "payment": {
                  "payment_id": "66",
                  "invoice_id": "in_17",
                  "payment_datetime": "2016-05-22 23:09:33"
                }
              },
              {
                "invoice_id": "in_18",
                "customer_id": "15",
                "total_amount": 3,
                "invoice_datetime": "2016-05-24 23:09:33"
              }
            ]
          }
        }

+ Response 404 (application/json)
    When customer id is not found.
    
    + Headers

            Location: /customer

    + Body

            {
              "status": "Not OK",
              "error": "Customer id not found"
            }

## Invoice [/invoice]

The invoice data can be added with POST request. It takes a JSON
array object containing one or many invoice data. 

### Add Invoices [POST]

+ Attributes (Invoice)

+ Request (application/json)

        [
            {
                "invoice_id": "in_10001",
                "customer_id": "Cus_01",
                "net_amount": 10,
                "vat_amount": 2.9,
                "total_amount" :12.9,
                "invoice_datetime" : "2016-05-22 23:09:33",
                "warehouse_id" : "w_01",
                "supplier_id" : "S_01",
                "salesperson_id" : "sp_01"
            }
        ]
 

+ Response 200 (application/json)

    When request has been processed successfully this response is obtained
    
    + Headers

            Location: /invoice

    + Body

            {
              "status": "OK",
              "message": "1 Invoice(s) added"
            }

+ Response 400 (application/json)
    When required fields are missing this response is obtained
    
    + Headers

            Location: /invoice

    + Body

            {
              "status": "Not OK",
              "message": {
                "obj[0].invoice_id": [
                  {
                    "msg": [
                      "error.path.missing"
                    ],
                    "args": []
                  }
                ]
              }
            }

+ Response 500 (application/json)
    When dupicate entry is sent this response is obtained
    
    + Headers

            Location: /invoice

    + Body

            {
              "status": "Not OK",
              "error": "Duplicate entry 'In_1001' for key 'PRIMARY'"
            }

### Payment [/payment]

The payment data can be added with POST request. It takes a JSON
array object containing one or many payments. 

### Add Payments [POST]

+ Attributes (Payment)

+ Request (application/json)

        [
            {
                "payment_id": "1",
                "invoice_id": "in10001",
                "payment_datetime" : "2016-05-22 23:09:33",
                "payment_type" : "Visa Card"
            }
        ]

+ Response 200 (application/json)
    When request has been processed successfully this response is obtained
    
    + Headers

            Location: /payment

    + Body

            {
              "status": "OK",
              "message": "1 Payment(s) added"
            }

+ Response 400 (application/json)
    When required fields are missing this response is obtained
    
    + Headers

            Location: /payment

    + Body

            {
              "status": "Not OK",
              "message": {
                "obj[0].payment_id": [
                  {
                    "msg": [
                      "error.path.missing"
                    ],
                    "args": []
                  }
                ]
              }
            }

+ Response 500 (application/json)
    
    When dupicate entry is sent this response is obtained
    
    + Headers

            Location: /payment

    + Body

            {
              "status": "Not OK",
              "error": "Duplicate entry 'p_1001' for key 'PRIMARY'"
            }

# Data Structures

## Customer (object)

+ customer_id: c01 (string, required) - Customer ID,
+ customer_firstname: Steve (string, required) - First name,
+ customer_lastname: John (string, optional) - Last name,
+ customer_email: John@gmail.com (string, optional) - Email,
+ customer_phone: 9991198199 (string, optional) - Phone,
+ customer_address: John (string, optional) - Address,
+ customer_zipcode: 00100 (string, optional) - Zipcode,
+ customer_city: Helsinki (string, optional) - City,
+ customer_country: Finland (string, optional) - Country

## Invoice (object)

+ invoice_id: in10001 (string, required) - Invoice ID,
+ customer_id: c01 (string, required) - Customer ID
+ net_amount: 10 (string, required) - Net Amount,
+ vat_amount: 2.9 (string, required) - Vat Amount,
+ total_amount: 12.9 (string, required) - Total Amount,
+ invoice_datetime: `2016-05-22 23:09:33` (string, required) - Invoice DateTime ("yyyy-MM-dd HH:mm:ss"),
+ warehouse_id: w01 (string, optional) - Warehouse ID,
+ supplier_id: S01 (string, optional) - Supplier ID,
+ salesperson_id: sp01 (string, optional) - Salesperson ID

## Payment (object)

+ payment_id: 1 (string, required) - Payment ID,
+ invoice_id: in10001 (string, required) - Invoice ID,
+ payment_datetime: `2016-05-22 23:09:33` (string, required) - Payment DateTime ("yyyy-MM-dd HH:mm:ss"),
+ payment_type: Visa Card (string, optional) - Payment Type
