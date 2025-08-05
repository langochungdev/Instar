<template>
  <div class="container py-5">
    <ThemeToggle />
    <h1 class="mt-4">Hello Bootstrap + Vue</h1>
    <p>
      Đang ở mode: <b>{{ isDark ? "Dark" : "Light" }}</b>
    </p>
    <button class="btn btn-primary">Primary Button</button>
  </div>
</template>

<script setup>
import { useDark } from "@vueuse/core";
import ThemeToggle from "./components/ThemeToggle.vue";

const isDark = useDark({ attribute: "data-bs-theme" });
</script>
