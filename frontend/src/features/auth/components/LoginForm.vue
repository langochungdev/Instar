<template>
    <form @submit.prevent="submitLogin">
        tk:<input id="username" v-model="username"> mk:<input id="password" v-model="password">
        <button type="submit">
            submit
        </button>
    </form>
</template>

<script setup>
import { ref } from "vue";
import { login } from "../authService";
import { useRouter } from "vue-router";
import { getDeviceIdentity } from "@/features/device/deviceService";
const router = useRouter();
const username = ref("");
const password = ref("");
const submitLogin = async () => {

    const deviceInfo = await getDeviceIdentity();
    const payload = {
        username: username.value,
        password: password.value,
        ...deviceInfo
    };
    const response = await login(payload);
    if (response?.user) {
        router.push("/");
    }
};

</script>
