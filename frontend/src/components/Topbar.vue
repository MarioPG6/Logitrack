<template>
  <header class="topbar">
    <button
      v-if="isAuthenticated"
      class="burger"
      @click="onBurger"
      aria-label="Abrir menú"
    >
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
        <path
          d="M3 6h18M3 12h18M3 18h18"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
        />
      </svg>
    </button>

    <RouterLink to="/" class="title-link">
      <h1 class="title">LogiTrack</h1>
    </RouterLink>

    <nav class="menu">
      <template v-if="!isAuthenticated">
        <RouterLink to="/register" class="item">Registrar</RouterLink>
        <RouterLink to="/login" class="item">Log-in</RouterLink>
      </template>
      <template v-else>
        <button class="logout-btn" @click="logout">Cerrar Sesión</button>
      </template>
    </nav>
  </header>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { RouterLink, useRouter } from "vue-router";

const emit = defineEmits(["toggle-sidebar"]);
const router = useRouter();
const isAuthenticated = ref(false);

function onBurger() {
  emit("toggle-sidebar");
}

function applyAuth() {
  isAuthenticated.value = !!localStorage.getItem("token");
}

function logout() {
  localStorage.removeItem("token");
  applyAuth();
  emit("toggle-sidebar");
  window.dispatchEvent(new Event("auth-changed"));
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
.topbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 56px;
  z-index: 1100;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  background: #2e7d32; /* tu verde */
  color: #fff;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.title-link {
  color: inherit;
  text-decoration: none;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

.title {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 700;
}

.burger {
  width: 40px;
  height: 40px;
  border: 0;
  border-radius: 8px;
  background: transparent;
  color: inherit;
  cursor: pointer;
}
.burger:hover {
  background: rgba(255, 255, 255, 0.15);
}

.menu {
  display: flex;
  gap: 16px;
  align-items: center;
}
.item {
  color: #e2e8f0;
  text-decoration: none;
  font-size: 0.95rem;
}
.item:hover {
  color: #fff;
}
.item.router-link-active {
  color: #fff;
}

.logout-btn {
  background: none;
  border: none;
  color: #ff4d4d;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
}
.logout-btn:hover {
  text-decoration: underline;
}
</style>
