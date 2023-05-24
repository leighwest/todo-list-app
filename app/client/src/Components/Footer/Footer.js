import React, { useContext, memo } from 'react';

import Button from '../UI/Button';
import TodoContext from 'context/todo-count-context';

import classes from './Footer.module.css';

// const Footer = memo(props => {
const Footer = (props) => {
  let ctx = useContext(TodoContext);

  let tasksToDo = '';

  if (ctx.todos.length === 1) {
    tasksToDo = `${ctx.todos.length} TASK`;
  }

  if (ctx.todos.length > 1) {
    tasksToDo = `${ctx.todos.length} TASKS`;
  }

  return (
    <div className={classes.wrapper}>
      <p className={classes.todosCount}>{tasksToDo}</p>
      <Button
        class={'add-new-task-btn'}
        onClick={props.addDraftTodo}
      />
    </div>
  );
  // });
};

export default Footer;
