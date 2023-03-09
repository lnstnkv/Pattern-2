import { useState } from "react";
import { Button, Collapse } from "react-bootstrap"
import { OperationsHistoryForm } from "./OperationsHistoryForm"

export function HistoryMenu() {
  const [open, setOpen] = useState(false);

  return (
      <div>
         <div className="ms-5 mt-3 rounded-5 p-2 pe-3 ps-3 text-center shadow-sm" style={{backgroundColor: 'lightgray', display: 'inline-block'}}>
            <h2>История операций счета</h2>
         </div> 
         <div className="ms-5 mt-3">   
          <Button onClick={() => setOpen(!open)}
          aria-controls="opsrationsHistoryForm"
          aria-expanded={open} 
          variant="dark" 
          className="m-2 ms-0">
              История операций конкретного счета
          </Button>
          <Collapse in={open}>
          <div id="operationsHistoryForm">
                  <OperationsHistoryForm/>
          </div>
          </Collapse>
        </div>
      </div>
  );
}
