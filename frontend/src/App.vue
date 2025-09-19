<template>
  <div>
    <nav>
      <router-link to="/login"><button>Login</button></router-link>
      <router-link to="/register"><button>Registro</button></router-link>
      <router-link to="/profile"><button>Perfil</button></router-link>
    </nav>

    <router-view />
  </div>
</template>
