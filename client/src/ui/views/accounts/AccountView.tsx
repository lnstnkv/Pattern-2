import { Typography, List } from "antd";
import { useParams } from "react-router-dom";
import {
  useGetAccountQuery,
  useGetAccountHistoryQuery,
} from "api/accounts/AccountsApi";
import { useEffect, useState } from "react";
import { useGetPaymentsQuery, useGetOverduePaymentsQuery } from "api/loans/LoansApi";
import { socket } from "config/socket";

const { Title, Text } = Typography;

const AccountView: React.FC = () => {
  const { id } = useParams();
  const { data: accountInfo } = useGetAccountQuery({ id: id || "" });
  const { data: accountHistory } = useGetAccountHistoryQuery({ id: id || "" });
  const { data: payments } = useGetPaymentsQuery({ id: id || "" });
  const { data: overduePayments } = useGetOverduePaymentsQuery({ id: id || "" });

  useEffect(() => {
    socket.on('history', (data: any) => {
      console.log('hist', data)
    })
    socket.on('newOperation', (data: any) => {
      console.log('newOperation', data)
    })
    socket.on('changeOperationStatus', (data: any) => {
      console.log('changeOperationStatus', data)
    })

  }, [])

  return (
    <>
      <Title level={4} style={{ margin: 0 }}>
        {accountInfo?.id}
      </Title>
      <Title level={3}>
        {accountInfo?.balance} {accountInfo?.currency}
      </Title >
      <List
        size='small'
        header={<Text type="warning">History</Text>}
        bordered
        dataSource={accountHistory || []}
        renderItem={(item: any) =>
          <List.Item>
            <Text>
              {item.type} - {item.payload.amountOfMoney}
            </Text>
            {item.type === 'transfer' && <Text>
              from {item.payload.senderAccountId} to {item.payload.senderAccountId}
            </Text>}
          </List.Item>}
      />
      <List
        size='small'
        header={<Text type="warning">Payments</Text>}
        bordered
        dataSource={payments || []}
        renderItem={(item: any) =>
          <List.Item>
            <Text>
              Timestamp - {item.timestamp}
            </Text>
            <Text>
              Payed - {item.payed}
            </Text>
          </List.Item>}
      />
      <List
        size='small'
        header={<Text type="warning">Overdue Payments</Text>}
        bordered
        dataSource={overduePayments || []}
        renderItem={(item: any) =>
          <List.Item>
            <Text>
              Timestamp - {item.timestamp}
            </Text>
            <Text>
              Payed - {item.payed}
            </Text>
          </List.Item>}
      />
    </>
  );
};

export default AccountView;
