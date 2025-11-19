
const mensajes = {
  CREADO: "🟦 Tu pedido ha sido creado.",
  "EN TRÁNSITO": "🚚 Tu pedido está en camino.",
  ENTREGADO: "📦 Tu pedido ha sido entregado.",
  RETRASADO: "⚠️ Tu pedido está retrasado, estamos solucionándolo.",
};

export function notificarCambioEstado(estado) {
  const mensaje = mensajes[estado];
  if (mensaje) {
    // Aquí puedes reemplazar alert por un toast o una API real
    alert(mensaje);
  }
}
