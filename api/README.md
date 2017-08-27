
API Readme

# Run docker-compose to start mysql
```
docker-compose up -d
```

# Build/Run project
```
# Compile
./gradlew clean build

# Run
./gradlew bootRun
```

# Example calls
```
ENDPOINT=http://localhost:8080

AUTH_USER=admin
AUTH_PASSWORD=password

Example Curl calls:

NAME=John
LAST_NAME=Smith

# Create user
curl -X POST -u $AUTH_USER:$AUTH_PASSWORD $ENDPOINT/users -d '{ "name": "'$NAME'", "lastName": "'$LAST_NAME'"}' -H "Content-Type: application/json" -k -v

USER_ID=1

# Get user
curl -X GET -u $AUTH_USER:$AUTH_PASSWORD $ENDPOINT/users/$USER_ID -H "Accept: application/json" -k -v

NAME=John
LAST_NAME=Smith2

# Update user
curl -X PUT -u $AUTH_USER:$AUTH_PASSWORD $ENDPOINT/users/$USER_ID -d '{ "name": "'$NAME'", "lastName": "'$LAST_NAME'"}' -H "Content-Type: application/json" -H "Accept: application/json" -k -v

# Delete user
curl -X DELETE -u $AUTH_USER:$AUTH_PASSWORD $ENDPOINT/users/$USER_ID -k -v

# Get users
curl -X GET -u $AUTH_USER:$AUTH_PASSWORD $ENDPOINT/users -H "Accept: application/json" -k -v
```

# API Documentation

## Resources
***GET|PUT|POST*** /stores

***GET|PUT|POST|DELETE*** /stores/{id}

***GET|POST*** /stores/{id}/checklists

***GET|PUT|POST*** /stores/{id}/checklists/{id}

***GET|POST*** /stores/{id}/checklists/{id}/items

***GET|PUT|POST*** /stores/{id}/checklists/{id}/item/{id}

***GET|PUT|POST|DELETE*** /stores/{id}/checklists/{id}/item/{id}/images
          
         
***GET|PUT|POST*** /users

***GET|PUT|POST|DELETE*** /users/{id}

