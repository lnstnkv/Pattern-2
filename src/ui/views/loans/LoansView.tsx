import { message } from "antd";
import React from "react";
import {} from "api/loans/LoansApi";

const LoansView: React.FC = () => {
  const [messageApi, contextHolder] = message.useMessage();

  return <>{contextHolder}</>;
};

export default LoansView;
