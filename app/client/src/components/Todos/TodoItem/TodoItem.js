import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import classes from './TodoItem.module.css'

const TodoItem = props => {

  return (
    <li className={classes.todo}>
      <FontAwesomeIcon icon={['far', 'square']} />
      <p className={classes.description}>{props.description}</p>
    </li>
  );
};

export default TodoItem;