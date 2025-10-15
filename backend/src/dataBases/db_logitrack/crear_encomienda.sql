
CREATE TABLE IF NOT EXISTS public.encomiendas
(
    id SERIAL PRIMARY KEY,
    cedula VARCHAR(255) NOT NULL,
    ciudad_destino VARCHAR(255) NOT NULL,
    ciudad_origen VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    forma_pago VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL,
    tiempo VARCHAR(255) NOT NULL,
    tipo_producto VARCHAR(255) NOT NULL,
    valor_declarado DOUBLE PRECISION NOT NULL,
    user_id INTEGER NOT NULL,
    estado VARCHAR(255) DEFAULT 'EN PROCESO',
    CONSTRAINT fk_encomienda_user FOREIGN KEY (user_id)
        REFERENCES public.users (id) ON DELETE CASCADE
);

ALTER TABLE public.encomiendas OWNER TO postgres;


INSERT INTO public.encomiendas (
    cedula, ciudad_destino, ciudad_origen, direccion, email,
    forma_pago, nombre, telefono, tiempo, tipo_producto,
    valor_declarado, user_id, estado
)
VALUES (
    '123456789',
    'Medellín',
    'Bogotá',
    'Carrera 10 #25-36',
    'cliente@example.com',
    'Efectivo',
    'Juan Pérez',
    '3001112233',
    '2 días',
    'Documentos',
    250000,
    (SELECT id FROM public.users WHERE role = 'CLIENTE' LIMIT 1),
    'EN PROCESO'
);

SELECT 
    e.id AS id_encomienda,
    e.nombre AS nombre_remitente,
    e.ciudad_origen,
    e.ciudad_destino,
    e.tipo_producto,
    e.valor_declarado,
    e.estado,
    u.firstname AS usuario_nombre,
    u.role AS rol_usuario
FROM public.encomiendas e
JOIN public.users u ON e.user_id = u.id
ORDER BY e.id ASC;
