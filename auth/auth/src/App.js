import React, { useState } from "react";

function App() {
  // const [receivedMessage, setReceivedMessage] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const loginUser = () => {
    var details = {
      client_id: "bank-application-client",
      grant_type: "password",
      username,
      password,
    };

    var formBody = [];
    for (var property in details) {
      var encodedKey = encodeURIComponent(property);
      var encodedValue = encodeURIComponent(details[property]);
      formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");

    fetch(
      "http://localhost:8181/realms/bank-application-realm/protocol/openid-connect/token/",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: formBody,
      }
    )
      .then((res) => sendMessage(res.json()))
      .catch((e) => sendMessage("error", e));
  };

  const sendMessage = (message) => {
    window.opener.postMessage("message" + message, "http://localhost:3000");
  };

  // useEffect(() => {
  //   window.addEventListener("message", function (e) {
  //     if (e.origin !== "http://localhost:3000") return;
  //     setReceivedMessage("Got this message from parent: " + e.data);
  //   });
  // }, []);

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        width: "200px",
        gap: "20px",
        padding: "50px",
      }}
    >
      <h2>Authorization</h2>
      <input
        placeholder='username'
        onChange={(e) => setUsername(e.currentTarget.value)}
      />
      <input
        placeholder='password'
        onChange={(e) => setPassword(e.currentTarget.value)}
      />
      <button onClick={loginUser}>Sign in</button>
    </div>
  );
}

export default App;
