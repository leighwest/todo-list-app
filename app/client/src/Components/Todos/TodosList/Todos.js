import React, { useState, useEffect } from 'react';

import Card from '../../UI/Card'
import TodoItem from '../TodoItem/TodoItem';
import TodoDb from "../../../Util/TodoDb";

import classes from './Todos.module.css'

// let DUMMY_TODOS = [
//   {
//     id: 1,
//     description: "Practice coding",
//     date: (new Date().toString()),
//     completed: false,
//   },
//   {
//     id: 2,
//     description: "Wash car",
//     date: (new Date().toString()),
//     completed: false,
//   },
//   {
//     id: 3,
//     description: "Grocery shopping",
//     date: (new Date().toString()),
//     completed: true,
//   },
//   {
//     id: 4,
//     description: "Meditate",
//     date: (new Date().toString()),
//     completed: false,
//   }
// ];

const Todos = () => {

  const [todos, setTodos] = useState([]);

  useEffect(() => {
    TodoDb.getTodos().then(todos => {
      if (!todos.message) {
        setTodos(todos);
      }
    })
  }, [])

  // This will eventually be extended to submit a DELETE request to a REST API
  const deleteTodoHandler = todoId => {
    setTodos(prevTodos => {
      return prevTodos.filter(todo => todo.id !== todoId);
    });
  };

  // This will eventually be extended to submit a PUT request to a REST API
  const todoCompletedStatusHandler = todoId => {
    const todosCopy = todos.map(todo => {return {...todo}});
    todosCopy.map(todo =>
      todo.id === todoId ? todo.completed = !todo.completed : todo
    )
    setTodos(todosCopy);
  }

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

  return (
    <section className={classes.todos}>
      <Card>
        <ul>
          {todosList}
        </ul>
      </Card>
    </section>
  )
};

export default Todos;