import { Typography, List } from "antd";
import { useParams } from "react-router-dom";
import { useGetAccountQuery, useGetAccountHistoryQuery } from "api/accounts/AccountsApi";

const { Text } = Typography;

const AccountView: React.FC = () => {
  const { id } = useParams();
  const { data: accountInfo } = useGetAccountQuery({ id: id || '' });
  const { data: accountHistory } = useGetAccountHistoryQuery({ id: id || '' });
  return (
    <>
      <Typography.Title editable level={2} style={{ margin: 0 }}>
        {accountInfo?.id}
      </Typography.Title>
      <Text>{accountInfo?.balance} {accountInfo?.currency}</Text>
      <List
      size="small"
      header={<div>History</div>}
      bordered
      dataSource={[]}
      renderItem={(item) => <List.Item>{item}</List.Item>}
    />
    </>
  );
};

export default AccountView;
