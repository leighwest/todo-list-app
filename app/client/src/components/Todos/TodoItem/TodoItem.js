

import classes from './TodoItem.module.css'

const TodoItem = props => {

  return (
    <li className={classes.todo}>
      <h3 className={classes.description}>{props.description}</h3>
    </li>
  );
};

export default TodoItem;