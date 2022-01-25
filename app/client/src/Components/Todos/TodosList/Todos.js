import React, { useState, useEffect, useContext } from 'react';

import TodoItem from '../TodoItem/TodoItem';
import TodoDb from "../../../Util/TodoDb";
import TodoContext from '../../../context/todo-count-context';

import classes from './Todos.module.css'

const Todos = () => {

  const [todos, setTodos] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  let ctx = useContext(TodoContext);

  useEffect(() => {
    TodoDb.getTodos().then(todos => {
      if (!todos.message) {
        setTodos(todos);
      } else {
        console.error(todos.message);
        setError("Unable to fetch your todos. Please contact the development team.");
      }
      setIsLoading(false);
    })
  }, [])

  const deleteTodoHandler = todoId => {
    setTodos(prevTodos => {
      return prevTodos.filter(todo => todo.id !== todoId);
    });

    TodoDb.deleteTodo(todoId);
  };

  // This will eventually be extended to submit a PUT request to a REST API
  const todoCompletedStatusHandler = todoId => {
    const todosCopy = todos.map(todo => {return {...todo}});
    todosCopy.map(todo =>
      todo.id === todoId ? todo.completed = !todo.completed : todo
    )
    setTodos(todosCopy);
  }

  ctx = todos.length;
  console.log(`ctx in Todo.js is equal to: ${ctx}`);

  const todosList = todos.map(todo =>
    <TodoItem
      id={todo.id}
      key={todo.id}
      description={todo.description}
      date={todo.date}
      completed={todo.completed}
      onDeleteTodo={deleteTodoHandler}
      onUpdateTodoStatus={todoCompletedStatusHandler}
    />
  );

  let content = <p>Nothing to do. Time to relax!</p>

  if (todos.length > 0) {
    content = todosList;
  }

  if (error) {
    content = <p>{error}</p>
  }

  if (isLoading) {
    content = <p>Loading...</p>
  }

  return (
    <section className={classes.todos}>
      {/* <Card> */}
        <ul>
          {content}
        </ul>
      {/* </Card> */}
    </section>
  )
};

export default Todos;