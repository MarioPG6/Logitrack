<template>
  <div class="home-container">
    <!-- Logo -->
    <div class="logo-container">
      <img src="../assets/Logo.png" alt="LogiTrack Logo" class="logo" />
    </div>

    <!-- Tarjetas de información -->
    <div class="cards">
      <div class="card">
        <h2>PROYECTO</h2>
        <p>Plataforma para la gestión logística de encomiendas.</p>
      </div>

      <div class="card">
        <h2>¿Qué es?</h2>
        <p>
          LogiTrack es una plataforma diseñada para optimizar el proceso de envío
          y seguimiento de encomiendas, facilitando la gestión tanto para usuarios
          como para empresas de logística.
        </p>
      </div>

      <div class="card">
        <h2>Objetivos Generales y Propósitos</h2>
        <ul>
          <li>Brindar una solución tecnológica para la gestión de envíos.</li>
          <li>Optimizar tiempos y recursos en la logística de encomiendas.</li>
          <li>Ofrecer a los usuarios transparencia y trazabilidad de sus envíos.</li>
          <li>Centralizar la información de manera segura y accesible.</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
// Aquí no necesitas lógica extra, solo asegúrate de que el logo esté en /src/assets
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.logo-container {
  margin-bottom: 20px;
}

.logo {
  max-width: 200px;
}

.cards {
  display: grid;
  gap: 20px;
  max-width: 800px;
  width: 100%;
}

.card {
  background: #ffffff;
  padding: 20px;
  border-radius: 15px; /* Bordes redondeados */
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

.card h2 {
  color: #2e7d32; /* Verde institucional */
  margin-bottom: 10px;
}
</style>
