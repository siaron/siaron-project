# docker-compose mongdb
```
mongo
  Dockerfile
  docker-compose.yml
  setup/
    setup.js
```
- Dockerfile
```
FROM mongo

COPY ./setup/setup.js /docker-entrypoint-initdb.d/
```
- docker-compose.yml
```
version: '3.1'
services:
  mongo:
    build: ./
    restart: always
    ports:
      - 27017:27017
    volumes:
      - ./setup:/docker-entrypoint-initdb.d/
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: DoNotPeek
  mongo-express:
    image: mongo-express
    restart: always
    ports:
      - 8080:8081
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: admin
      ME_CONFIG_MONGODB_ADMINPASSWORD: DoNotPeek
```
- setup/setup.js
```
db = db.getSiblingDB('newDB');
db.createUser(
    {
        user: "shon",
        pwd: "shonlovescoding",
        roles: [
            { role: "dbOwner", db: "newDB"}
        ]
    }
);
db.createCollection("newCollection");
```