import React, { useContext, memo } from 'react';


import Button from "../UI/Button";
import TodoContext from "../../context/todo-count-context";

import classes from './Footer.module.css'


const Footer = memo(() => {

  let ctx = useContext(TodoContext);

  return (
    <div className={classes.wrapper}>
      <p className={classes.todosCount}>{ctx.todos.length} TASKS</p>
      <Button type={"add-new-task-btn"}/>
    </div>
  );
});

export default Footer;