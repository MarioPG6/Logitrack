
-- Desactivar verificación de claves foráneas temporalmente
SET session_replication_role = replica;

-- Eliminar las tablas si existen
DROP TABLE IF EXISTS public.encomiendas CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

-- Restaurar comportamiento normal
SET session_replication_role = DEFAULT;

DO $$
BEGIN
    RAISE NOTICE 'Tablas eliminadas correctamente (users, encomiendas)';
END$$;
