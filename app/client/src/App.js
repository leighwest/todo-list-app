import React, { useState, memo } from 'react';
import Todos from './Components/Todos/TodosList/Todos';
import Footer from './Components/Footer/Footer';
import Card from './Components/UI/Card';
import TodoContext from './context/todo-count-context';

import classes from './App.module.css';

const App = memo(() => {
  const [todos, setTodos] = useState([]);
  const [draftTodo, setDraftTodo] = useState(false);
  const todoValue = React.useMemo(
    () => ({ todos, setTodos }),
    [todos, setTodos],
  );

  const addDraftTodo = () => {
    setDraftTodo(true);
  };

  const deleteDraftTodo = () => {
    setDraftTodo(false);
  };

  return (
    <div className={classes.wrapper}>
      <Card>
        <TodoContext.Provider value={todoValue}>
          <Todos
            draftTodo={draftTodo}
            deleteDraftTodo={deleteDraftTodo}
          />
          <Footer addDraftTodo={addDraftTodo} />
        </TodoContext.Provider>
      </Card>
    </div>
  );
});

export default App;
