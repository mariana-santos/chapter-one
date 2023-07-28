import axios from "axios";

const baseURL = 'https://chapteroneapi-1-b8568863.deta.app'

const api = axios.create({
    baseURL,
    timeout: 5000,
    resolve: {
        fallback: {
          "http": false
        }
      }
})

export default api