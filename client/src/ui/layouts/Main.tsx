import { WalletOutlined, UserOutlined, BankOutlined } from '@ant-design/icons'
import { Button, Layout, Menu, theme } from 'antd'
import React, { useEffect, useState } from 'react'
import { Outlet } from 'react-router-dom'
import { Link } from 'react-router-dom'

const { Content, Sider } = Layout

const MainLayout: React.FC = () => {
  const {
    token: { colorBgContainer }
  } = theme.useToken()

  // let childWindow: Window | null

  // const sendMessage = () => {
  //   if (!childWindow) return
  //   childWindow.postMessage('Hello son!', 'http://localhost:3000')
  // }

  useEffect(() => {
    window.addEventListener('message', function (e) {
      if (e.origin !== 'http://localhost:3001') return
      console.log(e.data)
      localStorage.setItem('access', e.data.accessToken)
      localStorage.setItem('refresh', e.data.refreshToken)
    })
  }, [])

  const openWindow = () => {
    window.open('http://localhost:3001', 'childWindow', 'width=500,height=500')
  }

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider>
        <Button style={{ margin: '10px 28px' }} onClick={openWindow}>
          Войти
        </Button>
        <Menu
          theme='dark'
          mode='inline'
          defaultSelectedKeys={['4']}
          items={[
            { icon: UserOutlined, label: 'Аккаунт', link: '/profile' },
            { icon: BankOutlined, label: 'Кредиты', link: '/loans' },
            { icon: WalletOutlined, label: 'Счета', link: '/accounts' }
          ].map(({ icon, label, link }, index) => ({
            key: String(index + 1),
            icon: React.createElement(icon),
            label: <Link to={link}>{label}</Link>
          }))}
        />
      </Sider>
      <Layout>
        <Content style={{ margin: '24px 16px' }}>
          <div
            style={{
              padding: 24,
              minHeight: 360,
              background: colorBgContainer
            }}
          >
            <Outlet />
          </div>
        </Content>
      </Layout>
    </Layout>
  )
}

export default MainLayout
