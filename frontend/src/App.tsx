import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { AppLayout } from "./components/AppLayout";
import {
  BudgetsPage,
  OverviewPage,
  ReportsPage,
  TransactionsPage,
} from "./pages/FinancePages";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<AppLayout />}>
          <Route index element={<OverviewPage />} />
          <Route path="transakcije" element={<TransactionsPage />} />
          <Route path="budzeti" element={<BudgetsPage />} />
          <Route path="izvjestaji" element={<ReportsPage />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
