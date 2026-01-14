<template>
  <div class="linea-tiempo">
    <h3>📍 Estado del envío</h3>

    <ul>
      <li
        v-for="(item, index) in estados"
        :key="index"
        :class="{
          activo: index <= estadoActualIndex,
          cancelado: estado === 'CANCELADO'
        }"
      >
        <span class="punto"></span>
        <span class="texto">{{ item }}</span>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { computed } from "vue";

const props = defineProps({
  estado: {
    type: String,
    required: true,
  },
});

/**
 * Estados del flujo de la encomienda
 * Deben coincidir con los que maneja el backend
 */
const estados = [
  "ESPERANDO RECOLECCIÓN",
  "EN PROCESO",
  "EN CAMINO",
  "ENTREGADO",
];

/**
 * Índice del estado actual
 */
const estadoActualIndex = computed(() => {
  return estados.indexOf(props.estado);
});
</script>

<style scoped>
.linea-tiempo {
  margin-top: 30px;
  padding: 15px;
  border-left: 3px solid #e0e0e0;
}

.linea-tiempo h3 {
  margin-bottom: 15px;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  color: #999;
}

li.activo {
  color: #2e7d32;
  font-weight: bold;
}

li.cancelado {
  color: red;
}

.punto {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: currentColor;
  margin-right: 10px;
}

.texto {
  font-size: 14px;
}
</style>
