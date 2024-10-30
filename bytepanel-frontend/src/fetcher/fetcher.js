import axios from "axios";

const API_URL = "http://localhost:5631";

export async function fetchServers() {
  return await axios.get(API_URL + "/api/servers", {
    headers: {
      'Accept': 'application/json',
      "Content-Type": "application/json"
    }
  })
}

export async function fetchServerInfo(id) {
  return await axios.get(API_URL + "/api/server", {
    headers: {
      'Accept': 'application/json',
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      id: id
    })
  })
}

