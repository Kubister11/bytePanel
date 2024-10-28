import axios from "axios";

const API_URL = "http://localhost:5631";

function fetchServers() {
  const response = axios.get(API_URL + "/api/servers", {
    headers: {
      'Accept': 'application/json',
      "Content-Type": "application/json"
    }
  }).then(data => {
    console.log(data);
    console.log(data.data);
  }).catch(error => {
    console.error("Error fetching servers:", error.message);
    if (error.response) {
      console.log("Server responded with status:", error.response.status);
    } else if (error.request) {
      console.log("No response received:", error.request);
    } else {
      console.log("Error setting up request:", error.message);
    }
  });
}

export default fetchServers;
