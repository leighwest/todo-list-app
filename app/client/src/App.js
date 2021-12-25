import Todos from './components/Todos/TodosList/Todos';

import { library } from '@fortawesome/fontawesome-svg-core'
import { faSquare, faCheckSquare, faTrashAlt} from '@fortawesome/free-regular-svg-icons'

import classes from './App.module.css';

library.add(faSquare, faCheckSquare, faTrashAlt)


const App = () => {
  return (
    <div className={classes.wrapper}>
      <Todos />
    </div>
  );
}

export default App;
