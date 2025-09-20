# LogiTrack

## Descripción
**LogiTrack** es un sistema desarrollado para la gestión y seguimiento de **logística de pedidos y entregas**, pensado para optimizar la conexión entre clientes, operadores y administradores.  
Permite una trazabilidad completa de las órdenes, mejorando la eficiencia en la administración de recursos y el control de la operación.

## Funcionalidades Principales
- **Gestión de órdenes:** Creación, seguimiento y actualización del estado de pedidos.  
- **Monitoreo en tiempo real:** Visualización del estado de entregas en curso.  
- **Panel administrativo:** Gestión de usuarios, roles y operaciones logísticas.  
- **Reportes:** Generación de reportes de desempeño y tiempos de entrega.  
- **Notificaciones:** Alertas para clientes y operadores sobre cambios en los estados de pedidos.  

## Tecnologías Utilizadas
- **Frontend:** Vue.js con Vite  
- **Backend:** Spring Boot (Java 17)  
- **Base de Datos:** PostgreSQL  
- **Herramientas adicionales:** Docker, Maven  

## Estructura del Proyecto
El proyecto está organizado en dos carpetas principales:
- **backend/** → Lógica de negocio y API REST con Spring Boot.  
- **frontend/** → Interfaz de usuario desarrollada en Vue.js.  

---

## Instalación y Configuración

### Requisitos Previos
Asegúrese de tener instalado lo siguiente:

- [Java 17+](https://adoptium.net/)  
- [Maven 3.9+](https://maven.apache.org/) (el proyecto incluye wrapper `mvnw`)  
- [Node.js (LTS)](https://nodejs.org/)  
- [npm](https://www.npmjs.com/) o [yarn](https://yarnpkg.com/)  
- [PostgreSQL 15+](https://www.postgresql.org/download/) o Docker  
- Git  

Verificar instalaciones:
```sh
java -version
mvn -v
node -v
npm -v
psql --version


