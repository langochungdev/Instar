<template>
  <div class="container mt-5">
    <nav>
      <router-link to="/login">login</router-link>
      <br>
      <a href="#" @click.prevent="logout();">logout</a>
    </nav>
    <h2 class="mb-4">Main Layout</h2>
    <router-view />
  </div>
</template>

<script setup>
import { logout } from "@/features/auth/service";
</script>
