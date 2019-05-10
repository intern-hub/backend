# Backend for InternHub
Built with Java Spring.

To run locally:

First set up the database:

    $ mysql -uroot
    mysql > create database db_example;
    mysql > create user 'springuser'@'%' identified by 'spring';
    mysql > grant all on db_example.* to 'springuser'@'%';

This gives a new user `springuser` with password `spring` giving it all access to a new empty database db_example.

Then spin up the backend:

1. Import project into IntelliJ
2. Turn on annotation processing in Settings.
3. Run InternhubApplication.java
4. Endpoint will open up at localhost:8080
5. Current Company object only has a name field. POST, GET, PUT, and DELETE are all supported operations.
