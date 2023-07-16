# Todo List Manager (under construction)

This web application allows users to generate and manage todo items. The front-end is built with React and the back-end is built with Java using the Spring Boot framework.

## Local Development Setup

### Docker Compose

Clone the repository and cd into the `/app` directory.

Build and run the client and database:

```
docker-compose up
```

Open

### Alternatively, starting the client and/or database independently:

### Client

cd into the `/app/client` directory.

Build the Docker image:

```
docker build . -t leighwest/todo-list-client
```

Run the Docker image:

```
docker run --name todo-client -p 3000:3000 -it leighwest/todo-list-client
```

### Server

cd into the `/app/server` directory.

Build the database Docker image:

```
docker build . -t leighwest/todo-list-db
```

Run the database Docker image:

```
docker run -d --name todo-mysql -v ~/Desktop/tmp:/var/lib/mysql -p 3306:3306 leighwest/todo-list-db
```

### Start the Spring Boot server application

Import the `/app/server` directory into IntelliJ

Run:

```
mvn install
```

Run `TodoApiApplication`

## Using the API

Using your favourite API client (i.e. Postman, Insomnia) create a POST request with the following headers:

```
Content-type application/json
Accept application/json
```

In the body of your JSON request, submit the following data:

```
{
	"description": "My first todo",
	"date": "20/04/2024",
}

```

Send the POST request to: localhost:8083/todos

### Expected response

201 Created

```
{
	"uuid": "3dcf6712-0c47-439b-a47d-c8fa0bddaa8d",
	"description": "My first todo",
	"date": "20/04/2024",
	"completed": false
}

```

## Using the API with Swagger

The API is viewable locally using swagger at `http://localhost:8083/swagger-ui/index.html`
