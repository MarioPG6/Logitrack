<template>
  <div class="container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="credentials.email" placeholder="Correo" required />
      <input
        v-model="credentials.password"
        type="password"
        placeholder="Contraseña"
        required
      />
      <button type="submit">Ingresar</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { login } from "../../services/authService";

const router = useRouter();

const credentials = reactive({
  email: "",
  password: "",
});

const message = ref("");

async function handleLogin() {
  try {
    const res = await login(credentials);
    if (res.token) {
      localStorage.setItem("token", res.token);
      message.value = "Login exitoso";

      router.push("/").then(() => {
        window.location.reload();
      });
    } else {
      message.value = "Credenciales incorrectas";
    }
  } catch (err) {
    console.error("Error en login:", err);
    message.value = "Credenciales incorrectas";
  }
}
</script>
