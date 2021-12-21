# Fintech-Team 8

Workstation specifications
• Windows 10 or Linux or MacOS, 64 bit
• RAM, 8GB required, 16GB preferable
• 128 GB Storage (SSD would be preferrable)
• 4 Core CPU

Preparation:
1. Install [Postman Client](https://www.postman.com/product/rest-client/). 
2. Download and install [Docker](https://www.docker.com/).
3. [Download RabbitMQ](https://www.rabbitmq.com/download.html) and install.


Instructions:
1. Execute command:```docker container run -p 15672:15672 -p 5672:5672 -d rabbitmq:3-management```
2. RabbitMQ Management - credentials - username: guest password: guest
3. Run Edge
4. Run Integration
 [On Postman API Platform](https://www.postman.com):
5. POST: localhost:8080/api-wallet/wallet-feeder
 For testing purposes:
6. GET: localhost:8080/api-wallet/accounts


Sample JSON file for /api-wallet/wallet-feeder/
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
Sample JSON file for /api/feeder/
```json
{
"cid": "CU084647410",
"creditorName": "Thomas Thomaidis",
"creditorIBAN": "GR44025635700006",
"debtorName": "Dimitris Iraklis",
"debtorIBAN": "GR74813235701234",
"paymentAmount": "28.90",
"valuerDate": "20211210",
"paymentCurrency": "EUR",
"feeAmount":"6",
"feeCurrency","EUR"
}
```
