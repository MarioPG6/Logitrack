<template>
  <div class="grid-container">
    <!-- Formulario Encomienda -->
    <div class="form-container">
      <h2>Registrar Encomienda</h2>

      <form @submit.prevent="registrarEncomienda">
        <div class="form-group">
          <label>Nombre</label>
          <input v-model="form.nombre" type="text" required />
        </div>

        <div class="form-group">
          <label>Cédula</label>
          <input v-model="form.cedula" type="text" required />
        </div>

        <div class="form-group">
          <label>Teléfono</label>
          <input v-model="form.telefono" type="text" required />
        </div>

        <div class="form-group">
          <label>Email</label>
          <input v-model="form.email" type="email" required />
        </div>

        <div class="form-group">
          <label>Dirección</label>
          <input v-model="form.direccion" type="text" required />
        </div>

        <!-- Tipo de producto -->
        <div class="form-group">
          <label>Tipo de Producto</label>
          <select v-model="form.tipoProducto" required>
            <option disabled value="">Seleccione</option>
            <option value="Paquete">Paquete</option>
            <option value="Documento">Documento</option>
          </select>
        </div>

        <!-- Ciudad origen -->
        <div class="form-group">
          <label>Ciudad Origen</label>
          <select v-model="form.ciudadOrigen" required>
            <option disabled value="">Seleccione una ciudad</option>
            <option v-for="ciudad in ciudades" :key="ciudad" :value="ciudad">
              {{ ciudad }}
            </option>
          </select>
        </div>

        <!-- Ciudad destino -->
        <div class="form-group">
          <label>Ciudad Destino</label>
          <select v-model="form.ciudadDestino" required>
            <option disabled value="">Seleccione una ciudad</option>
            <option v-for="ciudad in ciudades" :key="ciudad" :value="ciudad">
              {{ ciudad }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label>Forma de Pago</label>
          <select v-model="form.formaPago" required>
            <option value="Efectivo">Efectivo</option>
            <option value="Tarjeta">Tarjeta</option>
          </select>
        </div>

        <div class="form-group">
          <label>Tiempo</label>
          <select v-model="form.tiempo" required>
            <option value="Express">Express</option>
            <option value="Normal">Normal</option>
          </select>
        </div>

        <button type="submit">Registrar</button>
      </form>

      <p v-if="successMessage" class="success">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>

    <!-- Formulario Recolección -->
    <div class="form-container">
      <h2>Solicitar Recolección a Domicilio</h2>

      <form @submit.prevent="solicitarRecoleccion">
        <div class="form-group">
          <label>Dirección de recogida*</label>
          <input v-model="recoleccion.direccion" type="text" placeholder="Ej: Calle 80 # 10-20, Bogotá" required />
        </div>

        <div class="form-group">
          <label>Fecha preferida*</label>
          <input v-model="recoleccion.fecha" type="date" required />
        </div>

        <div class="form-group">
          <label>Hora preferida*</label>
          <input v-model="recoleccion.hora" type="time" required />
        </div>

        <button type="submit">Solicitar</button>
      </form>

      <p v-if="successRecoMessage" class="success">{{ successRecoMessage }}</p>
      <p v-if="errorRecoMessage" class="error">{{ errorRecoMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";

const ciudades = [
  "Bogotá",
  "Medellín",
  "Cali",
  "Barranquilla",
  "Cartagena",
  "Cúcuta",
  "Bucaramanga",
  "Pereira",
  "Santa Marta",
  "Manizales",
  "Ibagué",
  "Villavicencio",
];

const form = ref({
  nombre: "",
  cedula: "",
  telefono: "",
  email: "",
  direccion: "",
  tipoProducto: "",
  ciudadOrigen: "",
  ciudadDestino: "",
  formaPago: "Efectivo",
  tiempo: "Express",
});

const recoleccion = ref({
  direccion: "",
  fecha: "",
  hora: "",
});

const successMessage = ref("");
const errorMessage = ref("");
const successRecoMessage = ref("");
const errorRecoMessage = ref("");

async function registrarEncomienda() {
  try {
    const token = localStorage.getItem("token");
    const user = JSON.parse(atob(token.split(".")[1]));
    const userId = user.id;

    const response = await axios.post(
      "http://localhost:8080/encomiendas",
      {
        ...form.value,
        user: { id: userId },
      },
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    successMessage.value = "✅ Encomienda registrada correctamente";
    errorMessage.value = "";
    console.log("Encomienda creada:", response.data);

    Object.keys(form.value).forEach((key) => (form.value[key] = ""));
  } catch (error) {
    errorMessage.value = "❌ Error al registrar la encomienda";
    successMessage.value = "";
    console.error(error);
  }
}

async function solicitarRecoleccion() {
  try {
    const token = localStorage.getItem("token");
    const user = JSON.parse(atob(token.split(".")[1]));
    const userId = user.id;

    const response = await axios.post(
      "http://localhost:8080/recolecciones",
      {
        ...recoleccion.value,
        user: { id: userId },
      },
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    successRecoMessage.value = "✅ Recolección solicitada correctamente";
    errorRecoMessage.value = "";
    console.log("Recolección creada:", response.data);

    Object.keys(recoleccion.value).forEach((key) => (recoleccion.value[key] = ""));
  } catch (error) {
    errorRecoMessage.value = "❌ Error al solicitar la recolección";
    successRecoMessage.value = "";
    console.error(error);
  }
}
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  max-width: 1200px;
  margin: 40px auto;
}

.form-container {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 12px;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.2);
}

h2 {
  color: #2e7d32;
  margin-bottom: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 6px;
}

input,
select {
  width: 100%;
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

button {
  width: 100%;
  padding: 10px;
  background: #2e7d32;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background: #256628;
}

.success {
  margin-top: 15px;
  color: green;
}

.error {
  margin-top: 15px;
  color: red;
}
</style>
