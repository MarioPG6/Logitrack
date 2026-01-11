<template>
  <div class="table-container">
    <h2>📦 Encomiendas Disponibles</h2>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Cliente</th>
          <th>Teléfono</th>
          <th>Origen</th>
          <th>Destino</th>
          <th>Producto</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="encomienda in encomiendas" :key="encomienda.id">
          <td>{{ encomienda.id }}</td>
          <td>{{ encomienda.nombre }}</td>
          <td>{{ encomienda.telefono }}</td>
          <td>{{ encomienda.ciudadOrigen }}</td>
          <td>{{ encomienda.ciudadDestino }}</td>
          <td>{{ encomienda.tipoProducto }}</td>
          <td>
            <span
              class="badge"
              :class="{
                pendiente: encomienda.estado.includes('Pendiente'),
                entregado: encomienda.estado.includes('Entregado'),
                transito: encomienda.estado.includes('En tránsito'),
              }"
            >
              {{ encomienda.estado }}
            </span>
          </td>
          <td>
            <button @click="actualizarEstado(encomienda.id, 'En tránsito')">🚚 En tránsito</button>
            <button @click="actualizarEstado(encomienda.id, 'Entregado')">✅ Entregado</button>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-if="successMessage" class="success">{{ successMessage }}</p>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import api from "@/services/api";

const encomiendas = ref([]);
const successMessage = ref("");
const errorMessage = ref("");

async function cargarEncomiendas() {
  try {
    const { data } = await api.get("/encomiendas");
    encomiendas.value = data;
  } catch (error) {
    console.error("❌ Error cargando encomiendas:", error);
    errorMessage.value = "No se pudieron cargar las encomiendas";
  }
}

async function actualizarEstado(id, nuevoEstado) {
  try {
    await api.put(`/encomiendas/${id}`, {
      estado: nuevoEstado,
    });

    successMessage.value = `✅ Encomienda ${id} actualizada a ${nuevoEstado}`;
    errorMessage.value = "";

    cargarEncomiendas();
  } catch (error) {
    console.error("❌ Error actualizando estado:", error);
    errorMessage.value = "No se pudo actualizar la encomienda";
  }
}

onMounted(() => {
  cargarEncomiendas();
});
</script>


<style scoped>
.table-container {
  max-width: 1200px;
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

table {
  width: 100%;
  border-collapse: collapse;
}

th {
  background: #2e7d32;
  color: white;
  padding: 10px;
  text-align: center;
}

td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

button {
  margin: 2px;
  padding: 6px 10px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button:first-of-type {
  background: #ff9800;
  color: white;
}

button:last-of-type {
  background: #2e7d32;
  color: white;
}

button:hover {
  opacity: 0.8;
}

.success {
  margin-top: 15px;
  color: green;
  text-align: center;
}

.error {
  margin-top: 15px;
  color: red;
  text-align: center;
}

.badge {
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 0.9rem;
}

.badge.pendiente {
  background: orange;
  color: white;
}

.badge.entregado {
  background: green;
  color: white;
}

.badge.transito {
  background: #2196f3;
  color: white;
}
</style>
