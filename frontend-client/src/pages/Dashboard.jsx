import { useEffect, useState } from "react";
import API from "../services/api";
import Navbar from "../components/Navbar";

export default function Dashboard() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    API.get("/users")
      .then(res => setUsers(res.data))
      .catch(err => console.log(err));
  }, []);

  return (
    <>
      <Navbar />
      <div className="p-6">
        <h2 className="text-2xl font-bold mb-4">Users</h2>
        <div className="grid gap-4">
          {users.map(user => (
            <div key={user.id} className="bg-white shadow p-4 rounded">
              <h3 className="font-bold">{user.name}</h3>
              <p>{user.email}</p>
              <p className="text-sm text-gray-500">{user.role}</p>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}