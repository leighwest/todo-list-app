import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import TodoDb from '../../../Util/TodoDb';
import classes from './DraftTodoItem.module.css';

const DraftTodoItem = props => {

  const [todoDescription, setTodoDescription] = useState('');

  const onChangeHandler = event => {
    setTodoDescription(event.target.value)
  }

  const deleteHandler = () => {
    props.deleteDraftTodo();
  }

  const handleSubmit = event => {
    event.preventDefault();
    // if validate(todoDescription)
    const todo = {
      description: todoDescription,
      date: new Date(),
      completed: false
    }
    TodoDb.createTodo(todo);
    props.addTodo(todo);
    props.deleteDraftTodo();
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className={classes.draftTodo}>
        <input type="text" name="name" onChange={onChangeHandler} value={todoDescription} autoFocus autoComplete="off" placeholder='Enter todo' />
        {/* <input type="submit" value="Submit" /> */}
        <FontAwesomeIcon icon={['fa', 'check-circle']} onClick={handleSubmit} className={classes.tickIcon} />
        <FontAwesomeIcon icon={['fa', 'times-circle']} onClick={deleteHandler} className={classes.crossIcon} />
      </div>
    </form>
  );
};

export default DraftTodoItem;