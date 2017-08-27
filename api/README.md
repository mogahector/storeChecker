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
