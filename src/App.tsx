import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import MainLayout from "./ui/layouts/Main";
import LoansView from "./ui/views/loans/LoansView";
import AccountsView from "./ui/views/accounts/AccountsView";
import AccountView from "ui/views/accounts/AccountView";

import "./App.css";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/*" element={<MainLayout />}>
          <Route path="loans" element={<LoansView />} />
          <Route path="accounts" element={<AccountsView />} />
          <Route path="accounts/:id" element={<AccountView />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default App;
