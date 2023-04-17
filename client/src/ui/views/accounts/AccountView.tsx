import { Typography, List } from "antd";

import { useParams } from "react-router-dom";
import {
  useGetAccountQuery,
  useGetAccountHistoryQuery,
} from "api/accounts/AccountsApi";

const { Title, Text } = Typography;

const AccountView: React.FC = () => {
  const { id } = useParams();
  const { data: accountInfo } = useGetAccountQuery({ id: id || "" });
  const { data: accountHistory } = useGetAccountHistoryQuery({ id: id || "" });
  console.log(id)
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
    </>
  );
};

export default AccountView;
