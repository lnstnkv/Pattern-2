import axios from 'axios';
import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';

export function RateCreationForm() {

  async function createCreditRate(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    e.preventDefault();

    var rateCreateData = new FormData();
    rateCreateData.append('name', (document.getElementById("rateNameInput") as HTMLInputElement)?.value);
    rateCreateData.append('percentage', (document.getElementById("rateValueInput") as HTMLInputElement)?.value);
    axios({
      method: "post",
      url: "http://localhost:8181/api/tariffs",
      data: rateCreateData,
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
      <div id="formLine">
        <Form.Label className="m-2" htmlFor="rateNameInput">Название:</Form.Label>
        <Form.Control style={{ width: '300px', display: 'inline-block' }} className="m-2" id="rateNameInput" type="text"/>
        <Form.Label className="m-2" htmlFor="rateValueInput">Процентная ставка:</Form.Label>
        <Form.Control style={{ width: '300px', display: 'inline-block' }} className="m-2" id="rateValueInput" type="text"/>
        <Button variant="success" type="submit" className="m-2" onClick={(e) => createCreditRate(e)}>
          Создать
        </Button>
      </div>
  );
}
