<template>
  <div class="app-layout">
    <Sidebar />
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import Sidebar from "./components/Sidebar.vue";
</script>

<style scoped>
.app-layout {
  display: flex;
}

.content {
  margin-left: 220px; /* Ancho del sidebar */
  padding: 20px;
  flex: 1;
}
</style>
