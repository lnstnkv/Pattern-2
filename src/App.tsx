import { Route, Routes } from "react-router";
import { AccountsMenu } from "./components/AccountsMenu";
import { HistoryMenu } from "./components/HistoryMenu";
import { Navigation } from "./components/Navigation";
import { RatesMenu } from "./components/RatesMenu";
import { UsersMenu } from "./components/UserMenu";

function App() {
  return (
    <>
      <Navigation></Navigation>
      <Routes>
        <Route path="/accounts" element={<AccountsMenu/>} />
        <Route path="/history" element={<HistoryMenu/>} />
        <Route path="/rates" element={<RatesMenu/>} />
        <Route path="/users" element={<UsersMenu/>} />
      </Routes>
    </>
  );
}

export default App;
