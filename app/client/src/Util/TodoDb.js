const TodoDb = {
  async getTodos() {
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Origin','http://localhost:3000');

    try {
      const response = await fetch('http://localhost:8080/todos', {
      method: "GET",
      mode: 'cors',
      headers: headers
    });

    const data = await response.json();

    return data;
    } catch (error) {
      return error;
    }   
  },

  async createTodo(todo) {
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Origin','http://localhost:3000');

    try {
      const response = await fetch('http://localhost:8080/todos', {
      method: "POST",
      mode: 'cors',
      headers: headers,
      body: JSON.stringify(todo)
    });

    const data = await response.json();

    return data;
    } catch (error) {
      return error;
    }   
  },


  async deleteTodo(id) {
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Origin','http://localhost:3000');

    try {
      const response = await fetch(`http://localhost:8080/todos/${id}`, {
      method: "DELETE",
      mode: 'cors',
      headers: headers
    });

    const data = await response.json();
    console.log(`the data is: ${data}`)

    return data;
    } catch (error) {
      return error;
    }
  }
}

export default TodoDb;