import Todos from './components/Todos/Todos';

import classes from './App.module.css';


function App() {
  return (
    <div className={classes.wrapper}>
      <Todos />
    </div>
  );
}

export default App;
