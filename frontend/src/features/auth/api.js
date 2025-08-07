// src/features/auth/api.js
import axios from "@/services/axios";

export const login = async (payload) => {
  const { data } = await axios.post(
    "http://localhost:8080/api/auth/login",
    payload
  );
  return data;
};

// export const register = async (payload) => {
//   const { data } = await axios.post(
//     "http://localhost:8080/api/auth/register",
//     payload
//   );
//   return data;
// };
