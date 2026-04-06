const API_BASE = 'http://localhost:3000/api'; // Adjust as needed

class Api {
  async get(endpoint: string) {
    const res = await fetch(`${API_BASE}${endpoint}`);
    if (!res.ok) throw new Error(`Failed to fetch ${endpoint}`);
    return res.json();
  }

  async post(endpoint: string, data: any) {
    const res = await fetch(`${API_BASE}${endpoint}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error(`Failed to post ${endpoint}`);
    return res.json();
  }

  // Add other methods as needed
}

export const api = new Api();