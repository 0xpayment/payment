## Run Application Locally
1. docker-compose up -d
2. ./gradlew bootRun
3. go to localhost:8082 for phpadmin to view DB, user name root, password root, leave host as empty
4. run the following sql queries to init the auth-related tables:
    ```sql
    INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('0xPay-Web', '{bcrypt}$2a$10$gPhlXZfms0EpNHX0.HHptOhoFD1AoxSr/yUIdTqA8vtjeP4zi0DDu', 'http://localhost:8080/code', 'READ,WRITE', '3600', '10000', 'payment', 'authorization_code,password,refresh_token,implicit', '{}');
    ```

## Auth APIs

### Signup
```
curl --location --request POST 'localhost:9090/oauth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"username@gmail.com",
    "password": "password123",
    "merchantName": "test merchant",
    "merchantWalletAddress": "0x90CF09A1155Fe543013550edC96A14F7FF404293"
}'
```

### Login
```
curl --location --request POST 'http://localhost:9090/oauth/token' \
--header 'Authorization: Basic MHhQYXktV2ViOnBpbg==' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=username@gmail.com' \
--data-urlencode 'password=password123'
```

### Logout
```
curl --location --request POST 'localhost:9090/oauth/logout' \
--header 'Authorization: Bearer 3996ea77-2c82-494d-b5f1-2dec44ca30d1'
```

## Link APIs

### Create Link
```
curl --location --request POST 'localhost:9090/links' \
--header 'Authorization: Bearer 0133fcf1-2b99-47ac-b5ee-963cddc08b0d' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "test des 2",
    "name": "name",
    "amount": "0.001"
}'
```

### Get Link by Id
```
curl --location --request GET 'localhost:9090/links/2146248707463446528' \
--header 'Authorization: Bearer acf5c534-5e22-4ec2-adeb-32df182aa310'
```

### Get Links
```
curl --location --request GET 'localhost:9090/links' \
--header 'Authorization: Bearer acf5c534-5e22-4ec2-adeb-32df182aa310'
```

### Delete Link by Id
```
curl --location --request DELETE 'localhost:9090/links/2082509709163626496' \
--header 'Authorization: Bearer 6e50208d-c111-4a9c-999e-f084eb316a9a'
```

### Test Get Links
```
curl --location --request GET 'localhost:9090/links/getTestLinks' \
--header 'Authorization: Bearer 6e50208d-c111-4a9c-999e-f084eb316a9a'
```

### Get Links by linkId
```
curl --location --request GET 'localhost:9090/links/2124047471754608640/orders' \
--header 'Authorization: Bearer 0847c907-0683-4461-9b0f-5f5f437cb5cd'
```

### Get Success Orders by LinkId
```
curl --location --request GET 'localhost:9090/links/2146248707463446528/orders' \
--header 'Authorization: Bearer acf5c534-5e22-4ec2-adeb-32df182aa310'
```

### Get Merchant Orders by status
```
curl --location --request GET 'localhost:9090/links/orders?status=COMPLETED' \
--header 'Authorization: Bearer 0133fcf1-2b99-47ac-b5ee-963cddc08b0d'
```

## Order APIs

### Create Order For Link
```
curl --location --request GET 'localhost:9090/link/2149243966116069376'
```


## Merchnat APIs
### Get merchant info
```
curl --location --request GET 'localhost:9090/merchant' \
--header 'Authorization: Bearer 89624b92-c1ce-4b01-9505-79bea25ba816'
```