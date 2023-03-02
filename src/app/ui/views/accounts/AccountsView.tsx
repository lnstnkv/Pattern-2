import { Card, Input, Modal, Tag, message } from "antd";
import {
  PlusCircleOutlined,
  MinusCircleOutlined,
  DeleteOutlined,
} from "@ant-design/icons";
import React, { useState } from "react";

const AccountsView: React.FC = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    messageApi.open({
      type: "success",
      content: "This is a success message",
    });
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    messageApi.open({
      type: "error",
      content: "This is an error message",
    });
    setIsModalOpen(false);
  };

  return (
    <>
      {contextHolder}
      <Card title="Card title" bordered={false} style={{ width: 500 }}>
        <Tag color="green">active</Tag>
        <span>Card content</span>
        <>
          <PlusCircleOutlined onClick={showModal} />
          <MinusCircleOutlined />
          <DeleteOutlined />
          <Modal
            title="Top up account"
            open={isModalOpen}
            onOk={handleOk}
            onCancel={handleCancel}
          >
            <Input placeholder="Amount of money" />
          </Modal>
        </>
        <p>Card content</p>
      </Card>
    </>
  );
};

export default AccountsView;
