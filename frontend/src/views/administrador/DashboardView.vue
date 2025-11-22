<template>
  <div class="p-6 bg-gray-100 min-h-screen">

    <!-- Panel Superior -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <div class="summary-box">
        <h3 class="title">Usuarios</h3>
        <p class="value">{{ stats.totalUsuarios }}</p>
      </div>

      <div class="summary-box">
        <h3 class="title">Activos</h3>
        <p class="value">{{ stats.trabajadoresActivos }}</p>
      </div>

      <div class="summary-box">
        <h3 class="title">Encomiendas</h3>
        <p class="value">{{ stats.encomiendasActivas }}</p>
      </div>

      <div class="summary-box">
        <h3 class="title">Reportes</h3>
        <p class="value text-red-600">{{ stats.reportesPendientes }}</p>
      </div>
    </div>

    <!-- Contenedor de gráficas (dos columnas) -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">

      <!-- Encomiendas por Mes -->
      <div class="panel">
        <h2 class="panel-title">📦 Encomiendas por Mes</h2>
        <div class="chart-box">
          <canvas id="chartEncomiendas"></canvas>
        </div>
      </div>

      <!-- Estados de encomienda -->
      <div class="panel">
        <h2 class="panel-title">🚚 Estado de Encomiendas</h2>
        <div class="chart-box">
          <canvas id="chartEstados"></canvas>
        </div>
      </div>

    </div>

    <!-- Top Trabajadores -->
    <div class="panel mt-8">
      <h2 class="panel-title">👨‍🔧 Top Trabajadores Activos</h2>
      <ul class="divide-y divide-gray-200">
        <li
          v-for="(w, i) in topTrabajadores"
          :key="i"
          class="flex justify-between py-2"
        >
          <span class="font-medium text-gray-700">{{ w.nombre }}</span>
          <span class="font-bold text-green-700">{{ w.encomiendas }}</span>
        </li>
      </ul>
    </div>

  </div>
</template>

<script>
import { onMounted, reactive } from "vue";
import Chart from "chart.js/auto";

export default {
  name: "DashboardView",
  setup() {
    const stats = reactive({
      totalUsuarios: 0,
      trabajadoresActivos: 0,
      encomiendasActivas: 0,
      reportesPendientes: 0,
    });

    const topTrabajadores = reactive([]);

    async function fetchStats() {
      try {
        const fakeStats = {
          totalUsuarios: 1523,
          trabajadoresActivos: 48,
          encomiendasActivas: 237,
          reportesPendientes: 12,

          graficoMeses: {
            labels: [
              "Ene",
              "Feb",
              "Mar",
              "Abr",
              "May",
              "Jun",
              "Jul",
              "Ago",
              "Sep",
              "Oct",
              "Nov",
              "Dic",
            ],
            values: [
              120, 150, 180, 200, 170, 230, 250, 270, 300, 280, 260, 310,
            ],
          },

          graficoEstados: {
            labels: ["Pendiente", "En ruta", "Entregada", "Devuelta"],
            values: [50, 120, 320, 15],
          },
        };

        const fakeTop = [
          { nombre: "Carlos Ramirez", encomiendas: 89 },
          { nombre: "José González", encomiendas: 77 },
          { nombre: "Ana Pérez", encomiendas: 71 },
          { nombre: "Diana López", encomiendas: 68 },
          { nombre: "Santiago Torres", encomiendas: 64 },
        ];

        Object.assign(stats, fakeStats);
        topTrabajadores.splice(0, topTrabajadores.length, ...fakeTop);

        renderCharts(fakeStats.graficoMeses, fakeStats.graficoEstados);
      } catch (error) {
        console.error("Error cargando estadísticas simuladas", error);
      }
    }

    function renderCharts(dataMeses, dataEstados) {
      new Chart(document.getElementById("chartEncomiendas"), {
        type: "line",
        data: {
          labels: dataMeses.labels,
          datasets: [
            {
              label: "Encomiendas",
              data: dataMeses.values,
              borderColor: "#2e7d32",
              backgroundColor: "rgba(46,125,50,0.2)",
              fill: true,
              tension: 0.3,
            },
          ],
        },
        options: {
          maintainAspectRatio: false,
        },
      });

      new Chart(document.getElementById("chartEstados"), {
        type: "doughnut",
        data: {
          labels: dataEstados.labels,
          datasets: [
            {
              label: "Encomiendas",
              data: dataEstados.values,
              backgroundColor: ["#f87171", "#60a5fa", "#34d399", "#fbbf24"],
            },
          ],
        },
        options: {
          maintainAspectRatio: false,
        },
      });
    }

    onMounted(fetchStats);

    return { stats, topTrabajadores };
  },
};
</script>

<style scoped>
.summary-box {
  background: white;
  padding: 1rem 1.5rem;
  border-radius: 12px;
  box-shadow: 0 2px 5px #00000015;
  text-align: center;
}

.summary-box .title {
  color: #4b5563;
  font-size: 0.9rem;
  font-weight: 600;
}

.summary-box .value {
  margin-top: 6px;
  font-size: 1.8rem;
  font-weight: bold;
  color: #127e1c;
}

.panel {
  background: white;
  padding: 1.3rem;
  border-radius: 12px;
  box-shadow: 0 2px 5px #00000015;
}

.panel-title {
  color: #374151;
  font-size: 1.1rem;
  font-weight: 700;
  margin-bottom: 0.8rem;
}

.chart-box {
  height: 220px;
  position: relative;
}

canvas {
  width: 100% !important;
  height: 100% !important;
}
</style>
