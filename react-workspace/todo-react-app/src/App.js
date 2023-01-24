import logo from './logo.svg';
import './App.css';
import Todo from './Todo';
import { useState } from 'react';
import { List, Paper } from '@mui/material';
import { Container } from '@mui/system';
import AddTodo from "./AddTodo";

function App() {
  const [items, setItems] = useState([
    {
      id: "0",
      title: "Hello World 1",
      done: true
    },
    {
      id: "1",
      title: "Hello World 2",
      done: false
    }
  ]);

  let todoItems = items.length > 0 && (
    <Paper style={{margin: 16}}>
      <List>
        {items.map((item) => (
          <Todo item={item} key={item.id}/>
        ))}
      </List>
    </Paper>
  );

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo />
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
