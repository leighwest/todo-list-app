import React, { useState, memo } from 'react';
import Todos from './Components/Todos/TodosList/Todos';
import Footer from './Components/Footer/Footer';
import Card from './Components/UI/Card';
import TodoContext from './context/todo-count-context';

import classes from './App.module.css';

const App =  memo(() => {

  const [todos, setTodos] = useState([]);
  const todoValue = React.useMemo(() => ({todos, setTodos}), [todos, setTodos])
  
  return (
    <div className={classes.wrapper}>
      <Card>
        <TodoContext.Provider value={todoValue}>
          <Todos />
          <Footer />
        </TodoContext.Provider>
      </Card>
    </div >
  );
})

export default App;
