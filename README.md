Java Jetty Web Server Example
======

[Tutorial](http://www.avanderw.co.za/my-adventures-into-rest-with-java-jetty-jrebel-jersey-maven-and-intellij/)

Usage
* In browser go to http://localhost:8081
    - Directs to index.html

In REST client try various GET and POST methods:
* GET http://localhost:8081/jersey/hello 
    - Response header "Content-Type"=text/plain
* GET http://localhost:8081/jersey/hello
    - Response header "Content-Type"=text/html
* GET http://localhost:8081/jersey/hello
    - Response header "Content-Type"=text/xml
* POST http://localhost:8081/jersey/hello
    - Response header "Content-Type"=text/plain

