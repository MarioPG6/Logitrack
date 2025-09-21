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
    </div>

    <div v-else>
      <p>No hay usuario autenticado</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getCurrentUser } from "../services/authService.js";

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
</script>
