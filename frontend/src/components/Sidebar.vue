<template>
  <div class="sidebar">
    <div class="head">Menú</div>

    <ul class="list">
      <template v-if="isAuthenticated">
        <li><RouterLink to="/profile">Cuenta</RouterLink></li>

        <template v-if="userRole === 'CLIENTE'">
          <li>
            <RouterLink to="/encomiendas/nueva">Generar encomienda</RouterLink>
          </li>
          <li><RouterLink to="/mis-encomiendas">Encomiendas</RouterLink></li>
        </template>

        <template v-if="userRole === 'TRABAJADOR'">
          <li>
            <RouterLink to="/trabajador/encomiendas"
              >Encomiendas disponibles</RouterLink
            >
          </li>
          <li>
            <RouterLink to="/trabajador/reportar-problema"
              >Reportar problemática</RouterLink
            >
          </li>
        </template>

        <template v-if="userRole === 'ADMINISTRADOR'">
          <li>
            <RouterLink to="/admin/usuarios">Administrar usuarios</RouterLink>
          </li>
          <li>
            <RouterLink to="/admin/dashboard"
              >Dashboard / Estadísticas</RouterLink
            >
          </li>
        </template>
      </template>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser } from "../services/authService.js";

import {
  Home,
  LogIn,
  UserPlus,
  User,
  LogOut,
  MapPin,
  Package,
  Truck,
  Boxes,
  AlertTriangle,
  Users,
  BarChart2,
} from "lucide-vue-next";

const router = useRouter();
const isAuthenticated = ref(false);
const userRole = ref(null);

async function applyAuth() {
  try {
    const u = await getCurrentUser();
    isAuthenticated.value = !!u;
    userRole.value = u?.role ?? null;
  } catch {
    isAuthenticated.value = false;
    userRole.value = null;
  }
}

function onAuthChanged() {
  applyAuth();
}
function onStorage(e) {
  if (e.key === "token") applyAuth();
}

onMounted(() => {
  applyAuth();
  window.addEventListener("auth-changed", onAuthChanged);
  window.addEventListener("storage", onStorage); 
});

onBeforeUnmount(() => {
  window.removeEventListener("auth-changed", onAuthChanged);
  window.removeEventListener("storage", onStorage);
});

function logout() {
  localStorage.removeItem("token");
  isAuthenticated.value = false;
  userRole.value = null;
  window.dispatchEvent(new Event("auth-changed")); 
  router.push("/login");
}
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
