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
      // Mirrors the /img/<source>/ blocks in nginx.conf so artwork images work the
      // same way in dev as in Docker.
      '/img/artic': {
        target: 'https://www.artic.edu',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/img\/artic/, '/iiif/2'),
        headers: {
          Referer: 'https://www.artic.edu/',
        },
      },
      '/img/smk': {
        target: 'https://iip.smk.dk',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/img\/smk/, '/iiif/jp2'),
      }
    }
  }
})
