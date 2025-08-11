import axios from "@/utils/axios";
import { useAuthStore } from "@/features/auth/authStore";

export const login = async (payload) => {
    try {
        const { data: response } = await axios.post("/auth/login", payload);
        const authStore = useAuthStore();
        authStore.setUser(response.user);
        return response;
    } catch (e) {
        return null;
    }
};



export const getCurrentUser = async () => {
    try {
        const { data } = await axios.get("/auth/me");
        return data;
    } catch (e) {
        return null;
    }
};


export const logout = async () => {
    try {
        await axios.post("/auth/logout");
    } catch (e) {
        console.error(e);
    }
    const authStore = useAuthStore();
    authStore.clearAuth();
    if (window.location.pathname !== "/login") {
        location.href = "/login";
    }
};

// export const register = async (payload) => {
//   const { data } = await axios.post(
//     "http://localhost:8080/api/auth/register",
//     payload
//   );
//   return data;
// };
