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
} from "api/loans/LoansApi";

const { Text } = Typography;
const { Option } = Select;

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

const LoansView: React.FC = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();
  const [form] = Form.useForm();
  const { data: tariffsList } = useGetTariffsQuery();
  const { data: loansList } = useGetLoansQuery();
  const [postLoan] = usePostLoanMutation();

  const handleCreateLoan = () => {
    postLoan(form.getFieldsValue())
      .then(() => {
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
      <Button onClick={() => setIsModalOpen(true)}>Create new</Button>
      {loansList?.map((el) => (
        <Card title={el.id} bordered={false} style={{ width: 500 }}>
          <Space size='small'>
            <Text>Amount: {el.creditAmount}</Text>
            <Text>Duration: {el.creditDuration}</Text>
            <Text>Tariff: {el.tariff.name}</Text>
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
              placeholder='Select a option and change input text above'
              // onChange={onTariffChange}
              allowClear
            >
              {tariffsList?.map((el) => (
                <Option value={el.name}>{el.name}</Option>
              ))}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default LoansView;
