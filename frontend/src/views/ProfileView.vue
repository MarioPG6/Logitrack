<template>
  <div>
    <h2>Perfil de Usuario</h2>

    <p v-if="loading">Cargando...</p>
    <p v-else-if="error" class="text-red-500">{{ error }}</p>

    <div v-else-if="user">
      <p><strong>ID:</strong> {{ user.id }}</p>
      <p><strong>Email:</strong> {{ user.email }}</p>
      <p><strong>Rol:</strong> {{ user.role }}</p>
      <p><strong>Nombre:</strong> {{ user.firstname }} {{ user.lastname }}</p>

      <!-- Botón de logout -->
      <button @click="logout" class="mt-4 bg-red-500 text-white px-4 py-2 rounded">
        Cerrar Sesión
      </button>
    </div>

    <div v-else>
      <p>No hay usuario autenticado</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser } from "../services/authService.js";

const router = useRouter();
const user = ref(null);
const loading = ref(true);
const error = ref(null);

onMounted(async () => {
  try {
    user.value = await getCurrentUser();
  } catch (err) {
    error.value = err.message;
  } finally {
    loading.value = false;
  }
});

function logout() {
  localStorage.removeItem("token"); // eliminamos el token
  router.push("/login"); // redirigimos al login
}
</script>
