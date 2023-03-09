import { useEffect, useState } from "react";
import { Button, Collapse, Table } from "react-bootstrap";
import { UserAccountsForm } from "./UserAccountsForm";
import '../styles/Accounts.css';
import axios from "axios";
import { IAccount, IAccounts } from "../models";
import fakeAccounts from '../accountsDebugJSON.json';
import React from "react";
import { useTable } from "react-table";

export function AccountsMenu(){
    const [open, setOpen] = useState(false)
    const [showAccounts, setShowAccounts] = useState(false)
    const [accounts, setAccounts] = useState<IAccounts>()
    const [newFakeAccounts, setNewFakeAccounts] = useState<IAccount>(JSON.parse(JSON.stringify(fakeAccounts),replacer))

    async function getAllAccounts() {
        const response = await axios.get<IAccounts>('http://localhost:8000/accounts')
        setAccounts(response.data)
    }

    async function showAllAccounts() {
        setShowAccounts(true)
        // getAllAccounts()
    }
    
    function replacer(key: any, value: any) {
        if (typeof value === "boolean"||typeof value === "number") {
            if(String(value) == "false")
            return "Нет"
            if(String(value) == "true")
            return "Да"
        }
        return value;
    }

    console.log(newFakeAccounts);

    const data = React.useMemo(() => newFakeAccounts, []);
    const columns = React.useMemo(
    () => [
      {
        Header: "ID счета",
        accessor: "id",
      },
      {
        Header: "ID владельца счета",
        accessor: "ownerId",
      },
      {
        Header: "Валюта",
        accessor: "currency",
      },
      {
        Header: "Баланс",
        accessor: "balance",
      },
      {
        Header: "Заблокирован",
        accessor: "isBlocked",
      },
      {
        Header: "Удален",
        accessor: "isDeleted",
      },
    ],
    []
  );

    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = useTable({ columns, data });


    useEffect(()=>{
        console.log('ready');
    },[])

    return (
        <>
        <div className="ms-5 mt-3 rounded-5 p-2 pe-3 ps-3 text-center shadow-sm" style={{backgroundColor: 'lightgray', display: 'inline-block'}}>
            <h2>Счета клиентов</h2>
        </div>
        <div className="ms-5 mt-3">
            <Button variant="dark" 
            className="m-2 me-4 ms-0"
            onClick={()=>showAllAccounts()}>
                Все счета клиентов
            </Button>
            <Button onClick={() => setOpen(!open)}
            aria-controls="userAccountsForm"
            aria-expanded={open} 
            variant="dark" 
            className="m-2 ms-0">
                Счета конкретного клиента
            </Button>
            <Collapse in={open}>
            <div id="userAccountsForm">
                <UserAccountsForm/>
            </div>
            </Collapse>
            {showAccounts && <div>
                <Table {...getTableProps()}>
                    <thead>
                        {headerGroups.map((headerGroup) => (
                        <tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map((column) => (
                            <th {...column.getHeaderProps()}>
                                {column.render("Header")}
                            </th>
                            ))}
                        </tr>
                        ))}
                    </thead>
                    <tbody {...getTableBodyProps()}>
                        {rows.map((row) => {
                        prepareRow(row);
                        return (
                            <tr {...row.getRowProps()}>
                            {row.cells.map((cell) => (
                                <td {...cell.getCellProps()}> {cell.render("Cell")} </td>
                            ))}
                            </tr>
                        );
                        })}
                    </tbody>
                </Table>
            </div>}
        </div>
       </>
    )
}




