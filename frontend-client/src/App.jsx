import { useEffect, useState } from "react";

function App() {
  const API = "http://localhost:8080";

  const [token, setToken] = useState(localStorage.getItem("token"));
  const [isRegister, setIsRegister] = useState(false);

  const [name, setName] = useState("");
  const [bio, setBio] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [users, setUsers] = useState([]);

  const login = async (e) => {
    e.preventDefault();

    const res = await fetch(`${API}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    const data = await res.text();

    if (data && !data.includes("Invalid")) {
      localStorage.setItem("token", data);
      setToken(data);
    } else {
      alert("Invalid credentials");
    }
  };

  const register = async (e) => {
    e.preventDefault();

    const res = await fetch(`${API}/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, bio, password })
    });

    if (res.ok) {
      alert("Registered successfully. Please login.");
      setIsRegister(false);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
  };

  const fetchUsers = async () => {
    const res = await fetch(`${API}/users`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (res.status === 401 || res.status === 403) {
      logout();
      return;
    }

    const data = await res.json();
    setUsers(data);
  };

  useEffect(() => {
    if (token) fetchUsers();
  }, [token]);

  if (!token) {
    return (
      <div style={{ padding: "40px", fontFamily: "Arial" }}>
        <h2>{isRegister ? "Register" : "Login"}</h2>

        <form onSubmit={isRegister ? register : login}>
          {isRegister && (
            <>
              <input
                placeholder="Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
              <br /><br />
              <textarea
                placeholder="Bio"
                value={bio}
                onChange={(e) => setBio(e.target.value)}
                required
              />
              <br /><br />
            </>
          )}

          <input
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <br /><br />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <br /><br />

          <button type="submit">
            {isRegister ? "Register" : "Login"}
          </button>
        </form>

        <br />

        <button onClick={() => setIsRegister(!isRegister)}>
          {isRegister
            ? "Already have an account? Login"
            : "New user? Register"}
        </button>
      </div>
    );
  }

  return (
    <div style={{ padding: "20px", fontFamily: "Arial" }}>
      <h1>Secure Portfolio Dashboard</h1>
      <button onClick={logout}>Logout</button>

      <h2>Users</h2>
      {users.map((user) => (
        <div key={user.id} style={{ border: "1px solid #ccc", padding: "10px", marginBottom: "10px" }}>
          <strong>{user.name}</strong>
          <div>{user.email}</div>
          <div>{user.bio}</div>
        </div>
      ))}
    </div>
  );
}

export default App;