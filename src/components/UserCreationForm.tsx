import axios from 'axios';
import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';

export function UserCreationForm() {

  // var success = document.createElement("p");
  // success.textContent = "Пользователь успешно создан!"

  // var error = document.createElement("p");
  // success.textContent = "Возникла ошибка!"

  async function createUser(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    e.preventDefault();

    var userCreateData = new FormData();
    userCreateData.append('firstName', (document.getElementById("userFirstNameInput") as HTMLInputElement)?.value);
    userCreateData.append('lastName', (document.getElementById("userLastNameInput") as HTMLInputElement)?.value);
    userCreateData.append('username', (document.getElementById("usernameInput") as HTMLInputElement)?.value);
    userCreateData.append('password', (document.getElementById("userPasswordInput") as HTMLInputElement)?.value);
    userCreateData.append('role', (document.getElementById("userRoleInput") as HTMLInputElement)?.value);
    userCreateData.append('status', (document.getElementById("userStatusInput") as HTMLInputElement)?.value);
    axios({
      method: "post",
      url: "http://localhost:8080/api/users",
      data: userCreateData,
      headers: { 'Content-Type': 'application/json; charset=utf-8' },
    })
    .then(function (response) {
      // var form = document.getElementById("formCol");
      // form?.appendChild(success);
      // console.log(response);
    })
    .catch(function (response) {
      // var form = document.getElementById("formCol");
      // form?.appendChild(error);
      // console.log(response);
    });
}

  

  return (
      <Form id="formCol">
        <div>
        <Form.Label className="m-2 d-inline-block" htmlFor="userFirstNameInput">Имя:</Form.Label>
        <Form.Control required style={{ width: '300px', display: 'inline-block' }} className="m-2" id="userFirstNameInput" type="text"/>
        </div>
        <div >
        <Form.Label className="m-2 d-inline-block" htmlFor="userLastNameInput">Фамилия:</Form.Label>
        <Form.Control required style={{ width: '300px', display: 'inline-block' }} className="m-2" id="userLastNameInput" type="text"/>
        </div>
        <div>
        <Form.Label className="m-2 d-inline-block" htmlFor="usernameInput">Имя пользователя:</Form.Label>
        <Form.Control required style={{ width: '300px', display: 'inline-block' }} className="m-2" id="usernameInput" type="text"/>
        </div>
        <div>
        <Form.Label className="m-2 d-inline-block" htmlFor="userPasswordInput">Пароль:</Form.Label>
        <Form.Control required style={{ width: '300px', display: 'inline-block' }} className="m-2" id="userPasswordInput" type="password"/>
        </div>
        <div>
        <Form.Label className="m-2 d-inline-block" htmlFor="userRoleInput">Роль:</Form.Label>
        <Form.Select style={{ width: '300px', display: 'inline-block' }} className="m-2" id="userRoleInput" aria-label="Default select example">
          <option value="CLIENT">Клиент</option>
          <option value="EMPLOYEE">Сотрудник</option>
        </Form.Select>
        </div>
        <div>
        <Form.Label className="m-2 d-inline-block" htmlFor="userStatusInput">Статус:</Form.Label>
        <Form.Select style={{ width: '300px', display: 'inline-block' }} className="m-2" id="userStatusInput" aria-label="Default select example">
          <option value="ACTIVE">Активный</option>
          <option value="BANNED">Заблокированный</option>
        </Form.Select>
        </div>
        <Button variant="success" type="submit" className="m-2 mb-4 d-block" onClick={(e) => createUser(e)}>
          Создать
        </Button>
      </Form>
  );
}
