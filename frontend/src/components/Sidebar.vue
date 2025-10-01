<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h1 class="logo"><MapPin class="w-5 h-5" /> LogiTrack</h1>
    </div>

    <nav class="menu">
      <router-link to="/" class="menu-item">
        <Home class="w-5 h-5" /> Inicio
      </router-link>

      <template v-if="!isAuthenticated">
        <router-link to="/login" class="menu-item">
          <LogIn class="w-5 h-5" /> Iniciar Sesión
        </router-link>
        <router-link to="/register" class="menu-item">
          <UserPlus class="w-5 h-5" /> Registrarse
        </router-link>
      </template>

      <template v-else>
        <router-link to="/profile" class="menu-item">
          <User class="w-5 h-5" /> Perfil
        </router-link>

        <!-- CLIENTE -->
        <template v-if="userRole === 'CLIENTE'">
          <router-link to="/encomiendas/nueva" class="menu-item">
            <Package class="w-5 h-5" /> Generar Envío
          </router-link>
          <router-link to="/mis-encomiendas" class="menu-item">
            <Truck class="w-5 h-5" /> Ver Envíos
          </router-link>
        </template>

        <!-- TRABAJADOR -->
        <template v-if="userRole === 'TRABAJADOR'">
          <router-link to="/trabajador/encomiendas" class="menu-item">
            <Boxes class="w-5 h-5" /> Encomiendas Disponibles
          </router-link>
          <router-link to="/trabajador/reportar-problema" class="menu-item">
            <AlertTriangle class="w-5 h-5" /> Reportar Problemática
          </router-link>
        </template>

        <button @click="logout" class="menu-item logout-btn">
          <LogOut class="w-5 h-5" /> Cerrar Sesión
        </button>
      </template>
    </nav>
  </aside>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser } from "../services/authService.js";

// Iconos
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
} from "lucide-vue-next";

const router = useRouter();
const isAuthenticated = ref(false);
const userRole = ref(null);

async function checkAuth() {
  try {
    const tokenUser = await getCurrentUser(); 
    if (!tokenUser) {
      isAuthenticated.value = false;
      userRole.value = null;
      return;
    }
    isAuthenticated.value = true;
    userRole.value = tokenUser.role; 
  } catch {
    isAuthenticated.value = false;
    userRole.value = null;
  }
}

onMounted(() => {
  checkAuth();
});

function logout() {
  localStorage.removeItem("token");
  isAuthenticated.value = false;
  userRole.value = null;
  router.push("/login");
}
</script>


<style scoped>
.sidebar {
  width: 220px;
  height: 100vh;
  background-color: #2e7d32;
  color: white;
  display: flex;
  flex-direction: column;
  padding: 20px 10px;
  position: fixed;
  left: 0;
  top: 0;
  box-shadow: 2px 0 6px rgba(0, 0, 0, 0.2);
  font-family: Arial, sans-serif;
}
.sidebar-header {
  margin-bottom: 40px;
  text-align: center;
}
.logo {
  font-size: 1.4rem;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 8px;
  justify-content: center;
}
.menu {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.menu-item {
  color: white;
  text-decoration: none;
  font-size: 1rem;
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px;
  border-radius: 8px;
  transition: background 0.2s;
  background: none;
  border: none;
  text-align: left;
}
.menu-item:hover {
  background-color: #256628;
  cursor: pointer;
}
.logout-btn {
  color: #ffdddd;
}
</style>
