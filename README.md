# currency_convertor
This is a Java Currency Convertor API using exchange rates provided at https://exchangeratesapi.io/

this is sample exercise for Nosto Solutions

1.  you need JAVA 17 or higher and maven to compile this code base

2.  runnable JAR is available in the /run folder

3.  to start the server application run the /run/run.bat file

4.  by default server will run on port 8080

**client page (CurrencyConverterAdmin.html) is also available in the /run folder**

after server is started double click on **CurrencyConverterAdmin.html** page to see the converter client app

page is by default hard corded to send request to **localhost:8080/api/currency-converter?** end point.

For Testing the endpoint in Postman

using postmen after server is start , put a get request to localhost:8080/api/currency-converter?source=ABC&target=USD&amount=5

sample response

{
"sourceCurrency": "ABC",
"targetCurrency": "USD",
"monetaryValue": 5,
"rate": 0,
"result": 0,
"message": "Invalid Source Currency",
"status": -1
}

using the admin page just double-click on the run/CurrencyConverterAdmin.html 

Extra Points: **implemented and can be view in browser**
Extra Points: **by default enabled in spring boot**
Extra Points: **my Amazon free tyre is expired**
Extra Points: **spring-boot-starter-actuator and required configuration is added it is just a matter of
    To use Grafana and Prometheus together we will have to configure Prometheus as a data source in Grafana,**
