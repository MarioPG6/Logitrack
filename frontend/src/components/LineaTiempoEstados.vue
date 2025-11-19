<template>
  <div class="linea-tiempo">
    <h3>📦 Seguimiento del Pedido</h3>

    <div class="estados">
      <div
        v-for="etapa in etapas"
        :key="etapa.nombre"
        class="estado"
      >
        <div
          class="punto"
          :class="{ activo: etapa.nombre === estadoNormalizado }"
        ></div>

        <span
          class="texto"
          :class="{ activoTexto: etapa.nombre === estadoNormalizado }"
        >
          {{ etapa.label }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  estado: String
});

// Normalizamos el estado para que coincida
const estadoNormalizado = computed(() => props.estado?.toUpperCase() || "");

const etapas = [
  { nombre: "CREADO", label: "CREADO" },
  { nombre: "EN TRÁNSITO", label: "EN TRÁNSITO" },
  { nombre: "ENTREGADO", label: "ENTREGADO" },
  { nombre: "RETRASADO", label: "RETRASADO" }
];
</script>

<style scoped>
.linea-tiempo {
  background: #f7f7f7;
  padding: 20px;
  border-radius: 12px;
  text-align: center;
}

.estados {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}

.estado {
  text-align: center;
  width: 120px;
}

.punto {
  width: 18px;
  height: 18px;
  background: #ccc;
  border-radius: 50%;
  margin: auto;
}

.punto.activo {
  background: #007bff;
  box-shadow: 0 0 10px #007bff;
}

.texto {
  font-size: 12px;
  display: block;
  margin-top: 5px;
  color: #777;
}

.texto.activoTexto {
  font-weight: bold;
  color: #007bff;
}
</style>
