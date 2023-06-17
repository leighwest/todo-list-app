import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import classes from './TodoItem.module.css';

const TodoItem = (props) => {
  const deleteHandler = () => {
    props.onDeleteTodo(props.id);
  };

  const completeHandler = () => {
    props.onUpdateTodoStatus(props.id);
  };

  return (
    <li
      className={
        props.completed
          ? [classes.todo, classes.completed].join(' ')
          : classes.todo
      }>
      {!props.completed && (
        <FontAwesomeIcon
          icon={['far', 'square']}
          onClick={completeHandler}
        />
      )}
      {props.completed && (
        <FontAwesomeIcon
          icon={['far', 'check-square']}
          onClick={completeHandler}
        />
      )}
      <p>{props.description}</p>
      <FontAwesomeIcon
        icon={['far', 'trash-alt']}
        onClick={deleteHandler}
        className={classes.deleteIcon}
      />
    </li>
  );
};

export default TodoItem;
