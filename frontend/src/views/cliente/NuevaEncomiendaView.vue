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
          <label>Dirección de destino</label>
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

        <!-- Campos adicionales si es Paquete -->
        <div v-if="form.tipoProducto === 'Paquete'" class="paquete-fields">
          <div class="form-group">
            <label>Altura (cm)</label>
            <input v-model.number="form.altura" type="number" min="0" required />
          </div>
          <div class="form-group">
            <label>Ancho (cm)</label>
            <input v-model.number="form.ancho" type="number" min="0" required />
          </div>
          <div class="form-group">
            <label>Largo (cm)</label>
            <input v-model.number="form.largo" type="number" min="0" required />
          </div>
        </div>

        <!-- Ciudades -->
        <div class="form-group">
          <label>Ciudad Origen</label>
          <select v-model="form.ciudadOrigen" required>
            <option disabled value="">Seleccione una ciudad</option>
            <option v-for="ciudad in ciudades" :key="ciudad" :value="ciudad">
              {{ ciudad }}
            </option>
          </select>
        </div>

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
          <label>Tiempo de entrega</label>
          <select v-model="form.tiempo" required>
            <option value="Express">Express (1 a 2 días)</option>
            <option value="Normal">Normal (1 semana)</option>
          </select>
        </div>

        <!-- Solicitud de recolección -->
        <div class="form-group">
          <label>¿Solicitar recolección a domicilio?</label>
          <select v-model="form.solicitarRecoleccion" required>
            <option value="No">No</option>
            <option value="Sí">Sí</option>
          </select>
        </div>

        <!-- Campos de recolección si elige "Sí" -->
        <div v-if="form.solicitarRecoleccion === 'Sí'" class="recoleccion-extra">
          <div class="form-group">
            <label>Dirección de recogida*</label>
            <input v-model="recoleccion.direccion" type="text" required />
          </div>
          <div class="form-group">
            <label>Fecha preferida*</label>
            <input v-model="recoleccion.fecha" type="date" required />
          </div>
          <div class="form-group">
            <label>Hora preferida*</label>
            <input v-model="recoleccion.hora" type="time" required />
          </div>
        </div>

        <button type="submit" :disabled="!isFormValid">Registrar</button>

        <!-- 💰 Valor declarado dinámico -->
        <div class="valor-declarado">
          <strong>Valor declarado estimado:</strong>
          <span>${{ valorDeclarado.toLocaleString() }}</span>
        </div>
      </form>

      <p v-if="successMessage" class="success">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import axios from "axios";

const ciudades = [
  "Bogotá","Medellín","Cali","Barranquilla","Cartagena",
  "Cúcuta","Bucaramanga","Pereira","Santa Marta","Manizales",
  "Ibagué","Villavicencio"
];

const form = ref({
  nombre: "", cedula: "", telefono: "", email: "", direccion: "",
  tipoProducto: "", ciudadOrigen: "", ciudadDestino: "",
  formaPago: "Efectivo", tiempo: "Normal", solicitarRecoleccion: "No",
  altura: 0, ancho: 0, largo: 0
});

const recoleccion = ref({ direccion: "", fecha: "", hora: "" });

const successMessage = ref("");
const errorMessage = ref("");

// 💰 Valor declarado dinámico
const valorDeclarado = computed(() => {
  let valor = 0;

  if (form.value.tipoProducto === "Documento") valor += 3000;
  if (form.value.tipoProducto === "Paquete") {
    const { altura, ancho, largo } = form.value;
    if (altura <= 12 && ancho <= 12 && largo <= 16) valor = 5000;
    else if (altura >= 18 && ancho >= 18 && largo >= 16) valor = 10000;
    if (altura > 18 && ancho > 18 && largo > 24) valor = 20000;
  }
  if (form.value.tiempo === "Express") valor += 15000;
  if (form.value.solicitarRecoleccion === "Sí") valor += 10000;

  return valor;
});

// ✅ Validación formulario
const isFormValid = computed(() => {
  const values = Object.values(form.value);
  const validBase = values.every((v) => v !== "" && v !== null);
  if (form.value.solicitarRecoleccion === "Sí") {
    return (
      validBase &&
      recoleccion.value.direccion &&
      recoleccion.value.fecha &&
      recoleccion.value.hora
    );
  }
  return validBase;
});

async function registrarEncomienda() {
  try {
    const token = localStorage.getItem("token");
    const user = JSON.parse(atob(token.split(".")[1]));
    const userId = user.id;

    const estado =
      form.value.solicitarRecoleccion === "Sí"
        ? "ESPERANDO RECOLECCIÓN"
        : "EN PROCESO";

    const response = await axios.post(
      "http://localhost:8080/encomiendas",
      {
        ...form.value,
        valorDeclarado: valorDeclarado.value,
        estado,
        recoleccion:
          form.value.solicitarRecoleccion === "Sí"
            ? { ...recoleccion.value }
            : null,
        user: { id: userId },
      },
      { headers: { Authorization: `Bearer ${token}` } }
    );

    successMessage.value =
      form.value.solicitarRecoleccion === "Sí"
        ? "✅ Encomienda registrada y en espera de recolección"
        : "✅ Encomienda registrada correctamente";

    errorMessage.value = "";

    Object.keys(form.value).forEach((key) => (form.value[key] = ""));
    Object.keys(recoleccion.value).forEach((key) => (recoleccion.value[key] = ""));
  } catch (error) {
    errorMessage.value = "❌ Error al registrar la encomienda";
    successMessage.value = "";
    console.error(error);
  }
}
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
  max-width: 800px;
  margin: 40px auto;
}
.form-container {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.2);
}
h2 {
  color: #2e7d32;
  margin-bottom: 20px;
  text-align: center;
}
.form-group { margin-bottom: 15px; }
label {
  display: block;
  font-weight: bold;
  margin-bottom: 6px;
}
input, select {
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
button:disabled {
  background: #9e9e9e;
  cursor: not-allowed;
}
button:hover:enabled {
  background: #256628;
}
.valor-declarado {
  margin-top: 15px;
  padding: 10px;
  background: #e8f5e9;
  border-radius: 8px;
  text-align: center;
  font-size: 1.1rem;
}
.success { margin-top: 15px; color: green; }
.error { margin-top: 15px; color: red; }
</style>
