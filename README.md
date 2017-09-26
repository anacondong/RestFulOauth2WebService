"# RestFulOauth2WebService" <br/>
RestFul + Spring 3 + Oauth2
<br/>
Install >> mvn clean install
<br/>
Run
<br/>
Post >> http://localhost:8080/RestFulOauth2WebService/oauth/token?grant_type=password&username=anacondong&password=password
      - Basic Auth
      userName =  my-trusted-client
      password =  secret
      
<br/>      
Get >> http://localhost:8080/RestFulOauth2WebService/persons?access_token=0c58a57f-2bb2-424b-b6fd-428baa202131
