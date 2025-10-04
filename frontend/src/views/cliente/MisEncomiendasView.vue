<template>
  <div class="mis-encomiendas">
    <h2>Mis Encomiendas</h2>

    <div v-if="loading">Cargando...</div>
    <table v-else>
      <thead>
        <tr>
          <th>ID</th>
          <th>Destino</th>
          <th>Tipo Producto</th>
          <th>Forma de Pago</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="enc in encomiendas" :key="enc.id">
          <td>{{ enc.id }}</td>
          <td>{{ enc.ciudadDestino }}</td>
          <td>{{ enc.tipoProducto }}</td>
          <td>{{ enc.formaPago }}</td>
          <td>{{ enc.estado || 'Pendiente' }}</td>
          <td>
            <router-link :to="`/encomiendas/${enc.id}`">
              <button>Detalles</button>
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const encomiendas = ref([]);
const loading = ref(true);

onMounted(async () => {
  try {
    const token = localStorage.getItem("token");
    const user = JSON.parse(atob(token.split(".")[1])); // decodifica JWT
    const userId = user.id;

    const res = await fetch(`http://localhost:8080/encomiendas/usuario/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    encomiendas.value = await res.json();
  } catch (error) {
    console.error("Error al cargar las encomiendas:", error);
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.mis-encomiendas {
  max-width: 900px;
  margin: auto;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}
th {
  background-color: #f4f4f4;
}
button {
  padding: 5px 10px;
  cursor: pointer;
}
</style>
