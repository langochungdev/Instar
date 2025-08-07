<!-- src/features/auth/components/RegisterForm.vue
<template>
  <form @submit.prevent="submitRegister">
    name:
    <input v-model="name" id="name" />
    email:
    <input v-model="email" id="email" />
    mk:
    <input v-model="password" id="password" />
    <button type="submit">submit</button>
  </form>
</template>

<script>
import { register } from "../api";
import { useRouter } from "vue-router";

export default {
  setup() {
    const name = ref("");
    const email = ref("");
    const password = ref("");
    const router = useRouter();

    const submitRegister = async () => {
      const result = await register({
        name: name.value,
        email: email.value,
        password: password.value,
      });
      if (result?.message) {
        alert(result.message);
        router.push("/login");
      }
    };

    return { name, email, password, submitRegister };
  },
};
</script> -->
