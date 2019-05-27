# Backend for InternHub

_Please see https://internhub.us.to for a live demonstration._
<br/>
_Swagger documentation can be found at https://internhub.us.to/swagger-ui.html._

This server will run on port 5000.

There is already a MySQL server running on AWS.
Run `./gradlew bootRun` to connect to the server.
POST, PUT, GET, and DELETE are all supported operations.

## API Documentation 

**NOTE**: If a route is labeled as authenticated, this means that any requests without a proper Authorization header will be refused. The contents of a valid authorization header looks like: `Bearer {token}`, where {token} should be replaced with the JWT token received after POSTing to `/api/auth/login` with vaild credentials.

### Companies

`GET /api/companies` **PUBLIC**

| Component | Description |
| ------------- | ------------- |
| Response Body | Always returns a list of all companies present in the system.  |
| Status Code | Always 200. |

`GET /api/companies?coname={name}` **PUBLIC**

| Component | Description |
| ------------- | ------------- |
| Response Body | Returns a list of companies whose name matches the given coname query parameter. Since company names are unique, this will either be a list of one company (indicating a match) or a list of zero companies (indicating failure). |
| Status Code | Always 200. |

### Positions

`GET /api/positions?coname={name}` **PUBLIC**

| Component | Description |
| ------------- | ------------- |
| Response Body | Returns a list of positions whose company name matches the given coname query parameter. Could be empty if a company does not have any positions available or the company does not exist. | 
| Status Code | Always 200. |

### Authentication

`POST /api/auth/signup` **PUBLIC**

| Component | Description |
| ------------- | ------------- |
| Request Body | `{ "username": "USERNAME", "password": "PLAINTEXT PASSWORD", "email": "EMAIL" }` | 
| Response Body | No body on success. Typical error JSON body otherwise. | 
| Status Code | 400 if JSON body is malformed or a required field isn't present. 409 if user already exists. 200 if signup was a success. |

`POST /api/auth/login` **PUBLIC**

| Component | Description |
| ------------- | ------------- |
| Request Body | `{ "username": "USERNAME", "password": "PLAINTEXT PASSWORD" }` | 
| Response Body | `{ "token": "JWT TOKEN" }` on success. Typical error JSON body otherwise. | 
| Status Code | 400 if JSON body is malformed. 401 if authentication fails (i.e missing or bad credentials). | 

`GET /api/auth/me` **AUTHENTICATED**

| Component | Description |
| ------------- | ------------- |
| Response Body | Will return the user object corresponding to the JWT token specified in the Authorization header of the request. Password field will be omitted. | 
| Status Code | 403 if not authenticated. 200 otherwise. | 

### Applications

`GET /api/applications?coname={name}` **AUTHENTICATED**

| Component | Description |
| ------------- | ------------- |
| Response Body | | 
| Status Code | | 

`POST /api/applications` **AUTHENTICATED**

| Component | Description |
| ------------- | ------------- |
| Request Body | |
| Response Body | | 
| Status Code | | 

`PUT /api/applications/{id}` **AUTHENTICATED**

| Component | Description |
| ------------- | ------------- |
| Request Body | |
| Response Body | | 
| Status Code | | 

### Suggestions

`POST /api/suggestions` **AUTHENTICATED**

| Component | Description |
| ------------- | ------------- |
| Request Body | |
| Response Body | | 
| Status Code | | 
