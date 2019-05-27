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
* Body: always returns a list of all companies present in the system.
* Status Code: always 200.

`GET /api/companies?coname={name}` **PUBLIC**
* Body: returns a list of companies whose name matches the given coname query parameter. Since company names are unique, this will either be a list of one company (indicating a match) or a list of zero companies (indicating failure).
* Status Code: always 200.

### Positions

`GET /api/positions?coname={name}` **PUBLIC**
* Body: returns a list of positions whose company name matches the given coname query parameter. Could be empty if a company does not have any positions available or the company does not exist.
* Status Code: always 200.

### Authentication

`POST /api/auth/signup` **PUBLIC**
`POST /api/auth/login` **PUBLIC**
`GET /api/auth/me` **AUTHENTICATED**

### Applications

`GET /api/applications?coname={name}` **AUTHENTICATED**
<br/>
`POST /api/applications` **AUTHENTICATED**
<br/>
`PUT /api/applications/{id}` **AUTHENTICATED**
<br/>

### Suggestions

`POST /api/suggestions` **AUTHENTICATED**
