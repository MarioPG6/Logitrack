<template>
  <div class="ver-encomienda">
    <h2>Detalles de la Encomienda</h2>

    <div v-if="loading">Cargando...</div>
    <div v-else>
      <form @submit.prevent="updateEncomienda">
        <div class="form-group">
          <label>Nombre:</label>
          <input v-model="encomienda.nombre" readonly />
        </div>

        <div class="form-group">
          <label>Cédula:</label>
          <input v-model="encomienda.cedula" readonly />
        </div>

        <div class="form-group">
          <label>Correo:</label>
          <input v-model="encomienda.email" />
        </div>

        <div class="form-group">
          <label>Dirección:</label>
          <input v-model="encomienda.direccion" />
        </div>

        <div class="form-group">
          <label>Teléfono:</label>
          <input v-model="encomienda.telefono" />
        </div>

        <div class="form-group">
          <label>Tipo de Producto:</label>
          <input v-model="encomienda.tipoProducto" readonly />
        </div>

        <div class="form-group">
          <label>Ciudad Origen:</label>
          <input v-model="encomienda.ciudadOrigen" readonly />
        </div>

        <div class="form-group">
          <label>Ciudad Destino:</label>
          <input v-model="encomienda.ciudadDestino" readonly />
        </div>

        <div class="form-group">
          <label>Forma de Pago:</label>
          <input v-model="encomienda.formaPago" readonly />
        </div>

        <div class="form-group">
          <label>Tiempo:</label>
          <input v-model="encomienda.tiempo" readonly />
        </div>

        <div class="form-group">
          <label>Valor Declarado:</label>
          <input v-model="encomienda.valorDeclarado" readonly />
        </div>

        <div class="form-group">
          <label>Estado:</label>
          <input v-model="encomienda.estado" readonly />
        </div>

        <button type="submit" class="btn-guardar">Guardar Cambios</button>
      </form>

      <button @click="cancelarEncomienda" class="btn-cancelar">
        Cancelar Pedido
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const encomienda = ref(null);
const loading = ref(true);
const id = route.params.id;

onMounted(async () => {
  try {
    const token = localStorage.getItem("token");
    const res = await fetch(`http://localhost:8080/encomiendas/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();

    // Normalizamos datos (llenamos user para poder reenviar en PUT)
    encomienda.value = {
      id: data.id,
      nombre: data.nombre,
      cedula: data.cedula,
      telefono: data.telefono || data.user?.phone || "",
      email: data.email || data.user?.email || "",
      direccion: data.direccion || data.user?.direction || "",
      tipoProducto: data.tipoProducto,
      ciudadOrigen: data.ciudadOrigen,
      ciudadDestino: data.ciudadDestino,
      formaPago: data.formaPago,
      tiempo: data.tiempo,
      valorDeclarado: data.valorDeclarado,
      estado: data.estado || "Pendiente",
      user: { id: data.user?.id }, 
    };

    console.log("📦 Encomienda cargada:", encomienda.value);
  } catch (error) {
    console.error("Error al obtener la encomienda:", error);
  } finally {
    loading.value = false;
  }
});

const updateEncomienda = async () => {
  try {
    const token = localStorage.getItem("token");

    await fetch(`http://localhost:8080/encomiendas/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(encomienda.value), // 👈 enviamos TODO el objeto
    });

    alert("Encomienda actualizada con éxito");
  } catch (error) {
    console.error("Error al actualizar:", error);
  }
};

const cancelarEncomienda = async () => {
  if (!confirm("¿Estás seguro de cancelar este pedido?")) return;
  try {
    const token = localStorage.getItem("token");
    await fetch(`http://localhost:8080/encomiendas/${id}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` },
    });
    alert("Encomienda cancelada");
    router.push("/mis-encomiendas");
  } catch (error) {
    console.error("Error al cancelar:", error);
  }
};
</script>

<style scoped>
.ver-encomienda {
  max-width: 600px;
  margin: auto;
  padding: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.form-group label {
  font-weight: bold;
  margin-bottom: 5px;
}

input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.btn-guardar {
  background-color: #2e7d32;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.btn-guardar:hover {
  background-color: #256628;
}

.btn-cancelar {
  margin-top: 15px;
  background-color: #ffdddd;
  color: red;
  padding: 10px 15px;
  border: 1px solid red;
  border-radius: 6px;
  cursor: pointer;
}

.btn-cancelar:hover {
  background-color: #ffbbbb;
}
</style>

