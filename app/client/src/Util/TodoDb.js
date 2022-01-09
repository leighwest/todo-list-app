const TodoDb = {
  getTodos() {
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Origin','http://localhost:3000');

    return fetch('http://localhost:8080/todos', {
      method: "GET",
      mode: 'cors',
      headers: headers

    })
      .then(response => {
        return response.json();
      })
  }
}

export default TodoDb