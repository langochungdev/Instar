<template>
  <component :is="layout">
    <router-view />
  </component>
</template>

<script setup>
import { onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { checkTokenOnLoad } from "@/features/init/authCheck";
import MainLayout from "@/layouts/MainLayout.vue";
import AuthLayout from "@/layouts/AuthLayout.vue";
import { getToken } from "firebase/messaging";
import { messaging, vapidKey } from "@/utils/firebase"; // file firebase.js bạn đã tạo

const route = useRoute();
const router = useRouter();

// onMounted(async () => {
//   if ("serviceWorker" in navigator) {
//     try {
//       const registration = await navigator.serviceWorker.register("/firebase-messaging-sw.js");
//       const permission = await Notification.requestPermission();
//       if (permission !== "granted") {
//         return;
//       }
//       const token = await getToken(messaging, { vapidKey, serviceWorkerRegistration: registration });
//       console.log("deviceToken", token);
//       if (token) {
//         localStorage.setItem("deviceToken", token);
//       }
//     } catch (err) {
//       console.error("Đăng ký Service Worker thất bại", err);
//     }
//   }
// });

router.isReady().then(() => {
  if (route.meta?.layout !== "auth") {
    checkTokenOnLoad();
  }
});

const layout = computed(() => {
  return route.meta.layout === "auth" ? AuthLayout : MainLayout;
});
</script>
