<template>
  <div class="p-8">
    <h2 class="text-3xl font-bold text-center mb-6 text-gray-800">
      📦 Panel de Asignación de Encomiendas
    </h2>

    <p class="text-center text-gray-600 mb-10 max-w-2xl mx-auto">
      Selecciona una encomienda y un trabajador para realizar la asignación.
    </p>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- ======================= ENCOMIENDAS ======================= -->
      <div class="table-card">
        <h3 class="text-xl font-semibold text-gray-700 mb-4">Encomiendas</h3>

        <div class="overflow-y-auto max-h-96 rounded-lg border">
          <table class="styled-table">
            <thead class="bg-blue-600 text-white">
              <tr>
                <th>ID</th>
                <th>Paquete</th>
                <th>Tipo</th>
                <th>Origen</th>
                <th>Destino</th>
                <th>Cliente</th>
                <th>Estado</th>
                <th class="text-center">Seleccionar</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="enc in encomiendas"
                :key="enc.id"
                :class="[
                  selectedEncomienda === enc.id
                    ? 'bg-blue-100 font-semibold'
                    : '',

                  enc.estado === 'CANCELADO' ? 'bg-red-100 text-red-900' : '',
                  enc.estado === 'ASIGNADO'
                    ? 'bg-green-100 text-green-800'
                    : '',
                ]"
              >
                <td class="px-3 py-2 w-16 text-center">{{ enc.id }}</td>
                <td class="px-3 py-2 w-40">{{ enc.nombre }}</td>
                <td class="px-3 py-2 w-28 text-center">
                  {{ enc.tipoProducto }}
                </td>
                <td class="px-3 py-2 w-28 text-center">
                  {{ enc.ciudadOrigen }}
                </td>
                <td class="px-3 py-2 w-28 text-center">
                  {{ enc.ciudadDestino }}
                </td>

                <td class="px-3 py-2 w-40">
                  {{ enc.user.firstname }} {{ enc.user.lastname }}
                </td>

                <td class="px-3 py-2 w-28 text-center">
                  <span
                    class="status-badge"
                    :class="{
                      'status-registrado': enc.estado === 'REGISTRADO',
                      'status-asignado': enc.estado === 'ASIGNADO',
                      'status-entregado': enc.estado === 'ENTREGADO',
                      'status-cancelado': enc.estado === 'CANCELADO',
                    }"
                  >
                    {{ enc.estado }}
                  </span>
                </td>

                <td class="px-3 py-2 text-center w-20">
                  <input
                    type="radio"
                    name="selectedEncomienda"
                    :value="enc.id"
                    v-model="selectedEncomienda"
                    class="scale-110"
                    :disabled="
                      enc.estado === 'ASIGNADO' || enc.estado === 'CANCELADO'
                    "
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- ======================= TRABAJADORES ======================= -->
      <div class="table-card">
        <h3 class="text-xl font-semibold text-gray-700 mb-4">Trabajadores</h3>

        <div class="overflow-y-auto max-h-96 rounded-lg border">
          <table class="styled-table">
            <thead class="bg-green-600 text-white">
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Email</th>
                <th class="text-center">Seleccionar</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="trabajador in trabajadores"
                :key="trabajador.id"
                :class="
                  selectedTrabajador === trabajador.id ? 'selected-row' : ''
                "
              >
                <td>{{ trabajador.id }}</td>
                <td>{{ trabajador.firstname }} {{ trabajador.lastname }}</td>
                <td>{{ trabajador.email }}</td>

                <td class="text-center">
                  <input
                    type="radio"
                    name="selectedTrabajador"
                    :value="trabajador.id"
                    v-model="selectedTrabajador"
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- ======================= BOTÓN ======================= -->
    <div class="text-center mt-10">
      <button
        class="btn-assign"
        :disabled="!selectedEncomienda || !selectedTrabajador"
        @click="asignar"
      >
        Asignar Encomienda
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import api from "@/services/api";

export default {
  name: "AsignacionEncomiendas",

  data() {
    return {
      encomiendas: [],
      trabajadores: [],
      selectedEncomienda: null,
      selectedTrabajador: null,
    };
  },

  methods: {
    async cargarDatos() {
      try {
        const encRes = await api.get("/encomiendas");
        const trabRes = await api.get("/admin/users/workers");

        this.encomiendas = encRes.data;
        this.trabajadores = trabRes.data;
      } catch (err) {
        console.error("Error cargando datos", err);
      }
    },

    async asignar() {
      try {
        await api.post("/asignaciones/asignar", null, {
          params: {
            encomiendaId: this.selectedEncomienda,
            trabajadorId: this.selectedTrabajador,
          },
        });

        alert("Encomienda asignada correctamente!");
        await this.cargarDatos();
        this.selectedEncomienda = null;
        this.selectedTrabajador = null;
      } catch (err) {
        console.error("Error asignando encomienda", err);
        alert("Error al asignar");
      }
    },
  },

  mounted() {
    this.cargarDatos();
  },
};
</script>

<style scoped>
/* === Contenedor estilo card igual al ProfileView === */
.table-card {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.15);
  border-top: 6px solid #2e7d32; /* mismo verde del perfil */
}

/* === TABLA BASE === */
.styled-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.styled-table th,
.styled-table td {
  padding: 10px 12px;
  border-bottom: 1px solid #e5e7eb;
}

/* Hover tabla */
.styled-table tbody tr:hover {
  background-color: #f3f4f6;
}

/* Cuando seleccionas la fila */
.selected-row {
  background-color: #dbeafe !important; /* azul suave */
  font-weight: bold;
}

/* Si está cancelado */
.disabled-row {
  background-color: #f8d7da !important;
  color: #b71c1c;
  pointer-events: none;
  opacity: 0.7;
}

/* Badge de estado */
.status-badge {
  padding: 3px 8px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 600;
}

.status-registrado {
  background-color: #fff9c4;
  color: #f57f17;
}

.status-asignado {
  background-color: #bbdefb;
  color: #0d47a1;
}

.status-entregado {
  background-color: #c8e6c9;
  color: #1b5e20;
}

.status-cancelado {
  background-color: #ffcdd2;
  color: #b71c1c;
}

/* Botón */
.btn-assign {
  background-color: #2e7d32;
  color: white;
  padding: 12px 25px;
  font-size: 18px;
  border-radius: 10px;
  transition: 0.3s;
}

.btn-assign:hover {
  background-color: #1b5e20;
}

.btn-assign:disabled {
  background-color: gray;
}
/* Fila para encomiendas ya asignadas */
.assigned-row {
  background-color: #e8f5e9 !important; /* verde suave */
  color: #1b5e20;
  font-weight: bold;
  opacity: 0.9;
  pointer-events: none; /* no permite clic */
}

/* Nuevo estilo badge estado ASIGNADO en verde */
.status-asignado {
  background-color: #c8e6c9;
  color: #1b5e20;
}
</style>
