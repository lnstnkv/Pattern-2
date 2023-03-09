import { Container, Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";

export function Navigation(){
    return (
        <Navbar bg="dark" variant="dark">
        <Container>
          <Navbar.Brand>Сотрудник банка</Navbar.Brand>
          <Nav className="me-auto">
            <Link to="/accounts" className="nav-link">Счета клиентов</Link>
            <Link to="/history" className="nav-link">История операций счета</Link>
            <Link to="/rates" className="nav-link">Тарифы для кредитов</Link>
            <Link to="/users" className="nav-link">Пользователи</Link>
          </Nav>
        </Container>
      </Navbar>
    )
}
