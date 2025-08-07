// src/features/auth/pinia.js
import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: JSON.parse(localStorage.getItem("user") || "null"),
  }),
  actions: {
    setUser(user) {
      this.user = user;
      localStorage.setItem("user", JSON.stringify(user));
    },
    clearAuth() {
      this.user = null;
      localStorage.removeItem("user");
      document.cookie = "token=; Max-Age=0; path=/";
    },
  },
});
