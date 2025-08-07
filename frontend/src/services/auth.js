// src/services/auth.js
import axios from "./axios";

export const login = async ({ username, password }) => {
  try {
    const { data } = await axios.post("/auth/login", { username, password });
    return data;
  } catch (e) {
    alert("Đăng nhập thất bại");
    return null;
  }
};

export const getCurrentUser = async () => {
  try {
    const { data } = await axios.get("/auth/me");
    return data;
  } catch (error) {
    return null;
  }
};

export const logout = () => {
  document.cookie = "token=; Max-Age=0; path=/";
  localStorage.removeItem("user");

  if (window.location.pathname !== "/login") {
    location.href = "/login";
  }
};
