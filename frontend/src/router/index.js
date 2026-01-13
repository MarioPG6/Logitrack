import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/user/LoginView.vue";
import RegisterView from "../views/user/RegisterView.vue";
import ProfileView from "../views/user/ProfileView.vue";
import NuevaEncomiendaView from "../views/cliente/NuevaEncomiendaView.vue";
import MisEncomiendasView from "../views/cliente/MisEncomiendasView.vue";
import VerEncomiendaView from "../views/cliente/VerEncomiendaView.vue";
import HomeView from "../views/HomeView.vue";
import TrabajadorEncomiendasView from "../views/trabajador/TrabajadorEncomiendasView.vue";
import ReportarProblematica from "../views/trabajador/ReportarProblematica.vue";
import AdminUsuarios from "../views/administrador/AdminUsuarios.vue";
import DashboardView from "../views/administrador/DashboardView.vue";


const routes = [
  {
    path: "/",
    name: "Home",
    component: HomeView,
  },
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
  {
    path: "/trabajador/encomiendas",
    name: "EncomiendasDisponibles",
    component: TrabajadorEncomiendasView,
  },
  {
    path: "/trabajador/reportar-problema",
    name: "ReportarProblematica",
    component: ReportarProblematica,
  },
  {
    path: "/admin/usuarios",
    name: "AdminUsuarios",
    component: AdminUsuarios,
    meta: { requiresAuth: true, role: "ADMINISTRADOR" }
  },
  {
    path: "/admin/dashboard",
    name: "Dashboard",
    component: DashboardView,
    meta: { requiresAuth: true, role: "ADMINISTRADOR" }
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
