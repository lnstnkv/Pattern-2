import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import MainLayout from "./app/ui/pages/MainLayout/MainLayout";
import LoansView from "./app/ui/views/loans/LoansView";
import AccountsView from "./app/ui/views/accounts/AccountsView";

import "./App.css";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/*" element={<MainLayout />}>
          <Route path="loans" element={<LoansView />} />
          <Route path="accounts" element={<AccountsView />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default App;
