<template>
  <div class="p-8 bg-gray-50 min-h-screen">
    <!-- Título principal -->
    <h1 class="text-4xl font-extrabold text-center mb-10 text-green-700 tracking-wide">
      📊 Panel de Control - Administrador
    </h1>

    <!-- Cards Resumen -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-12">
      <div class="card">
        <h2 class="text-lg font-semibold text-gray-600">Usuarios Totales</h2>
        <p class="text-3xl font-bold text-green-700">{{ stats.totalUsuarios }}</p>
      </div>
      <div class="card">
        <h2 class="text-lg font-semibold text-gray-600">Trabajadores Activos</h2>
        <p class="text-3xl font-bold text-green-700">{{ stats.trabajadoresActivos }}</p>
      </div>
      <div class="card">
        <h2 class="text-lg font-semibold text-gray-600">Encomiendas Activas</h2>
        <p class="text-3xl font-bold text-green-700">{{ stats.encomiendasActivas }}</p>
      </div>
      <div class="card">
        <h2 class="text-lg font-semibold text-gray-600">Reportes Pendientes</h2>
        <p class="text-3xl font-bold text-red-600">{{ stats.reportesPendientes }}</p>
      </div>
    </div>

    <!-- Gráficas -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-10">
      <!-- Encomiendas por mes -->
      <div class="bg-white p-6 rounded-xl shadow-lg">
        <h2 class="text-xl font-bold text-gray-700 mb-4">📦 Encomiendas por Mes</h2>
        <canvas id="chartEncomiendas"></canvas>
      </div>

      <!-- Distribución por estado -->
      <div class="bg-white p-6 rounded-xl shadow-lg">
        <h2 class="text-xl font-bold text-gray-700 mb-4">🚚 Estado de Encomiendas</h2>
        <canvas id="chartEstados"></canvas>
      </div>
    </div>

    <!-- Top trabajadores -->
    <div class="bg-white p-6 rounded-xl shadow-lg mt-10">
      <h2 class="text-xl font-bold text-gray-700 mb-4">👨‍🔧 Top 5 Trabajadores Activos</h2>
      <ul class="divide-y divide-gray-200">
        <li v-for="(worker, index) in topTrabajadores" :key="index" class="py-3 flex justify-between">
          <span class="font-semibold text-gray-600">{{ worker.nombre }}</span>
          <span class="text-green-600 font-bold">{{ worker.encomiendas }} encomiendas</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive } from "vue";
import Chart from "chart.js/auto";
import axios from "axios";

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
        // 🔹 Aquí debes conectar con tus endpoints de Spring Boot
        const resStats = await axios.get("http://localhost:8080/admin/stats");
        const resTop = await axios.get("http://localhost:8080/admin/top-trabajadores");

        Object.assign(stats, resStats.data);
        topTrabajadores.splice(0, topTrabajadores.length, ...resTop.data);

        renderCharts(resStats.data.graficoMeses, resStats.data.graficoEstados);
      } catch (error) {
        console.error("Error cargando estadísticas", error);
      }
    }

    function renderCharts(dataMeses, dataEstados) {
      // 📦 Encomiendas por Mes
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
      });

      // 🚚 Estados de Encomiendas
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
      });
    }

    onMounted(fetchStats);

    return { stats, topTrabajadores };
  },
};
</script>


