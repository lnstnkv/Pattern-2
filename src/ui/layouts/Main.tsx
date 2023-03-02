import { WalletOutlined, UserOutlined, BankOutlined } from "@ant-design/icons";
import { Layout, Menu, theme } from "antd";
import React from "react";
import { Outlet } from "react-router-dom";
import { Link } from "react-router-dom";

const { Content, Sider } = Layout;

const MainLayout: React.FC = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Sider>
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={["4"]}
          items={[
            { icon: UserOutlined, label: "Аккаунт", link: "/profile" },
            { icon: BankOutlined, label: "Кредиты", link: "/loans" },
            { icon: WalletOutlined, label: "Счета", link: "/accounts" },
          ].map(({ icon, label, link }, index) => ({
            key: String(index + 1),
            icon: React.createElement(icon),
            label: <Link to={link}>{label}</Link>,
          }))}
        />
      </Sider>
      <Layout>
        <Content style={{ margin: "24px 16px" }}>
          <div
            style={{
              padding: 24,
              minHeight: 360,
              background: colorBgContainer,
            }}
          >
            <Outlet />
          </div>
        </Content>
      </Layout>
    </Layout>
  );
};

export default MainLayout;
