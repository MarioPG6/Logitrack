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
          <input v-model="encomienda.email" :readonly="encomienda.estado === 'CANCELADO'" />
        </div>

        <div class="form-group">
          <label>Dirección:</label>
          <input v-model="encomienda.direccion" :readonly="encomienda.estado === 'CANCELADO'" />
        </div>

        <div class="form-group">
          <label>Teléfono:</label>
          <input v-model="encomienda.telefono" :readonly="encomienda.estado === 'CANCELADO'" />
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

        <!-- Botón Guardar deshabilitado si está cancelado -->
        <button 
          type="submit" 
          class="btn-guardar"
          :disabled="encomienda.estado === 'CANCELADO'"
        >
          Guardar Cambios
        </button>

        <!-- Mensaje si está cancelada -->
        <p v-if="encomienda.estado === 'Cancelado'" class="cancelado-msg">
          🚫 Esta encomienda ha sido cancelada. No puedes editar sus datos.
        </p>
      </form>

      <!-- 🧾 Botón para generar orden de pago -->
      <button
        v-if="encomienda.estado !== 'CANCELADO'"
        @click="generarOrdenPago"
        class="btn-orden"
      >
        🧾 Generar Orden de Pago
      </button>

      <!-- Botón cancelar visible solo si el estado lo permite -->
      <button
        v-if="encomienda.estado === 'ESPERANDO RECOLECCIÓN' || encomienda.estado === 'EN PROCESO'"
        @click="cancelarEncomienda"
        class="btn-cancelar"
      >
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
      body: JSON.stringify(encomienda.value),
    });

    alert("✅ Encomienda actualizada con éxito");
  } catch (error) {
    console.error("Error al actualizar:", error);
  }
};

const cancelarEncomienda = async () => {
  if (!confirm("¿Estás seguro de cancelar este pedido?")) return;

  const estado = encomienda.value.estado;

  if (estado !== "Esperando recolección" && estado !== "En proceso") {
    alert("Solo puedes cancelar encomiendas en estado 'Esperando recolección' o 'En proceso'.");
    return;
  }

  try {
    const token = localStorage.getItem("token");
    const updated = { ...encomienda.value, estado: "CANCELADO" };

    await fetch(`http://localhost:8080/encomiendas/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(updated),
    });

    encomienda.value.estado = "CANCELADO";
    alert("🚫 Encomienda cancelada correctamente.");
  } catch (error) {
    console.error("Error al cancelar:", error);
    alert("❌ No se pudo cancelar la encomienda.");
  }
};

// 🧾 Función para generar PDF con jsPDF
const generarOrdenPago = () => {
  if (!encomienda.value) {
    alert("No hay datos de la encomienda para generar la orden de pago.");
    return;
  }

  import("jspdf").then(({ jsPDF }) => {
    const doc = new jsPDF();

    doc.setFontSize(16);
    doc.text("ORDEN DE PAGO - LOGITRACK", 70, 20);

    doc.setFontSize(12);
    doc.text(`Fecha de emisión: ${new Date().toLocaleDateString()}`, 20, 35);

    doc.text("Datos del Pedido:", 20, 50);
    doc.text(`Número de Guía: ${encomienda.value.id || "N/A"}`, 20, 60);
    doc.text(`Remitente: ${encomienda.value.nombre}`, 20, 70);
    doc.text(`Cédula: ${encomienda.value.cedula}`, 20, 80);
    doc.text(`Correo: ${encomienda.value.email}`, 20, 90);
    doc.text(`Dirección: ${encomienda.value.direccion}`, 20, 100);
    doc.text(`Teléfono: ${encomienda.value.telefono}`, 20, 110);
    doc.text(`Tipo de Producto: ${encomienda.value.tipoProducto}`, 20, 120);
    doc.text(`Ciudad Origen: ${encomienda.value.ciudadOrigen}`, 20, 130);
    doc.text(`Ciudad Destino: ${encomienda.value.ciudadDestino}`, 20, 140);
    doc.text(`Forma de Pago: ${encomienda.value.formaPago}`, 20, 150);
    doc.text(`Valor Declarado: ${encomienda.value.valorDeclarado}`, 20, 160);
    doc.text(`Estado Actual: ${encomienda.value.estado}`, 20, 170);

    doc.text("Datos del Pago:", 20, 185);
    doc.text(`Tarifa: __________________________`, 20, 195);
    doc.text(`Método de Pago: __________________`, 20, 205);
    doc.text(`Estado del Pago: __________________`, 20, 215);
    doc.text(`Observaciones: ____________________`, 20, 225);

    doc.text("Firmas:", 20, 245);
    doc.text("Firma del Remitente: ___________________________", 20, 255);
    doc.text("Firma del Operador: ____________________________", 20, 265);

    doc.setFontSize(10);
    doc.text(
      "Gracias por confiar en LogiTrack. Su envío está siendo procesado bajo nuestras políticas de seguridad y trazabilidad.",
      20,
      285,
      { maxWidth: 170 }
    );

    doc.save(`OrdenPago_${encomienda.value.id || "Encomienda"}.pdf`);
  });
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

.btn-guardar:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
  opacity: 0.7;
}

.btn-orden {
  margin-top: 15px;
  background-color: #1976d2;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.btn-orden:hover {
  background-color: #1565c0;
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

.cancelado-msg {
  color: red;
  font-weight: bold;
  margin-top: 10px;
  text-align: center;
}
</style>
