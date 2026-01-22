<template>
  <div class="app">
    <Topbar @toggle-sidebar="isSidebarOpen = !isSidebarOpen" />

    <div
      v-if="isAuthenticated && isMobile && isSidebarOpen"
      class="overlay"
      @click="isSidebarOpen = false"
    ></div>

    <aside
      v-if="isAuthenticated"
      class="sidebar-shell"
      :class="{ open: isSidebarOpen }"
    >
      <Sidebar />
    </aside>

    <main
      class="content"
      :class="{ pushed: isAuthenticated && isSidebarOpen && !isMobile }"
    >
      <RouterView />
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { RouterView } from "vue-router";
import Topbar from "./components/Topbar.vue";
import Sidebar from "./components/Sidebar.vue";

const isSidebarOpen = ref(false);
const isMobile = ref(false);
const isAuthenticated = ref(false);

const update = () => (isMobile.value = matchMedia("(max-width:900px)").matches);

function applyAuth() {
  const hasToken = !!localStorage.getItem("token");
  isAuthenticated.value = hasToken;

  if (!hasToken) {
    isSidebarOpen.value = false;
  }
}

onMounted(() => {
  update();
  applyAuth();

  window.addEventListener("resize", update);
  window.addEventListener("auth-changed", applyAuth);
  window.addEventListener("storage", applyAuth);
});

onBeforeUnmount(() => {
  removeEventListener("resize", update);
  window.removeEventListener("auth-changed", applyAuth);
  window.removeEventListener("storage", applyAuth);
});
</script>

<style>
:root {
  --topbar-h: 56px;
  --sidebar-w: 260px;
  --brand-green: #2e7d32;
  --sidebar-bg: #0f172a;
}

.overlay {
  position: fixed;
  inset: 0;
  z-index: 1040;
  background: rgba(0, 0, 0, 0.45);
}
@media (min-width: 901px) {
  .overlay {
    display: none;
  }
}

.sidebar-shell {
  position: fixed;
  top: var(--topbar-h);
  left: 0;
  width: var(--sidebar-w);
  height: calc(100vh - var(--topbar-h));
  background: var(--sidebar-bg);
  transform: translateX(-100%);
  transition: transform 0.2s ease;
  z-index: 1050;
  overflow-y: auto;
}
.sidebar-shell.open {
  transform: translateX(0);
}

.content {
  padding: calc(var(--topbar-h) + 24px) 16px 24px;
  transition: margin-left 0.2s ease;
}
@media (min-width: 901px) {
  .content.pushed {
    margin-left: var(--sidebar-w);
  }
}
</style>
