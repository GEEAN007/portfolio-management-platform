import { useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="bg-gray-900 text-white p-4 flex justify-between">
      <h1 className="font-bold text-lg">Portfolio Manager</h1>
      <button
        onClick={logout}
        className="bg-red-500 px-4 py-1 rounded hover:bg-red-600"
      >
        Logout
      </button>
    </div>
  );
}