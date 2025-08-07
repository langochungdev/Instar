import { getCurrentUser, logout } from "@/services/auth";
import { useAuthStore } from "@/features/auth/pinia";

export async function checkTokenOnLoad() {
  const auth = useAuthStore();
  const user = await getCurrentUser();
  if (user) {
    console.log("✅ Token is valid");
    auth.setUser(user); // token valid, set user again (fresh)
  } else {
    console.warn("❌ Token is invalid or expired");
    auth.clearAuth(); // token expired or invalid
    logout();
  }
}
