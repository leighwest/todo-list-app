import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import classes from './TodoItem.module.css'

const TodoItem = props => {

  const deleteHandler = () => {
    props.onDeleteTodo(props.id);
  }

  return (
    <li className={classes.todo} >
      <FontAwesomeIcon icon={['far', 'square']} />
      <p className={classes.description}>{props.description}</p>
      <FontAwesomeIcon icon={['far', 'trash-alt']} onClick={deleteHandler} className={classes.deleteIcon}/>

    </li>
  );
};

export default TodoItem;