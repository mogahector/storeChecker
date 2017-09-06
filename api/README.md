
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

# Run Debug
./gradlew clean bootRun --debug-jvm
```

# Load Data
```
mysql -h localhost -P 3306 --protocol=tcp -u admin -p < load_data.sql
```

# Example calls
```
ENDPOINT=http://localhost:8080

AUTH_USER=admin
AUTH_PASSWORD=password

AUTH_USER=$USERNAME
AUTH_PASSWORD=$PASSWORD


Example Curl calls:

USERNAME=user$RANDOM
NAME=name
LAST_NAME=last
PASSWORD=P@ssword1
EMAIL=name.last@mail.com
ROLE_NAME=OWNER

# Create user
curl -X POST -u $AUTH_USER:$AUTH_PASSWORD $ENDPOINT/users -d '{ "username": "'$USERNAME'", "name": "'$NAME'", "lastName": "'$LAST_NAME'", "password": "'$PASSWORD'", "email": "'$EMAIL'", "roles":[{"role": "'$ROLE_NAME'"}]}' -H "Content-Type: application/json" -k -v

USER_ID=2

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

# Create store
curl -X POST -u admin:password http://localhost:8080/stores -d '{ "name": "storeName", "address": "store address"}' -H "Content-Type: application/json" -k -v
```

# API Documentation

## Resources
***GET|PUT|POST*** /stores

***GET|PUT|POST|DELETE*** /stores/{id}

***GET|POST*** /stores/{id}/checklists

***GET|PUT|DELETE*** /stores/{id}/checklists/{id}

***GET|POST*** /stores/{id}/checklists/{id}/images

***GET|PUT|POST*** /users

***GET|PUT|POST|DELETE*** /users/{id}

