import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8082', // Spring Boot backend
        changeOrigin: true,
        secure: false,
      },
      // Mirrors the /iiif/ block in nginx.conf so artwork images work the same
      // way in dev as in Docker.
      '/iiif': {
        target: 'https://www.artic.edu',
        changeOrigin: true,
        headers: {
          Referer: 'https://www.artic.edu/',
        },
      }
    }
  }
})
