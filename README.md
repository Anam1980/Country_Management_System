# Country Management System

The Country Management System is a web application that provides information about different countries. It allows users to retrieve details about a specific country, and filter and sort a list of countries based on various criteria.


## Prerequisites
- IntelliJ IDEA
- Maven
- Java
- Postman

## Running the Application
1. Clone this repository in IntelliJ.
2. In Postman, authenticate using cURL by hitting the authentication endpoint: `http://localhost:8080/auth/login` and method: 'POST'.

   ```bash
    curl --location 'http://localhost:8080/auth/login' \
    --header 'Content-Type: application/json' \
   --data '{
    "username":"User",
    "password":"Pass123"
   }'
    ```

    or

    ```bash
    curl --location 'http://localhost:8080/auth/login' \
    --header 'Content-Type: application/json' \
   --data '{
    "username":"Anam",
    "password":"Anam123"
   }'
    ```

3. Copy the JWT token received in the response.

4. In Postman, add the token to the header:
   - Key: `Authorization`
   - Value: `Bearer your_copied_token`
5.  Please be aware that the token will expire after 5 hours. You may need to re-authenticate and obtain a new token when the current token expires.

## Making API Requests

1. Get Country Information by name with the endpoint: `http://localhost:8080/country/getInfo/country_name/{countryName}`.
 ```bash
curl --location 'http://localhost:8080/country/getInfo/country_name/eesti' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbmFtIiwiaWF0IjoxNzAzNTc1MTE2LCJleHAiOjE3MDM1OTMxMTZ9.xlm8TRyETVRnD5seeCbtlJuxtIpxmRd-U2bcwp3L1_GweohDiGsK3pP56BGNV5for0-7uSu9EyE96yJcBo3yoQ'
```

2. Get a List of Country Names Based on the Filter and Sort with the endpoint: `http://localhost:8080/country/getCountries?language={language}&population={minPopulation}&area={minArea}&sort={asc/desc}&page={pageNo}&pageSize={pageSize}`
 ```bash
curl --location 'http://localhost:8080/country/getCountries?language=spanish&population=1000&area=500.0&sort=desc&page=1&pageSize=10' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyIiwiaWF0IjoxNzAzNDk4MzU4LCJleHAiOjE3MDM1MTYzNTh9.lhbI9BkU866EHbiDSIJeq9kTnZ2PzXwSf_N3M3gHMdwxsTf1ypyuFDknyQ3WJjFfOeON4vD4ux62sq7EaV0Zzw'
```
