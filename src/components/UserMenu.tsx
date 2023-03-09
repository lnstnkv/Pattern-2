import { useState } from "react";
import { Button, Collapse } from "react-bootstrap";
import { UserCreationForm } from "./UserCreationForm";
import { UserDeletionForm } from "./UserDeletionForm";
import '../styles/Users.css';

export function UsersMenu() {
    const [openCreate, setOpenCreate] = useState(false);
    const [openDelete, setOpenDelete] = useState(false);
    const [openCreateCustomer, setOpenCreateCustomer] = useState(false);
    const [openCreateEmployee, setOpenCreateEmployee] = useState(false);
    const [openDeleteCustomer, setOpenDeleteCustomer] = useState(false);
    const [openDeleteEmployee, setOpenDeleteEmployee] = useState(false);
    

    return (
        <div>
            <div className="ms-5 mt-3 rounded-5 p-2 pe-3 ps-3 text-center shadow-sm" style={{backgroundColor: 'lightgray', display:'inline-block'}}>
               <h2>Пользователи</h2>
            </div>     
            <div className="ms-5 mt-3">   
                <Button onClick={() => setOpenCreate(!openCreate)}
                aria-controls="create"
                aria-expanded={openCreate} 
                variant="dark" 
                className="m-2 ms-0">
                    Создать 
                </Button>
                <Button onClick={() => setOpenDelete(!openDelete)}
                aria-controls="delete"
                aria-expanded={openDelete} 
                variant="dark" 
                className="m-2 ms-0">
                    Удалить
                </Button>
                <Collapse in={openCreate}>
                    <div id="create">
                        <Button onClick={() => setOpenCreateCustomer(!openCreateCustomer)}
                        aria-controls="createCustomer"
                        aria-expanded={openCreateCustomer} 
                        variant="dark" 
                        className="m-2 mb-0 ms-0 d-block">
                            Создать пользователя 
                        </Button>
                        <Collapse in={openCreateCustomer}>
                            <div id="createCustomer">
                                <UserCreationForm/>
                            </div>
                        </Collapse>
                        {/* <Button onClick={() => setOpenCreateEmployee(!openCreateEmployee)}
                        aria-controls="createEmployee"
                        aria-expanded={openCreateEmployee} 
                        variant="dark" 
                        className="m-2 ms-0 d-block">
                            Создать сотрудника
                        </Button>
                        <Collapse in={openCreateEmployee}>
                            <div id="createEmployee" className="mt-2">
                                Создать сотрудника
                            </div>
                        </Collapse> */}
                    </div>
                </Collapse>
                <Collapse in={openDelete}>
                    <div id="delete" >
                        <Button onClick={() => setOpenDeleteCustomer(!openDeleteCustomer)}
                        aria-controls="deleteCustomer"
                        aria-expanded={openDeleteCustomer} 
                        variant="dark" 
                        className="m-2 mb-0 ms-0 d-block">
                            Удалить пользователя
                        </Button>
                        <Collapse in={openDeleteCustomer}>
                            <div id="deleteCustomer" >
                                <UserDeletionForm/>
                            </div>
                        </Collapse>
                        {/* <Button onClick={() => setOpenDeleteEmployee(!openDeleteEmployee)}
                        aria-controls="deleteEmployee"
                        aria-expanded={openDeleteEmployee} 
                        variant="dark" 
                        className="m-2 ms-0 d-block">
                            Удалить сотрудника
                        </Button>
                        <Collapse in={openDeleteEmployee}>
                            <div id="deleteEmployee" className="mt-2">
                                Удалить сотрудника
                            </div>
                        </Collapse> */}
                    </div>
                </Collapse>
            </div>
        </div>
    );
  }
  