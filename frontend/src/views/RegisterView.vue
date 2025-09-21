<style scoped src="../assets/styles.css"></style>
<template>
  <div class="container">
    <h2>Registro</h2>
    <form @submit.prevent="handleRegister">
      <input v-model="form.email" placeholder="Correo" required />
      <input v-model="form.password" type="password" placeholder="Contraseña" required />
      <input v-model="form.firstname" placeholder="Nombre" required />
      <input v-model="form.lastname" placeholder="Apellido" required />
      <input v-model="form.direction" placeholder="Dirección" />
      <input v-model="form.phone" placeholder="Teléfono" />
      <button type="submit">Registrarse</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { register } from "../services/authService";

const form = reactive({
  email: "",
  password: "",
  firstname: "",
  lastname: "",
  direction: "",
  phone: ""
});

const message = ref("");

async function handleRegister() {
  const res = await register(form);
  if (res.token) {
    localStorage.setItem("token", res.token);
    message.value = "Registro exitoso";
  } else {
    message.value = "Error en el registro";
  }
}
</script>
