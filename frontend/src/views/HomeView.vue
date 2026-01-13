<template>
  <section class="hero">
    <div class="hero-text">
      <h1 class="hero-title">LOGÍSTICA DE PEDIDOS Y ENTREGAS</h1>
      <p class="hero-subtitle">
        LogiTrack es una plataforma diseñada para optimizar la gestión de
        encomiendas. Permite a las empresas y personas registrar, controlar y
        rastrear envíos en tiempo real, con alertas automáticas sobre
        documentación obligatoria y una interfaz fácil de usar que mejora la
        eficiencia y la transparencia en cada entrega.
      </p>

      <div class="hero-buttons">
        <RouterLink :to="authLink" class="btn primary"
          >Empezar ahora!</RouterLink
        >
      </div>
    </div>

    <div class="hero-image">
      <img src="/logistica.png" alt="Plataforma logística" />
    </div>
  </section>

  <section class="objectives">
    <div class="objectives-grid">
      <div class="objectives-img">
        <img src="/Objetivos.png" alt="Gestión logística" />
      </div>

      <div class="objectives-list">
        <h2>Objetivos Generales y Propósitos</h2>

        <div class="cards">
          <div class="objective-card">
            <h3>Solución tecnológica</h3>
            <p>
              Ofrecer una plataforma digital robusta, fácil de usar y accesible
              para empresas y usuarios individuales. LogiTrack proporciona una
              solución integral que permite registrar, rastrear y gestionar
              encomiendas
            </p>
          </div>
          <div class="objective-card">
            <h3>Optimización</h3>
            <p>
              LogiTrack busca optimizar todos los aspectos relacionados con la
              logística de encomiendas, desde la asignación automática de los
              envíos hasta el seguimiento en tiempo real de cada paquete.
            </p>
          </div>
          <div class="objective-card">
            <h3>Transparencia</h3>
            <p>
              Proporcionamos a los usuarios total transparencia sobre el estado
              de sus encomiendas. A través de un sistema de seguimiento en
              tiempo real.
            </p>
          </div>
          <div class="objective-card">
            <h3>Seguridad</h3>
            <p>
              LogiTrack pone un fuerte énfasis en la seguridad de los datos.
              Toda la información relacionada se maneja de manera segura y
              accesible.
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const authLink = ref("/register");

onMounted(() => {
  const token = localStorage.getItem("token"); 
  if (token) {
    authLink.value = "/encomiendas/nueva"; 
  }
});
</script>

<style scoped>
.hero {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 80px 80px; 
  gap: 40px;
}

.hero-text {
  flex: 1;
  max-width: 600px;
  margin-left: 200px; 
}

.hero-title {
  font-size: 3rem; 
  font-weight: 800;
  color: #2e7d32;
  margin-bottom: 20px;
}

.hero-subtitle {
  font-size: 1.25rem; 
  line-height: 1.6;
  color: #444;
  margin-bottom: 30px;
}

.hero-buttons {
  display: flex;
  gap: 16px;
}

.btn {
  padding: 12px 24px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: 600;
  transition: background 0.2s ease;
}

.btn.primary {
  background: #2e7d32;
  color: #fff;
}

.btn.primary:hover {
  background: #256628;
}

.btn.secondary {
  background: #f3f4f6;
  color: #333;
}

.btn.secondary:hover {
  background: #e0e0e0;
}

.hero-image img {
  max-width: 100%;
  width: 700px;
  height: 500px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.objectives {
  padding: 60px 20px;
  background: #f9fafb;
}

.objectives-grid {
  display: grid;
  grid-template-columns: 500px 1fr; 
  column-gap: 60px;
  align-items: start;
  max-width: 1200px; 
  margin: 0 auto;
  width: 100%;
}

.objectives-list {
  margin-left: 180px;
  width: 100%;
}

.cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
  width: 100%;
}

.objectives-img {
  width: 700px;
  height: 500px; 
}

.objectives-img img {
  width: 100%;
  height: 500px;
  aspect-ratio: 4 / 3; 
  object-fit: contain;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  display: block;
}

.objectives-list h2 {
  font-size: 2rem;
  font-weight: 800;
  color: #2e7d32;
  margin: 0 0 50px 0;
}

.objective-card {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s;
}
.objective-card:hover {
  transform: translateY(-6px);
}
.icon {
  font-size: 1.8rem;
  margin-bottom: 8px;
  display: inline-block;
}
.objective-card h3 {
  color: #2e7d32;
  font-size: 1.1rem;
  margin: 0 0 6px;
}
.objective-card p {
  color: #444;
  font-size: 0.95rem;
  line-height: 1.4;
}

@media (max-width: 900px) {
  .objectives-grid {
    grid-template-columns: 1fr;
    column-gap: 0;
    row-gap: 28px;
  }
  .objectives-list {
    padding-left: 0;
    text-align: center;
  }
  .cards {
    grid-template-columns: 1fr;
  }
}
</style>
