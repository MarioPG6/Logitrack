#!/bin/bash
set -e

echo "Iniciando restauración de la base de datos PostgreSQL..."

echo "Base de datos: $POSTGRES_DB"
echo "Usuario: $POSTGRES_USER"

pg_restore \
  --verbose \
  --clean \
  --if-exists \
  --no-owner \
  --no-privileges \
  -U "$POSTGRES_USER" \
  -d "$POSTGRES_DB" \
  /backup

echo "Restauración completada correctamente"
