<template>
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
</template>


<script setup>
import { ref } from "vue";
import axios from "axios";

// Lista de ciudades principales de Colombia (puedes ampliarla)
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
  valorDeclarado: 0,
});

const successMessage = ref("");
const errorMessage = ref("");

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
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    successMessage.value = "✅ Encomienda registrada correctamente";
    errorMessage.value = "";
    console.log("Encomienda creada:", response.data);

    // Resetear formulario
    Object.keys(form.value).forEach((key) => (form.value[key] = ""));
  } catch (error) {
    errorMessage.value = "❌ Error al registrar la encomienda";
    successMessage.value = "";
    console.error(error);
  }
}
</script>

<style scoped>
.form-container {
  max-width: 600px;
  margin: 40px auto;
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
