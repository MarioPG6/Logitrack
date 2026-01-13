import api from "./api";

export async function login(credentials) {
  const response = await api.post("/auth/login", credentials);
  localStorage.setItem("token", response.data.token);
  return response.data;
}

export async function register(form) {
  const response = await api.post("/auth/register", form);
  return response.data;
}

export async function getCurrentUser() {
  const response = await api.get("/auth/info");
  return response.data;
}

