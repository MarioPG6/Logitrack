const API_URL = "http://localhost:8080/auth";

export async function login(credentials) {
  const response = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(credentials)
  });

  if (!response.ok) throw new Error("Error en login");

  const data = await response.json();
  localStorage.setItem("token", data.token);
  return data;
}

export async function register(form) {
  const response = await fetch(`${API_URL}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(form)
  });

  if (!response.ok) throw new Error("Error en registro");

  return await response.json();
}

export async function getCurrentUser() {
  const token = localStorage.getItem("token");
  if (!token) return null;

  const response = await fetch(`${API_URL}/info`, {
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });

  if (!response.ok) {
    throw new Error("Error obteniendo usuario");
  }

  return await response.json();
}
