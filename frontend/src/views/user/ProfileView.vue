<!-- src/views/ProfileView.vue -->
<template>
  <div class="container">
    <div class="profile-card">
      <!-- Foto de perfil -->
      <img
        :src="user?.avatar || defaultAvatar"
        alt="Foto de perfil"
        class="profile-avatar"
      />

      <h2>Perfil de Usuario</h2>

      <p v-if="loading">Cargando...</p>
      <p v-else-if="error" class="error">{{ error }}</p>

      <div v-else-if="user" class="profile-info">
        <p><strong>Email:</strong> {{ user.email }}</p>
        <p><strong>Rol:</strong> {{ user.role }}</p>
        <p><strong>Nombre:</strong> {{ user.firstname }} {{ user.lastname }}</p>
      </div>

      <div v-else>
        <p>No hay usuario autenticado</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getCurrentUser } from "../../services/authService.js";

// Estado
const user = ref(null);
const loading = ref(true);
const error = ref(null);

// Imagen predeterminada (asegúrate que exista en src/assets/)
import defaultAvatar from "../../assets/defaultProfile.png";

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

<style scoped>
.container {
  display: flex;
  justify-content: center; /* Centra horizontal */
  align-items: center;     /* Centra vertical */
  min-height: 100vh;       /* Ocupa toda la altura de la ventana */
  background-color: #f9f9f9; /* Opcional: fondo más claro */
}

.profile-card {
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.15);
  padding: 30px;
  max-width: 400px;
  width: 100%;
  text-align: center;
  border-top: 6px solid #2e7d32;
}

.profile-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 4px solid #2e7d32;
  margin-bottom: 20px;
}

.profile-info {
  text-align: left;
  font-size: 16px;
  line-height: 1.6;
}

.profile-info strong {
  color: #1b5e20;
}

.error {
  color: red;
  font-weight: bold;
}
</style>
