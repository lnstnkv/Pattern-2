import {
  PlusCircleOutlined,
  MinusCircleOutlined,
  DeleteOutlined,
} from "@ant-design/icons";
import { Card, Input, Modal, message, Space, Button } from "antd";
import React, { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  useDeleteAccountMutation,
  useGetAccountsQuery,
  usePostAccountMutation,
} from "api/accounts/AccountsApi";
import { useTopUpAccountMutation } from "api/accounts/AccountsOperationsApi";

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

  const [messageApi, contextHolder] = message.useMessage();
  const navigate = useNavigate();
  const { data: accountList } = useGetAccountsQuery();
  const [createAccount] = usePostAccountMutation();
  const [deleteAccount] = useDeleteAccountMutation();
  const [topUp] = useTopUpAccountMutation();
  const [withdraw] = useTopUpAccountMutation();

  console.log(accountList)

  const showModal = (cause: string, cardId: string) => {
    setModalInfo({ open: true, cause, cardId });
  };

  const topUpAccount = () => {
    topUp({ id: modalInfo.cardId, amountOfMoney: inputValue })
      .then(() => {
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
      .then(() => {
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
        return;
    }
  };

  const handleCancel = () => {
    setModalInfo(initialModalInfoState);
  };

  const handleDeleteAccount = (id: string) => {
    deleteAccount({ id })
      .then(() => {
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

  return (
    <>
      {contextHolder}
      <Button onClick={() => {
        createAccount({ currency: 'rub', ownerId: 1 })
      }}>Create account</Button>
      {accountList?.map((el: any) => (
        <Card
          title={el.id}
          bordered={false}
          style={{ width: 500 }}
          onClick={() => navigate(`/${el.id}`)}
        >
          <Space size='small'>
            <span>{el.balance}</span>
            <span>{el.currency}</span>
            <PlusCircleOutlined
              onClick={(e) => { e.stopPropagation(); showModal(causeOfModalOpening.topUp, el.id) }}
            />
            <MinusCircleOutlined
              onClick={(e) => { e.stopPropagation(); showModal(causeOfModalOpening.topUp, el.id) }}
            />
            <DeleteOutlined
              onClick={(e) => { e.stopPropagation(); handleDeleteAccount(el.id) }}
            />
          </Space>
        </Card>
      ))}

      <Modal
        title={`${causeOfModalOpening} account`}
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
    </>
  );
};

export default AccountsView;
