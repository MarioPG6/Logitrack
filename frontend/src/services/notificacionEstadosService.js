/**
 * Servicio para notificar cambios de estado de una encomienda
 * Por ahora usa alert, pero queda listo para:
 * - Toasts
 * - Emails
 * - WebSockets
 * - Push notifications
 */

export function notificarCambioEstado(estado) {
  let mensaje = "";

  switch (estado) {
    case "ESPERANDO RECOLECCIÓN":
      mensaje = "📦 Tu encomienda está esperando recolección.";
      break;

    case "EN PROCESO":
      mensaje = "🚚 Tu encomienda está siendo procesada.";
      break;

    case "EN CAMINO":
      mensaje = "🛣️ Tu encomienda va en camino.";
      break;

    case "ENTREGADO":
      mensaje = "✅ Tu encomienda fue entregada con éxito.";
      break;

    case "CANCELADO":
      mensaje = "🚫 La encomienda ha sido cancelada.";
      break;

    default:
      mensaje = `ℹ️ Estado actualizado: ${estado}`;
  }

  // Notificación básica (puede reemplazarse por toast)
  alert(mensaje);

  // Log útil para debugging
  console.log("🔔 Notificación enviada:", mensaje);
}
