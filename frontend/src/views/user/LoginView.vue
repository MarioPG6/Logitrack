<template>
  <div
    class="bg-background-light dark:bg-background-dark min-h-screen flex items-center justify-center p-4"
  >
    <div
      class="max-w-6xl w-full bg-white dark:bg-slate-800 rounded-2xl shadow-2xl overflow-hidden flex flex-col md:flex-row min-h-[700px]"
    >
      <!-- PANEL IZQUIERDO -->
      <div class="hidden md:flex md:w-1/2 relative overflow-hidden bg-primary">
        <img
          class="absolute inset-0 w-full h-full object-cover opacity-40 mix-blend-overlay"
          src="https://lh3.googleusercontent.com/aida-public/AB6AXuBIrCsOCAwtbz180b9izP4O9InZoX-u9ZVAx5Qq2s3nvyh7YBkC6KwVQIwLQI256pv0gSd355_1wjos4cd2_gB6PlJNzvwaC2-ANB6JC6iA5hcj6Yadi6GPQeAOQTkJhe2IuC1312ceI9HKwgWeFpjElZrAK3OAUIzXL6VUOWU0Flasf-6lTURqPcf1BJ_Fl4wMVXLn-6Sd6cQYwlbyi-c7Rh4Ilk9kvk8b7n78etxE_VLB9O5Ss-Xdf9zrPOrbmIgMX7BVWwsLvOUF"
          alt="Logistics"
        />

        <div
          class="relative z-10 w-full h-full p-12 flex flex-col justify-between text-white login-gradient"
        >
          <div class="flex items-center gap-2">
            <div class="bg-white/20 p-2 rounded-lg backdrop-blur-md">
              <span class="material-icons-outlined text-3xl">
                local_shipping
              </span>
            </div>
            <span class="text-xl font-bold">LogiTrack Enterprise</span>
          </div>

          <div>
            <h1 class="text-4xl font-bold mb-6">
              Streamline Your Global Logistics.
            </h1>
            <p class="text-lg text-emerald-50 mb-8">
              Real-time tracking and intelligent routing in one platform.
            </p>
          </div>

          <p class="text-sm opacity-60">© 2025 LogiTrack Inc.</p>
        </div>
      </div>

      <!-- FORMULARIO -->
      <div class="w-full md:w-1/2 p-8 lg:p-16 flex items-center">
        <div class="max-w-md mx-auto w-full">
          <div class="text-center mb-10">
            <!-- LOGO -->
            <img
              :src="logo"
              alt="LogiTrack Logo"
              class="mx-auto mb-6 h-20 w-auto object-contain"
            />

            <h2 class="text-3xl font-bold mb-2">Welcome Back</h2>
            <p class="text-slate-500">Please enter your details</p>
          </div>

          <form @submit.prevent="handleLogin" class="space-y-6">
            <!-- EMAIL -->
            <div>
              <label class="block text-sm font-semibold mb-2">
                Email Address
              </label>
              <div class="relative">
                <span
                  class="material-icons-outlined absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
                >
                  mail
                </span>
                <input
                  v-model="credentials.email"
                  type="email"
                  required
                  placeholder="name@company.com"
                  class="w-full pl-10 py-3 bg-slate-50 dark:bg-slate-900 border rounded-lg focus:ring-2 focus:ring-primary outline-none"
                />
              </div>
            </div>

            <!-- PASSWORD -->
            <div>
              <label class="block text-sm font-semibold mb-2"> Password </label>
              <div class="relative">
                <span
                  class="material-icons-outlined absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
                >
                  lock
                </span>

                <input
                  :type="showPassword ? 'text' : 'password'"
                  v-model="credentials.password"
                  required
                  placeholder="••••••••"
                  class="w-full pl-10 pr-12 py-3 bg-slate-50 dark:bg-slate-900 border rounded-lg focus:ring-2 focus:ring-primary outline-none"
                />

                <!-- BOTÓN OJO -->
                <button
                  type="button"
                  @click="showPassword = !showPassword"
                  class="absolute right-3 top-1/2 -translate-y-1/2 text-slate-400 hover:text-primary"
                >
                  <span class="material-icons-outlined">
                    {{ showPassword ? "visibility_off" : "visibility" }}
                  </span>
                </button>
              </div>
            </div>

            <!-- MENSAJE -->
            <p
              v-if="message"
              class="text-center text-sm"
              :class="{
                'text-green-600': success,
                'text-red-600': !success,
              }"
            >
              {{ message }}
            </p>

            <!-- BOTÓN -->
            <button
              type="submit"
              class="w-full bg-primary hover:bg-emerald-800 text-white font-bold py-3.5 rounded-lg transition-all shadow-lg flex justify-center items-center gap-2"
            >
              Log In
              <span class="material-icons-outlined">arrow_forward</span>
            </button>
          </form>

          <p class="mt-8 text-center text-slate-500">
            Don't have an account?
            <a class="font-bold text-primary hover:underline">
              Request Access
            </a>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { login } from "@/services/authService";
import logo from "@/assets/Logo.png";

const router = useRouter();

const credentials = reactive({
  email: "",
  password: "",
});

const showPassword = ref(false);
const message = ref("");
const success = ref(false);

async function handleLogin() {
  try {
    const res = await login(credentials);

    if (res?.token) {
      localStorage.setItem("token", res.token);
      success.value = true;
      message.value = "Login exitoso";
      router.push("/");
    } else {
      success.value = false;
      message.value = "Credenciales incorrectas";
    }
  } catch (error) {
    success.value = false;
    message.value = "Error en el servidor";
    console.error(error);
  }
}
</script>

<style scoped>
.login-gradient {
  background: linear-gradient(
    135deg,
    rgba(46, 125, 50, 0.9) 0%,
    rgba(27, 94, 32, 0.8) 100%
  );
}
</style>
