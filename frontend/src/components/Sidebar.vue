<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h1 class="logo">LogiTrack</h1>
    </div>

    <nav class="menu">
      <router-link to="/" class="menu-item">
        <i class="fas fa-home"></i> Página Principal
      </router-link>
      <router-link to="/login" class="menu-item">
        <i class="fas fa-box"></i> Iniciar Sesión
      </router-link>
      <router-link to="/register" class="menu-item">
        <i class="fas fa-chart-bar"></i> Registrarse
      </router-link>
    </nav>
  </aside>
</template>

<script setup></script>

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
}

.menu-item:hover {
  background-color: #256628;
}
</style>
