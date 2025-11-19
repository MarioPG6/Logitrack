export async function generarOrdenPagoPDF(encomienda) {
  if (!encomienda) {
    alert("No hay datos de la encomienda para generar la orden de pago.");
    return;
  }

  // Import dinámico (optimiza el bundle)
  const { jsPDF } = await import("jspdf");

  const doc = new jsPDF();

  doc.setFontSize(16);
  doc.text("ORDEN DE PAGO - LOGITRACK", 70, 20);

  doc.setFontSize(12);
  doc.text(`Fecha de emisión: ${new Date().toLocaleDateString()}`, 20, 35);

  doc.text("Datos del Pedido:", 20, 50);
  doc.text(`Número de Guía: ${encomienda.id || "N/A"}`, 20, 60);
  doc.text(`Remitente: ${encomienda.nombre}`, 20, 70);
  doc.text(`Cédula: ${encomienda.cedula}`, 20, 80);
  doc.text(`Correo: ${encomienda.email}`, 20, 90);
  doc.text(`Dirección: ${encomienda.direccion}`, 20, 100);
  doc.text(`Teléfono: ${encomienda.telefono}`, 20, 110);
  doc.text(`Tipo de Producto: ${encomienda.tipoProducto}`, 20, 120);
  doc.text(`Ciudad Origen: ${encomienda.ciudadOrigen}`, 20, 130);
  doc.text(`Ciudad Destino: ${encomienda.ciudadDestino}`, 20, 140);
  doc.text(`Forma de Pago: ${encomienda.formaPago}`, 20, 150);
  doc.text(`Valor Declarado: ${encomienda.valorDeclarado}`, 20, 160);
  doc.text(`Estado Actual: ${encomienda.estado}`, 20, 170);

  doc.text("Datos del Pago:", 20, 185);
  doc.text(`Tarifa: __________________________`, 20, 195);
  doc.text(`Método de Pago: __________________`, 20, 205);
  doc.text(`Estado del Pago: __________________`, 20, 215);
  doc.text(`Observaciones: ____________________`, 20, 225);

  doc.text("Firmas:", 20, 245);
  doc.text("Firma del Remitente: ___________________________", 20, 255);
  doc.text("Firma del Operador: ____________________________", 20, 265);

  doc.setFontSize(10);
  doc.text(
    "Gracias por confiar en LogiTrack. Su envío está siendo procesado bajo nuestras políticas de seguridad y trazabilidad.",
    20,
    285,
    { maxWidth: 170 }
  );

  doc.save(`OrdenPago_${encomienda.id || "Encomienda"}.pdf`);
}
