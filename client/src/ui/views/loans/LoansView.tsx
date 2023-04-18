import {
  Button,
  Card,
  Form,
  Input,
  message,
  Modal,
  Select,
  Space,
  Typography,
} from "antd";
import React, { useState } from "react";

import {
  useGetTariffsQuery,
  useGetLoansQuery,
  usePostLoanMutation,
  useGetRatingQuery,
  useGetPaymentsQuery,
  useGetOverduePaymentsQuery,
} from "api/loans/LoansApi";
import { useGetAccountsQuery } from "api/accounts/AccountsApi";

const { Text, Title } = Typography;
const { Option } = Select;

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

const LoansView: React.FC = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();
  const [form] = Form.useForm();
  const { data: rating } = useGetRatingQuery({ id: 1 });
  const { data: tariffsList } = useGetTariffsQuery();
  const { data: loansList } = useGetLoansQuery();
  const { data: accountList } = useGetAccountsQuery();

  const [postLoan] = usePostLoanMutation();

  const handleCreateLoan = () => {
    postLoan({ ...form.getFieldsValue(), userId: 1 })
      .then((resp) => {
        if ('error' in resp) {
          throw Error;
        }
        messageApi.open({
          type: "success",
          content: "Credit created",
        });
      })
      .catch(() => {
        messageApi.open({
          type: "error",
          content: "Failed to create credit",
        });
      })
      .finally(() => {
        setIsModalOpen(false);
        form.resetFields();
      });
  };

  const handleOk = () => {
    handleCreateLoan();
  };

  const handleCancel = () => {
    setIsModalOpen(false);
    form.resetFields();
  };

  return (
    <>
      {contextHolder}
      <Title>Rating - {rating?.returnProbability}</Title>
      <br />
      <Button onClick={() => setIsModalOpen(true)}>Create new</Button>
      {loansList?.map((el) => (
        <Card title={el.id} bordered={false} style={{ width: 500 }}>
          <Space size='small'>
            <Text>Amount: {el.creditAmount}</Text>
            <Text>Duration: {el.creditDuration}</Text>
            <Text>Tariff: {el.tariff}</Text>
          </Space>
        </Card>
      ))}
      <Modal
        title={`Get credit`}
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form
          {...layout}
          form={form}
          name='control-hooks'
          style={{ maxWidth: 600 }}
        >
          <Form.Item
            name='creditAmount'
            label='Credit amount'
            rules={[{ required: true }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name='creditDuration'
            label='Credit duration'
            rules={[{ required: true }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            name='tariffName'
            label='Tariff'
            rules={[{ required: true }]}
          >
            <Select
              placeholder='Select an option'
              // onChange={onTariffChange}
              allowClear
            >
              {tariffsList?.map((el) => (
                <Option value={el.name}>{el.name}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name='accountId'
            label='Account'
            rules={[{ required: true }]}
          >
            <Select
              placeholder='Select an option'
              // onChange={onTariffChange}
              allowClear
            >
              {accountList?.map((el) => (
                <Option value={el.id}>{el.id}</Option>
              ))}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default LoansView;
