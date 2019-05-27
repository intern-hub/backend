# Backend for InternHub

_Please see https://internhub.us.to for a live demonstration._

This server will run on port 5000.

There is already a MySQL server running on AWS.
Run `./gradlew bootRun` to connect to the server.
POST, PUT, GET, and DELETE are all supported operations.

## API Documentation 

`GET /api/companies` **PUBLIC**: 
<br/>
`GET /api/companies?coname={name}` **PUBLIC**:
<br/>

`GET /api/positions?coname={name}` **PUBLIC**:
<br/>

`GET /api/auth/me` **AUTHENTICATED**:
<br/>
`POST /api/auth/signup` **PUBLIC**:
<br/>
`POST /api/auth/login` **PUBLIC**:
<br/>

`GET /api/applications?coname={name}` **AUTHENTICATED**:
<br/>
`POST /api/applications` **AUTHENTICATED**:
<br/>
`PUT /api/applications/{id}` **AUTHENTICATED**:
<br/>

`POST /api/suggestions` **AUTHENTICATED**:
