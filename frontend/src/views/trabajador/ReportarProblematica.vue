<template>
  <div class="problema-container">
    <h2>📢 Reportar Problemática</h2>

    <!-- Seleccionar encomienda -->
    <div class="form-group">
      <label for="encomienda">Seleccionar Encomienda</label>
      <select v-model="problema.encomiendaId" required>
        <option disabled value="">Seleccione una encomienda</option>
        <option v-for="encomienda in encomiendas" :key="encomienda.id" :value="encomienda.id">
          #{{ encomienda.id }} - {{ encomienda.tipoProducto }} ({{ encomienda.ciudadOrigen }} ➝ {{ encomienda.ciudadDestino }})
        </option>
      </select>
    </div>

    <!-- Tipo de problema -->
    <div class="form-group">
      <label for="tipo">Tipo de Problemática</label>
      <select v-model="problema.tipo" required>
        <option disabled value="">Seleccione un tipo</option>
        <option value="Dirección incorrecta">📍 Dirección incorrecta</option>
        <option value="Cliente no encontrado">🙍 Cliente no encontrado</option>
        <option value="Paquete dañado">📦 Paquete dañado</option>
        <option value="Otro">⚠️ Otro</option>
      </select>
    </div>

    <!-- Descripción -->
    <div class="form-group">
      <label for="descripcion">Descripción</label>
      <textarea v-model="problema.descripcion" placeholder="Escribe aquí los detalles..." rows="4" required></textarea>
    </div>

    <!-- Botón -->
    <button @click="reportarProblema">🚨 Reportar</button>

    <!-- Mensajes -->
    <p v-if="successMessage" class="success">{{ successMessage }}</p>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import api from "@/services/api";

const encomiendas = ref([]);
const problema = ref({
  encomiendaId: "",
  tipo: "",
  descripcion: "",
});

const successMessage = ref("");
const errorMessage = ref("");

// 🔹 Obtener todas las encomiendas al cargar
async function cargarEncomiendas() {
  try {
    const { data } = await api.get("/encomiendas");
    encomiendas.value = data;
  } catch (error) {
    console.error("Error cargando encomiendas:", error);
  }
}

async function reportarProblema() {
  try {
    const token = localStorage.getItem("token");
    const user = JSON.parse(atob(token.split(".")[1])); // trabajador

    await api.post("/problemas", {
      trabajadorId: user.id,
      encomiendaId: problema.value.encomiendaId,
      descripcion: problema.value.descripcion,
    });

    successMessage.value = "✅ Problemática reportada correctamente.";
    errorMessage.value = "";

    // limpiar formulario
    problema.value = { encomiendaId: "", tipo: "", descripcion: "" };
  } catch (error) {
    console.error(error);
    errorMessage.value = "❌ Error al reportar la problemática.";
    successMessage.value = "";
  }
}

onMounted(() => {
  cargarEncomiendas();
});
</script>


<style scoped>
.problema-container {
  max-width: 600px;
  margin: 40px auto;
  padding: 25px;
  background: #f9f9f9;
  border-radius: 16px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
  font-family: "Segoe UI", sans-serif;
  animation: fadeIn 0.6s ease-in-out;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #c62828;
}

.form-group {
  margin-bottom: 15px;
}

label {
  font-weight: bold;
  display: block;
  margin-bottom: 6px;
}

select,
textarea {
  width: 100%;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
  resize: none;
}

button {
  width: 100%;
  padding: 12px;
  background: #c62828;
  color: white;
  font-size: 1rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

button:hover {
  background: #a61c1c;
}

.success {
  margin-top: 15px;
  color: green;
  font-weight: bold;
  text-align: center;
}

.error {
  margin-top: 15px;
  color: red;
  font-weight: bold;
  text-align: center;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
