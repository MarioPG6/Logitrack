<template>
  <main class="relative">
    <!-- Fondo decorativo -->
    <div class="absolute inset-0 -z-10 overflow-hidden">
      <div
        class="absolute top-0 left-1/2 -translate-x-1/2 w-full h-[600px]
               bg-gradient-to-b from-primary/10 to-transparent"
      ></div>
    </div>

    <!-- HERO -->
    <section class="max-w-[960px] mx-auto pt-32 pb-20 px-6 text-center">
      <h1
        class="tracking-tight text-[42px] lg:text-[56px] font-extrabold leading-[1.1]
               text-slate-900 dark:text-white pb-6"
      >
        LOGÍSTICA DE PEDIDOS Y ENTREGAS <br />
        <span class="text-primary">en tiempo real</span>
      </h1>

      <p
        class="text-slate-600 dark:text-slate-300 text-lg lg:text-xl
               leading-relaxed max-w-2xl mx-auto pb-10"
      >
        LogiTrack es una plataforma diseñada para optimizar la gestión de
        encomiendas, permitiendo registrar, controlar y rastrear envíos con
        eficiencia, seguridad y total transparencia.
      </p>

      <!-- CTA -->
      <div class="flex justify-center">
        <RouterLink
          :to="authLink"
          class="flex items-center justify-center h-14 px-10
                 rounded-xl bg-primary text-white text-lg font-bold
                 transition-all hover:brightness-110 shadow-lg shadow-primary/30"
        >
          Empezar ahora
        </RouterLink>
      </div>
    </section>

    <!-- IMAGEN HERO -->
    <section class="max-w-5xl mx-auto px-6 pb-28">
      <div
        class="rounded-2xl overflow-hidden shadow-2xl
               border border-slate-200 dark:border-slate-800"
      >
        <img
          src="/logistica.png"
          alt="Plataforma logística"
          class="w-full h-[420px] object-cover"
        />
      </div>
    </section>

    <!-- OBJETIVOS -->
    <section class="bg-background-light dark:bg-background-dark py-24">
      <div
        class="max-w-6xl mx-auto px-6 grid grid-cols-1 lg:grid-cols-2 gap-16 items-center"
      >
        <!-- Imagen -->
        <div>
          <img
            src="/Objetivos.png"
            alt="Gestión logística"
            class="rounded-2xl shadow-xl w-full object-contain"
          />
        </div>

        <!-- Texto -->
        <div>
          <h2
            class="text-3xl font-extrabold text-primary mb-10 text-center lg:text-left"
          >
            Objetivos Generales y Propósitos
          </h2>

          <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
            <div class="objective-card">
              <h3>Solución tecnológica</h3>
              <p>
                Plataforma robusta y accesible para registrar, rastrear y
                gestionar encomiendas en un solo lugar.
              </p>
            </div>

            <div class="objective-card">
              <h3>Optimización</h3>
              <p>
                Automatización de procesos logísticos y seguimiento en tiempo
                real de cada envío.
              </p>
            </div>

            <div class="objective-card">
              <h3>Transparencia</h3>
              <p>
                Información clara y actualizada sobre el estado de las
                encomiendas en todo momento.
              </p>
            </div>

            <div class="objective-card">
              <h3>Seguridad</h3>
              <p>
                Protección de datos y control seguro de la información de
                usuarios y envíos.
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref, onMounted } from "vue";

const authLink = ref("/register");

onMounted(() => {
  const token = localStorage.getItem("token");
  if (token) {
    authLink.value = "/encomiendas/nueva";
  }
});
</script>

<style scoped>
.objective-card {
  background: white;
  padding: 1.25rem;
  border-radius: 1rem;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s ease;
}

.objective-card:hover {
  transform: translateY(-6px);
}

.objective-card h3 {
  color: #2f7f34;
  font-weight: 700;
  margin-bottom: 0.4rem;
}

.objective-card p {
  font-size: 0.95rem;
  color: #444;
  line-height: 1.45;
}
</style>
