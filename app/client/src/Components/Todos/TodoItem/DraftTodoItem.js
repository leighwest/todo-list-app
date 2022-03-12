import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import classes from './DraftTodoItem.module.css';

const DraftTodoItem = props => {

  const deleteHandler = () => {
    // props.onDeleteTodo(props.id);
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" name="name" className={classes.draftTodo}autoFocus placeholder='Enter todo'/>
      {/* <input type="submit" value="Submit" /> */}
      
    </form>
  );
};

export default DraftTodoItem;