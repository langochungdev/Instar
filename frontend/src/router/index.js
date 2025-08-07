// src/router/index.js
import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/features/auth/views/LoginView.vue";
// import RegisterView from "@/features/auth/views/RegisterView.vue";
// import PostListView from "@/features/post/views/PostListView.vue";
// import PostDetailView from "@/features/post/views/PostDetailView.vue";

const routes = [
  {
    path: "/login",
    name: "Login",
    component: LoginView,
    meta: { layout: "auth", requiresGuest: true },
  },
  // {
  //   path: "/register",
  //   name: "Register",
  //   component: RegisterView,
  //   meta: { layout: "auth", requiresGuest: true },
  // },
  // {
  //   path: "/posts",
  //   name: "Posts",
  //   component: PostListView,
  //   meta: { layout: "main", requiresAuth: true },
  // },
  // {
  //   path: "/posts/:id",
  //   name: "PostDetail",
  //   component: PostDetailView,
  //   meta: { layout: "main", requiresAuth: true },
  // },
  // {
  //   path: "/",
  //   redirect: "/posts",
  // },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Middleware kiểm tra auth
// router.beforeEach((to, from, next) => {
//   const token = document.cookie.replace(
//     /(?:(?:^|.*;\s*)token\s*=\s*([^;]*).*$)|^.*$/,
//     "$1"
//   );
//   const isAuthenticated = !!token;

//   if (to.meta.requiresAuth && !isAuthenticated) {
//     return next("/login");
//   }

//   if (to.meta.requiresGuest && isAuthenticated) {
//     return next("/posts");
//   }

//   next();
// });

export default router;
