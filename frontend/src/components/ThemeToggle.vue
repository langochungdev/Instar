<template>
  <div class="form-check form-switch">
    <input
      class="form-check-input"
      type="checkbox"
      id="toggleDark"
      v-model="darkMode"
    />
    <label class="form-check-label" for="toggleDark"> Dark Mode </label>
  </div>
</template>

<script setup>
import { useDark } from "@vueuse/core";
const darkMode = useDark({ attribute: "data-bs-theme" });
</script>
