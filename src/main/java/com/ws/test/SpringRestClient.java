package com.ws.test;
 
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.ws.entity.AuthTokenInfo;
import com.ws.entity.Person;
 
public class SpringRestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8080/RestFulOauth2WebService";
    
    public static final String AUTH_SERVER_URI = "http://localhost:8080/RestFulOauth2WebService/oauth/token";
    
    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=anacondong&password=password";
    
    public static final String QPM_ACCESS_TOKEN = "?access_token=";

    /*
     * Prepare HTTP Headers.
     */
    private static HttpHeaders getHeaders(){
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
    }
    
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    private static HttpHeaders getHeadersWithClientCredentials(){
    	String plainClientCredentials="my-trusted-client:secret";
    	String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
    	
    	HttpHeaders headers = getHeaders();
    	headers.add("Authorization", "Basic " + base64ClientCredentials);
    	return headers;
    }    
    
    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    @SuppressWarnings({ "unchecked"})
	private static AuthTokenInfo sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI+QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthTokenInfo tokenInfo = null;
        
        if(map!=null){
        	tokenInfo = new AuthTokenInfo();
        	tokenInfo.setAccess_token((String)map.get("access_token"));
        	tokenInfo.setToken_type((String)map.get("token_type"));
        	tokenInfo.setRefresh_token((String)map.get("refresh_token"));
        	tokenInfo.setExpires_in((int)map.get("expires_in"));
        	tokenInfo.setScope((String)map.get("scope"));
        	System.out.println(tokenInfo);
        	//System.out.println("access_token ="+map.get("access_token")+", token_type="+map.get("token_type")+", refresh_token="+map.get("refresh_token")
        	//+", expires_in="+map.get("expires_in")+", scope="+map.get("scope"));;
        }else{
            System.out.println("No Person exist----------");
            
        }
        return tokenInfo;
    }
    
    /*
     * Send a GET request to get list of all Persons.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void listAllPersons(AuthTokenInfo tokenInfo){
    	Assert.notNull(tokenInfo, "Authenticate first please......");

    	System.out.println("\nTesting listAllPersons API-----------");
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/persons/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> PersonsMap = (List<LinkedHashMap<String, Object>>)response.getBody();
        
        if(PersonsMap!=null){
            for(LinkedHashMap<String, Object> map : PersonsMap){
                System.out.println("Person : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No Person exist----------");
        }
    }
     
    /*
     * Send a GET request to get a specific Person.
     */
    private static void getPerson(AuthTokenInfo tokenInfo){
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting getPerson API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<Person> response = restTemplate.exchange(REST_SERVICE_URI+"/persons/1"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		HttpMethod.GET, request, Person.class);
        Person Person = response.getBody();
        System.out.println(Person);
    }
 
    public static void main(String args[]){
    	AuthTokenInfo tokenInfo = sendTokenRequest();
    	getPerson(tokenInfo);
    	listAllPersons(tokenInfo);
    }
}