const TodoDb = {
  async getTodos() {
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Origin','http://localhost:3000');

    const response = await fetch('http://localhost:8080/todos', {
      method: "GET",
      mode: 'cors',
      headers: headers
    })

    const data = await response.json();

    return data;
  }
}

export default TodoDb