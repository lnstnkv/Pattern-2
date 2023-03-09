import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';

export function UserAccountsForm() {
  return (
      <div id="formLine">
        <Form.Label className="m-2" htmlFor="userIdInput">ID клиента:</Form.Label>
        <Form.Control style={{ width: '300px', display: 'inline-block' }} className="m-2" id="userIdInput" type="text"/>
        <Button variant="success" type="submit" className="m-2">
          Поиск
        </Button>
      </div>
  );
}
