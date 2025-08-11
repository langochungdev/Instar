import { getCurrentUser, logout } from "@/features/auth/authService";
import { useAuthStore } from "@/features/auth/authStore";

export async function checkTokenOnLoad() {
  const user = await getCurrentUser();
  if (user) {
    const authStore = useAuthStore();
    console.log("✅ Token is valid");
    authStore.setUser(user);
  } else {
    console.warn("❌ Token is invalid or expired");
    logout();
  }
}
