<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Administrar Usuarios</h2>

    <table class="min-w-full bg-white border border-gray-300 shadow-md rounded-lg">
      <thead>
        <tr class="bg-gray-100 text-left">
          <th class="py-2 px-4 border">ID</th>
          <th class="py-2 px-4 border">Nombre</th>
          <th class="py-2 px-4 border">Apellido</th>
          <th class="py-2 px-4 border">Email</th>
          <th class="py-2 px-4 border">Teléfono</th>
          <th class="py-2 px-4 border">Rol</th>
          <th class="py-2 px-4 border">Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id" class="hover:bg-gray-50">
          <td class="py-2 px-4 border">{{ user.id }}</td>
          <td class="py-2 px-4 border">{{ user.firstname }}</td>
          <td class="py-2 px-4 border">{{ user.lastname }}</td>
          <td class="py-2 px-4 border">{{ user.email }}</td>
          <td class="py-2 px-4 border">{{ user.phone }}</td>
          <td class="py-2 px-4 border">{{ user.role }}</td>
          <td class="py-2 px-4 border text-center">
            <button 
              class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
              @click="deleteUser(user.id)"
            >
              Eliminar
            </button>
          </td>
        </tr>
      </tbody>
    </table>
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
}
</style>