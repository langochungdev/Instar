<!-- src/features/auth/components/LoginForm.vue -->
<template>
  <form @submit.prevent="submitLogin">
    tk:<input v-model="username" id="username" /> mk:<input
      v-model="password"
      id="password"
    />
    <button type="submit">submit</button>
  </form>
</template>

<script>
import { ref } from "vue";
import { login } from "@/services/auth";
import { useAuthStore } from "@/features/auth/pinia";
import { useRouter } from "vue-router";

export default {
  setup() {
    const authStore = useAuthStore();
    const router = useRouter();
    const username = ref("");
    const password = ref("");

    const submitLogin = async () => {
      const response = await login({
        username: username.value,
        password: password.value,
      });
      if (response?.accessToken) {
        // Save token to cookie
        document.cookie = `token=${response.accessToken}; path=/; max-age=${
          response.expiresIn / 1000
        }; secure; samesite=strict`;

        // Save user to pinia + localStorage
        authStore.setUser(response.user);

        alert("Đăng nhập thành công");
        router.push("/posts");
      }
    };

    return { username, password, submitLogin };
  },
};
</script>
