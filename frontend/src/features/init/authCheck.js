import { getCurrentUser, logout } from "@/features/auth/service";
import { useAuthStore } from "@/features/auth/store";

export async function checkTokenOnLoad() {
  console.log("Checking token on load...");
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
