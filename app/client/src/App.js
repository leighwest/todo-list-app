import Todos from './Components/Todos/TodosList/Todos';
import Footer from './Components/Footer/Footer';
import Card from './Components/UI/Card';

import classes from './App.module.css';

const App = () => {
  return (
    <div className={classes.wrapper}>
      <Card>
        <Todos />
        <Footer />
      </Card>
    </div>
  );
}

export default App;
