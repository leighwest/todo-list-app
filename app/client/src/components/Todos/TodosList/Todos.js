import Card from '../../UI/Card'
import TodoItem from '../TodoItem/TodoItem';

import classes from './Todos.module.css'

const DUMMY_TODOS = [
  {
    id: 1,
    description: "Practice coding",
    date: (new Date().toString()),
    completed: false,
  },
  {
    id: 2,
    description: "Wash car",
    date: (new Date().toString()),
    completed: false,
  },
  {
    id: 3,
    description: "Grocery shopping",
    date: (new Date().toString()),
    completed: true,
  },
  {
    id: 4,
    description: "Meditate",
    date: (new Date().toString()),
    completed: false,
  }
];

const Todos = () => {
  const todosList = DUMMY_TODOS.map(todo => 
    <TodoItem
      id={todo.id}
      key={todo.id}
      description={todo.description}
      date={todo.date}
      completed={todo.completed}
    />  
  );

  return (
    <section className={classes.todos}>
      <Card>
        <ul>
          {todosList}
        </ul>
      </Card>
    </section>
  )
};

export default Todos;