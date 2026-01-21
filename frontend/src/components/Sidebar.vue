<template>
  <aside
    v-if="isAuthenticated"
    class="h-full w-64 bg-primary text-white
           flex flex-col border-r border-white/10"
  >
    <!-- Header -->
    <div class="px-4 py-4 font-bold text-lg">
      Menú
    </div>

    <!-- Links -->
    <ul class="flex-1 px-2 space-y-1">
      <li>
        <RouterLink
          to="/profile"
          class="flex items-center gap-3 px-3 py-2 rounded-lg
                 hover:bg-white/10 transition"
        >
          <User class="w-5 h-5" />
          Cuenta
        </RouterLink>
      </li>

      <!-- CLIENTE -->
      <template v-if="userRole === 'CLIENTE'">
        <li>
          <RouterLink
            to="/encomiendas/nueva"
            class="flex items-center gap-3 px-3 py-2 rounded-lg
                   hover:bg-white/10 transition"
          >
            <Package class="w-5 h-5" />
            Generar encomienda
          </RouterLink>
        </li>
      </template>

      <!-- ADMIN / TRABAJADOR igual -->
    </ul>

    <!-- LOGOUT -->
    <div class="p-2 border-t border-white/10">
      <button
        @click="handleLogout"
        class="w-full flex items-center gap-3 px-3 py-2 rounded-lg
               text-red-200 hover:text-white
               hover:bg-red-500/20 transition"
      >
        <LogOut class="w-5 h-5" />
        Cerrar sesión
      </button>
    </div>
  </aside>
</template>


<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser, logout } from "@/services/authService";

import {
  User,
  Package,
  Truck,
  AlertTriangle,
  Users,
  BarChart2,
  LogOut,
} from "lucide-vue-next";

const router = useRouter();
const isAuthenticated = ref(false);
const userRole = ref(null);

async function applyAuth() {
  try {
    const user = await getCurrentUser();
    isAuthenticated.value = true;
    userRole.value = user.role;
  } catch {
    isAuthenticated.value = false;
    userRole.value = null;
  }
}

function handleLogout() {
  logout();
  router.push("/login");
}

onMounted(() => {
  applyAuth();
  window.addEventListener("auth-changed", applyAuth);
  window.addEventListener("storage", applyAuth);
});

onBeforeUnmount(() => {
  window.removeEventListener("auth-changed", applyAuth);
  window.removeEventListener("storage", applyAuth);
});
</script>


<style scoped>
.sidebar {
  background: #2e7d32; /* fondo sidebar */
  color: #e5e7eb;
  padding: 12px;
  border-right: 1px solid rgba(255, 255, 255, 0.08);
}
.head {
  font-weight: 700;
  margin-bottom: 8px;
}
.list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 8px;
}
a {
  color: #e5e7eb;
  text-decoration: none;
}
a:hover {
  color: #fff;
}
.logout-link {
  background: none;
  border: 0;
  color: #ffdddd;
  padding: 0;
  font: inherit;
  cursor: pointer;
  text-align: left;
}
.logout-link:hover {
  color: #fff;
  text-decoration: underline;
}
</style>
