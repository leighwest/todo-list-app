# Todo List Manager (under construction)

This web application allows users to generate and manage todo items. The front-end is built with React and the back-end is built with Java using the Spring Boot framework.

## Local Development Setup

### Client (todo)

### Server

Clone the repository and cd into the `/docker` directory.

Build the Docker image:

```
docker build . -t <your username>/todo-list-server
```

Run the Docker image:

```
docker run -d --name todo-mysql -v ~/Desktop/tmp:/var/lib/mysql -p 3306:3306 <your username>/todo-list-server
```

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
	"completed":"false"
}

```

Send the POST request to: localhost:8080/todos

### Expected response

201 Created

```
{
	"id": 3,
	"description": "My first todo",
	"date": "20/04/2024",
	"completed": false
}

```
