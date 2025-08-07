// src/App.vue
<template>
  <component :is="layout">
    <router-view />
  </component>
</template>
<script setup>
import { onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { checkTokenOnLoad } from "@/features/init/authCheck";
import MainLayout from "@/layouts/MainLayout.vue";
import AuthLayout from "@/layouts/AuthLayout.vue";

const route = useRoute();

onMounted(() => {
  // ❗ CHỈ kiểm tra token nếu không phải trang auth
  if (route.meta.layout !== "auth") {
    checkTokenOnLoad();
  }
});

const layout = computed(() => {
  return route.meta.layout === "auth" ? AuthLayout : MainLayout;
});
</script>

<!-- <style>
@import "./assets/styles.css";
</style> -->
