
CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS public.users CASCADE;

CREATE TABLE IF NOT EXISTS public.users
(
    id SERIAL PRIMARY KEY,
    direction VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    role VARCHAR(255) CHECK (role IN ('CLIENTE', 'ADMINISTRADOR', 'TRABAJADOR'))
);

ALTER TABLE public.users OWNER TO postgres;

CREATE OR REPLACE FUNCTION encrypt_user_password()
RETURNS TRIGGER AS $$
BEGIN
    -- Si la contraseña NO está cifrada, la ciframos con bcrypt
    IF (NEW.password IS NOT NULL AND NEW.password NOT LIKE '$2a$%') THEN
        NEW.password := crypt(NEW.password, gen_salt('bf'));
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_encrypt_password
BEFORE INSERT OR UPDATE OF password
ON public.users
FOR EACH ROW
EXECUTE FUNCTION encrypt_user_password();

INSERT INTO public.users (direction, email, firstname, lastname, password, phone, role)
VALUES 
('Calle 123', 'cliente@example.com', 'Juan', 'Pérez', 'qwerty', '3001112233', 'CLIENTE'),
('Carrera 45', 'admin@example.com', 'Ana', 'Gómez', 'qwerty', '3004445566', 'ADMINISTRADOR'),
('Avenida 10', 'trabajador@example.com', 'Carlos', 'López', 'qwerty', '3007778899', 'TRABAJADOR');

SELECT id, email, firstname, lastname, password, role
FROM public.users
ORDER BY id ASC;

SELECT *
FROM public.users
WHERE email = 'cliente@example.com'
  AND password = crypt('qwerty', password);
