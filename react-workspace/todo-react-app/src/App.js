import logo from './logo.svg';
import './App.css';
import Todo from './Todo';
import { useEffect, useState } from 'react';
import { List, Paper } from '@mui/material';
import { Container } from '@mui/system';
import AddTodo from "./AddTodo";
import { call } from './service/ApiService';

function App() {
  const [items, setItems] = useState([]);

  //useEffect로 Todo API 호출 //fetch로 인한 무한 루프에 빠지지 않을 수 있음
  useEffect(() => {
    call("/todo", "GET", null)
    .then((response) => setItems(response.data));

    // const requestOptions = {
    //   method: "GET",
    //   headers: {"Content-Type": "application/json"},
    // };

    // fetch("http://localhost:8080/todo", requestOptions)
    //   .then((response) => response.json())
    //   .then(
    //     (response) => {
    //       setItems(response.data);
    //     },
    //     (error) => {}
    //   );
  },[]);

  // //API를 이용해 리스트 초기화
  // const requestOpions = {
  //   methods: "GET",
  //   headers: { "Content-Type": "application/json"},
  // }
  // fetch("http://localhost:8080/todo", requestOpions)
  //   .then((response) => response.json())
  //   .then(
  //     (response) => {
  //       setItems(response.data);
  //     },
  //     (error) => {

  //     }
  //   )

  const editItem = (item) => {
    call("/todo", "PUT", item)
    .then((response) => setItems(response.data));
    // setItems([...items]);
  }

  const deleteItem = (item) => {
    call("/todo", "DELETE", item)
    .then((response) => setItems(response.data));
    // //삭제할 아이템 찾기
    // const newItems = items.filter(e => e.id !== item.id);
    // //삭제할 아이템을 제외한 아이템을 다시 배열에 저장한다.
    // setItems([...newItems]);
  }

  const addItem = (item) => {
    call("/todo", "POST", item)
    .then((response) => setItems(response.data));
    // item.id = "ID-" + items.length; //key를 위한 id
    // item.done = false; //done 초기화
    // //업데이트는 반드시 setItems로 하고 새 배열을 만들어야 한다.
    // setItems([...items, item]);
    // console.log("items: ", items);
  }

  let todoItems = items.length > 0 && (
    <Paper style={{margin: 16}}>
      <List>
        {items.map((item) => (
          <Todo item={item} key={item.id} editItem={editItem} deleteItem={deleteItem} />
        ))}
      </List>
    </Paper>
  );

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo addItem={addItem}/>
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
