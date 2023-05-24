import React, { useState, useEffect, useContext } from 'react';

import TodoItem from '../TodoItem/TodoItem';
import DraftTodoItem from '../TodoItem/DraftTodoItem';
import TodoDb from 'Util/TodoDb';

import TodoContext from 'context/todo-count-context';

import classes from './Todos.module.css';

const Todos = (props) => {
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const { todos, setTodos } = useContext(TodoContext);

  useEffect(() => {
    TodoDb.getTodos().then((todos) => {
      if (!todos.message) {
        setTodos(todos);
      } else {
        console.error(todos.message);
        setError(
          'Unable to fetch your todos. Please contact the development team.',
        );
      }
      setIsLoading(false);
    });
  }, [setTodos]);

  const addTodoHandler = (todo) => {
    console.log(`in addTodoHandler todoID is: ${todo.id}`);
    let _todos = [...todos];
    _todos.push(todo);
    setTodos(_todos);
  };

  const deleteTodoHandler = (todoId) => {
    console.log(`deleteTodoHandler called with todoId: ${todoId}`);
    setTodos((prevTodos) => {
      return prevTodos.filter((todo) => todo.id !== todoId);
    });

    TodoDb.deleteTodo(todoId);
  };

  // This will eventually be extended to submit a PUT request to a REST API
  const todoCompletedStatusHandler = (todoId) => {
    const todosCopy = todos.map((todo) => {
      return { ...todo };
    });
    todosCopy.map((todo) =>
      todo.id === todoId ? (todo.completed = !todo.completed) : todo,
    );
    setTodos(todosCopy);
  };

  const todosList = todos.map((todo) => (
    <TodoItem
      id={todo.id}
      key={todo.id}
      description={todo.description}
      date={todo.date}
      completed={todo.completed}
      onDeleteTodo={deleteTodoHandler}
      onUpdateTodoStatus={todoCompletedStatusHandler}
    />
  ));

  let content = <p>Nothing to do. Time to relax!</p>;

  if (todos.length > 0) {
    content = todosList;
  }

  if (error) {
    content = <p>{error}</p>;
  }

  if (isLoading) {
    content = <p>Loading...</p>;
  }

  return (
    <section className={classes.todos}>
      <ul>
        {content}
        {props.draftTodo && (
          <DraftTodoItem
            deleteDraftTodo={props.deleteDraftTodo}
            addTodo={addTodoHandler}
          />
        )}
      </ul>
    </section>
  );
};

export default Todos;
