import { useState } from "react";
import { Button, Collapse } from "react-bootstrap";
import { RateCreationForm } from "./RateCreationForm";

export function RatesMenu() {
    const [open, setOpen] = useState(false);

    return (
        <div>
            <div className="ms-5 mt-3 rounded-5 p-2 pe-3 ps-3 text-center shadow-sm" style={{backgroundColor: 'lightgray', display: 'inline-block'}}>
                <h2>Тарифы для кредитов</h2>
            </div>
            <div className="ms-5 mt-3">   
                <Button onClick={() => setOpen(!open)}
                aria-controls="rateCreationForm"
                aria-expanded={open} 
                variant="dark" 
                className="m-2 ms-0">
                    Создать новый тариф
                </Button>
                <Collapse in={open}>
                <div id="rateCreationForm">
                        <RateCreationForm/>
                </div>
                </Collapse>
            </div>
        </div>
    );
  }
  