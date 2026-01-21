<template>
  <header
    class="fixed top-0 inset-x-0 z-50 h-16
           bg-primary text-white shadow-lg"
  >
    <div
      class="max-w-[1440px] mx-auto h-full px-4 lg:px-8
             flex items-center justify-between"
    >
      <!-- LEFT: Burger + Logo -->
      <div class="flex items-center gap-3">
        <!-- Burger (solo autenticado) -->
        <button
          v-if="isAuthenticated"
          @click="onBurger"
          aria-label="Abrir menú"
          class="w-10 h-10 flex items-center justify-center
                 rounded-lg hover:bg-white/10 transition"
        >
          <svg
            class="w-6 h-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            stroke-width="2"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M4 6h16M4 12h16M4 18h16"
            />
          </svg>
        </button>

        <!-- Logo -->
        <RouterLink
          to="/"
          class="flex items-center gap-2 transition hover:scale-105"
        >
          <img
            src="@/assets/Logo3.png"
            alt="LogiTrack"
            class="
              w-9 h-9 object-contain
              drop-shadow-sm
            "
          />

          <!-- Texto solo desktop -->
          <span
            class="hidden md:block text-xl font-extrabold tracking-tight"
          >
            LogiTrack
          </span>
        </RouterLink>
      </div>

      <!-- RIGHT: Menu -->
      <nav class="flex items-center gap-6 text-sm font-semibold">
        <template v-if="!isAuthenticated">
          <RouterLink
            to="/login"
            class="text-white/90 hover:text-white transition"
          >
            Iniciar sesión
          </RouterLink>

          <RouterLink
            to="/register"
            class="px-5 py-2 rounded-lg bg-white text-primary
                   font-bold shadow-md hover:bg-slate-100
                   transition active:scale-95"
          >
            Registrar
          </RouterLink>
        </template>

        <template v-else>
          <button
            @click="logout"
            class="text-red-200 hover:text-red-100 transition font-semibold"
          >
            Cerrar sesión
          </button>
        </template>
      </nav>
    </div>
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
