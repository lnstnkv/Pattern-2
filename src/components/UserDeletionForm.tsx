import axios from 'axios';
import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';

export function UserDeletionForm() {

  async function deleteUser(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    e.preventDefault();

    var userDeleteData = new FormData();
    userDeleteData.append('id', (document.getElementById("userIdInput") as HTMLInputElement)?.value);
    axios({
      method: "delete",
      url: `http://localhost:8080/api/users/${userDeleteData.get("id")}`,
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
      <div id="formCol">
        <Form.Label className="m-2" htmlFor="userIdInput">ID пользователя:</Form.Label>
        <Form.Control style={{ width: '300px', display: 'block' }} className="m-2" id="userIdInput" type="text"/>
        <Button variant="danger" type="submit" className="m-2" onClick={(e) => deleteUser(e)}>
          Удалить
        </Button>
      </div>
  );
}
