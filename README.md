# MoneyTransfer

A Java RESTful API for money transfers between users accounts

##Technologies

⋅⋅* SparkJava
⋅⋅* Jooq
⋅⋅* PostgreSql
⋅⋅* Docker
⋅⋅* TestContainers
⋅⋅* Guice
⋅⋅* Mockito

##Available Services

| HTTP METHOD   | PATH                       | USAGE          |
| ------------- |:--------------------------:| --------------:|
| GET           | /users                     | get all users  |
| GET           | /users/:id                 | get user by id |
| GET           | /users                     | get all users  |
| GET           | /users/:username           | get user by username |
| GET           | /accounts                  | get all accounts  |
| GET           | /accounts/:id              | get account by id |
| GET           | /moneyTransfers            | get all moneyTransfers  |
| GET           | /moneyTransfers/:id        | get moneyTransfer by id |
| POST          | /users                     | create a new user  |
| POST          | /accounts/:userId          | create a new account |
| POST          | /moneyTransfer             | perform transaction  |
| PUT           | /users/:id                 | update user |
| PUT           | /accounts/:id              | update account  |
| DELETE        | /accounts/:id              | remove account by id |
| DELETE        | /users/:id                 | remove user by id |

##Http Status
⋅⋅* 200 OK: The request has succeeded
⋅⋅* 400 Bad Request: The request could not be understood by the server
⋅⋅* 404 Not Found: The requested resource cannot be found
⋅⋅* 500 Internal Server Error: The server encountered an unexpected condition

##Sample JSON for User and Account

Create a new User
```javascript
{
    "firstName": "test",
    "lastName": "test",
    "email": "test@gmail.com",
    "username": "test",
    "password": "test"
}
```
Create a new Account
```javascript
{
    "accountHolderName":"test",
    "balance": 10.000
}
```
Perform transaction
```javascript
{
    "fromAccountNumber":4343322889283454872,
    "toAccountNumber":8234025905318676320,
    "amount": 1000.0
}
```
