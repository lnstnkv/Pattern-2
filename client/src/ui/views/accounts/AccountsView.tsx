import {
  PlusCircleOutlined,
  MinusCircleOutlined,
  DeleteOutlined,
} from "@ant-design/icons";
import { Card, Input, Modal, message, Space, Button, Form, Select } from "antd";
import React, { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  useDeleteAccountMutation,
  useGetAccountsQuery,
  usePostAccountMutation,
} from "api/accounts/AccountsApi";
import { useTopUpAccountMutation, useTransferMutation, useWithDrawAccountMutation } from "api/accounts/AccountsOperationsApi";
import { socket } from "config/socket";

const { Option } = Select;

const causeOfModalOpening = {
  topUp: "Top Up",
  withDraw: "Withdraw",
};

const AccountsView: React.FC = () => {
  const initialModalInfoState = useMemo(
    () => ({ open: false, cardId: "", cause: "" }),
    []
  );
  const [inputValue, setInputValue] = useState(0);
  const [modalInfo, setModalInfo] = useState(initialModalInfoState);
  const [senderId, setSenderId] = useState<string>('');
  const [receiverId, setReceiverId] = useState<string>('');
  const [isTransferModalOpen, setIsTransferModalOpen] = useState(false);

  const [messageApi, contextHolder] = message.useMessage();
  const navigate = useNavigate();
  const { data: accountList } = useGetAccountsQuery();
  const [createAccount] = usePostAccountMutation();
  const [deleteAccount] = useDeleteAccountMutation();
  const [topUp] = useTopUpAccountMutation();
  const [withdraw] = useWithDrawAccountMutation();
  const [transferMoney] = useTransferMutation();

  const showModal = (cause: string, cardId: string) => {
    setModalInfo({ open: true, cause, cardId });
  };

  const topUpAccount = () => {
    topUp({ id: modalInfo.cardId, amountOfMoney: inputValue })
      .then((resp) => {
        if ('error' in resp) {
          throw Error;
        }
        messageApi.open({
          type: "success",
          content: "Account replenished",
        });
      })
      .catch(() => {
        messageApi.open({
          type: "error",
          content: "Failed to top up account",
        });
      })
      .finally(() => {
        setModalInfo(initialModalInfoState);
      });
  };

  const withdrawAccount = () => {
    withdraw({ id: modalInfo.cardId, amountOfMoney: inputValue })
      .then((resp) => {
        if ('error' in resp) {
          throw Error;
        }
        messageApi.open({
          type: "success",
          content: "Money withdrawn",
        });
      })
      .catch(() => {
        messageApi.open({
          type: "error",
          content: "Failed to withdraw",
        });
      })
      .finally(() => {
        setModalInfo(initialModalInfoState);
      });
  };

  const handleOk = () => {
    switch (modalInfo.cause) {
      case causeOfModalOpening.topUp:
        topUpAccount();
        break;
      case causeOfModalOpening.withDraw:
        withdrawAccount();
        break;
      default:
        break;
    }

    socket.emit('history', { id: modalInfo.cardId }) 
  };

  const handleCancel = () => {
    setModalInfo(initialModalInfoState);
  };

  const handleDeleteAccount = (id: string) => {
    deleteAccount({ id })
      .then((resp) => {
        if ('error' in resp) {
          throw Error();
        }
        messageApi.open({
          type: "success",
          content: "Account deleted",
        });
      })
      .catch(() => {
        messageApi.open({
          type: "error",
          content: "Failed to delete account",
        });
      })
  };

  const handleTransfer = () => {
    setIsTransferModalOpen(false);
    transferMoney({ id: senderId, receiverId: receiverId, amountOfMoney: inputValue })
      .then((resp: any) => {
        if ('error' in resp) {
          messageApi.open({
            type: "error",
            content: resp.error.data.message[0] || "Failed to transfer money"
          });
        }
        else {
          messageApi.open({
            type: "success",
            content: "Success",
          })
        }
      })
      .catch((e) => {
        messageApi.open({
          type: "error",
          content: "Failed to transfer money"
        });
      })
  };

  return (
    <>
      {contextHolder}
      <Button onClick={() => {
        createAccount({ currency: 'rub', ownerId: 1 })
      }}>Create account</Button>
      <Button onClick={() => {
        setIsTransferModalOpen(true)
      }}>Transfer money</Button>
      {accountList?.map((el: any) => (
        <Card
          title={el.id}
          bordered={false}
          style={{ width: 500 }}
          onClick={() => navigate(`${el.id}`)}
        >
          <Space size='small'>
            <span>{el.balance}</span>
            <span>{el.currency}</span>
            <PlusCircleOutlined
              onClick={(e) => { e.stopPropagation(); showModal(causeOfModalOpening.topUp, el.id) }}
            />
            <MinusCircleOutlined
              onClick={(e) => { e.stopPropagation(); showModal(causeOfModalOpening.withDraw, el.id) }}
            />
            <DeleteOutlined
              onClick={(e) => { e.stopPropagation(); handleDeleteAccount(el.id) }}
            />
          </Space>
        </Card>
      ))}

      <Modal
        title={`${modalInfo.cause} account`}
        open={modalInfo.open}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Input
          placeholder='Amount of money'
          type='number'
          value={inputValue}
          onChange={(e) => setInputValue(+e.target.value)}
        />
      </Modal>
      <Modal
        title={`Transfer money`}
        open={isTransferModalOpen}
        onOk={handleTransfer}
        onCancel={() =>
          setIsTransferModalOpen(true)
        }
      >
        <Form
        >
          <Form.Item
            name={['address', 'province']}
            noStyle
            rules={[{ required: true, message: 'Province is required' }]}
          >
            <Select placeholder="Select province" onChange={(item) => setSenderId(item)}>
              {accountList?.filter(el => el.id !== senderId).map((item) => <Option value={item.id}>{item.id}</Option>)}
            </Select>
            <Select placeholder="Select province" onChange={(item) => setReceiverId(item)}>
              {accountList?.filter(el => el.id !== senderId).map((item) => <Option value={item.id}>{item.id}</Option>)}
            </Select>
          </Form.Item>

          <Input
            placeholder='Amount of money'
            type='number'
            value={inputValue}
            onChange={(e) => setInputValue(+e.target.value)}
          />
        </Form>
      </Modal >
    </>
  );
};

export default AccountsView;
