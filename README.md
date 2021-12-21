# Fintech-Team 8

Workstation specifications: </br>
• Windows 10 or Linux or MacOS, 64 bit </br>
• RAM, 8GB required, 16GB preferable </br>
• 128 GB Storage (SSD would be preferrable) </br>
• 4 Core CPU </br>

Preparation:
1. Install [Postman Client](https://www.postman.com/product/rest-client/). 
2. Download and install Docker.
3. Download RabbitMQ and install.


Instructions:
1. Execute command:```docker container run -p 15672:15672 -p 5672:5672 -d rabbitmq:3-management```
2. RabbitMQ Management - credentials - username: guest password: guest
3. Run Edge
4. Run Integration
 [On Postman API Platform](https://www.postman.com):
5. POST: localhost:8080/api-wallet/wallet-feeder
 For testing purposes:
6. GET: localhost:8080/api-wallet/accounts


Sample JSON file:
```json
{
"cid": "CU084647410",
"creditorName": "Thomas Thomaidis",
"creditorIBAN": "GR44025635700006",
"debtorName": "Dimitris Iraklis",
"debtorIBAN": "GR74813235701234",
"paymentAmount": "28.90",
"valuerDate": "20211210",
"paymentCurrency": "EUR"
}
```
