

import classes from './TodoItem.module.css'

const TodoItem = props => {

  return (
    <li className={classes.todo}>
      <span className={classes.checkbox}/>
      <p className={classes.description}>{props.description}</p>
    </li>
  );
};

export default TodoItem;