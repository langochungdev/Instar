// src/store/index.js
import { createStore } from "vuex";
import auth from "@/features/auth/store";

export default createStore({
  modules: {
    auth,
  },
});
