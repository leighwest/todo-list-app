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
      <div className={classes.draftTodo}>
        <input type="text" name="name"  autoFocus placeholder='Enter todo' />
        {/* <input type="submit" value="Submit" /> */}
        <FontAwesomeIcon icon={['fa', 'check-circle']} onClick={() => console.log("clicked!")} className={classes.tickIcon} />
      </div>

    </form>
  );
};

export default DraftTodoItem;