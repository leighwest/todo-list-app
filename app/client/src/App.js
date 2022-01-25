import Todos from './Components/Todos/TodosList/Todos';
import Footer from './Components/Footer/Footer';
import Card from './Components/UI/Card';
import TodoContext from './context/todo-count-context';

import classes from './App.module.css';

const App = () => {
  

  return (
    <div className={classes.wrapper}>
      <Card>
        <TodoContext.Provider value={0}>
          <Todos />
          <Footer />
        </TodoContext.Provider>

      </Card>
    </div >
  );
}

export default App;
