<template>
  <div class="p-8">
    <!-- Título centrado -->
    <h2 class="text-3xl font-extrabold text-center mb-2 text-gray-800">
      👤 Panel de Administración de Usuarios
    </h2>

    <!-- Texto explicativo -->
    <p class="text-center text-gray-600 mb-6 max-w-2xl mx-auto">
      En este módulo el administrador puede visualizar todos los usuarios registrados en el sistema, 
      consultar su información básica y realizar acciones como la eliminación de cuentas.
    </p>

    <!-- Tabla de usuarios -->
    <div class="overflow-x-auto shadow-lg rounded-lg">
      <table class="min-w-full bg-white border border-gray-300 rounded-lg">
        <thead>
          <tr class="bg-blue-600 text-white text-left">
            <th class="py-3 px-4 border">ID</th>
            <th class="py-3 px-4 border">Nombre</th>
            <th class="py-3 px-4 border">Apellido</th>
            <th class="py-3 px-4 border">Email</th>
            <th class="py-3 px-4 border">Teléfono</th>
            <th class="py-3 px-4 border">Rol</th>
            <th class="py-3 px-4 border text-center">Acciones</th>
          </tr>
        </thead>
        <tbody>
          <!-- Si hay usuarios -->
          <tr 
            v-for="user in users" 
            :key="user.id" 
            class="hover:bg-gray-100 transition border-b"
          >
            <td class="py-3 px-4 text-gray-700">{{ user.id }}</td>
            <td class="py-3 px-4">{{ user.firstname }}</td>
            <td class="py-3 px-4">{{ user.lastname }}</td>
            <td class="py-3 px-4">{{ user.email }}</td>
            <td class="py-3 px-4">{{ user.phone }}</td>
            <td class="py-3 px-4 font-semibold">
              <span 
                :class="{
                  'text-green-600': user.role === 'ADMIN',
                  'text-blue-600': user.role === 'TRABAJADOR',
                  'text-gray-600': user.role === 'USER'
                }"
              >
                {{ user.role }}
              </span>
            </td>
            <td class="py-3 px-4 text-center">
              <button 
                class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded transition"
                @click="deleteUser(user.id)"
              >
                Eliminar
              </button>
            </td>
          </tr>

          <!-- Si no hay usuarios mostrar filas vacías -->
          <tr v-if="users.length === 0">
            <td colspan="7" class="text-center py-6 text-gray-500">
              No hay usuarios registrados en este momento
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminUsuarios",
  data() {
    return {
      users: []
    };
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get("http://localhost:8080/admin/users");
        this.users = response.data;
      } catch (error) {
        console.error("Error al cargar usuarios", error);
      }
    },
    async deleteUser(id) {
      if (confirm("¿Seguro que deseas eliminar este usuario?")) {
        try {
          await axios.delete(`http://localhost:8080/admin/users/${id}`);
          this.users = this.users.filter(user => user.id !== id);
        } catch (error) {
          console.error("Error al eliminar usuario", error);
        }
      }
    }
  },
  mounted() {
    this.fetchUsers();
  }
};
</script>

<style scoped>
table {
  border-collapse: collapse;
  width: 100%;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Encabezados más definidos */
th {
  font-weight: bold;
  text-align: left;
}

/* Fila par de color diferente */
tbody tr:nth-child(even) {
  background-color: #f9fafb;
}

/* Altura mínima para filas aunque estén vacías */
td {
  min-height: 50px;
}
</style>