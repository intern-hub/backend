# Backend for InternHub

_Please see https://internhub.us.to for a live demonstration._

This server will run on port 5000.

There is already a MySQL server running on AWS.
Run `./gradlew bootRun` to connect to the server.
POST, PUT, GET, and DELETE are all supported operations.

## API Documentation 

`GET /api/companies` **PUBLIC**: 
`GET /api/companies?coname={name}` **PUBLIC**:

`GET /api/positions?coname={name}` **PUBLIC**:

`GET /api/auth/me` **AUTHENTICATED**:
`POST /api/auth/signup` **PUBLIC**:
`POST /api/auth/login` **PUBLIC**:

`GET /api/applications?coname={name}` **AUTHENTICATED**:
`POST /api/applications` **AUTHENTICATED**:
`PUT /api/applications/{id}` **AUTHENTICATED**:

`POST /api/suggestions` **AUTHENTICATED**:
