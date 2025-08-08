<template>
    <form @submit.prevent="submitLogin">
        tk:<input v-model="username" id="username" /> mk:<input v-model="password" id="password" />
        <button type="submit">submit</button>
    </form>
</template>

<script setup>
import { ref } from "vue";
import { login } from "../service";
import { useRouter } from "vue-router";
const router = useRouter();
const username = ref("");
const password = ref("");

const submitLogin = async () => {
    const response = await login({
        username: username.value,
        password: password.value,
    });
    if (response?.user) {
        router.push("/");
    }
};
</script>
