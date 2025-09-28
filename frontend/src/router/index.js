import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import ProfileView from "../views/ProfileView.vue";
import NuevaEncomiendaView from "../views/NuevaEncomiendaView.vue";
import MisEncomiendasView from "../views/MisEncomiendasView.vue";
import VerEncomiendaView from "../views/VerEncomiendaView.vue"; 

const routes = [
  {
    path: "/login",
    name: "Login",
    component: LoginView,
  },
  {
    path: "/register",
    name: "Register",
    component: RegisterView,
  },
  {
    path: "/profile",
    name: "Profile",
    component: ProfileView,
  },
  {
    path: "/encomiendas/nueva",
    name: "NuevaEncomienda",
    component: NuevaEncomiendaView,
  },
  {
    path: "/mis-encomiendas",
    name: "MisEncomiendas",
    component: MisEncomiendasView,
  },
  {
    path: "/encomiendas/:id",  
    name: "VerEncomienda",
    component: VerEncomiendaView,
    props: true, 
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
