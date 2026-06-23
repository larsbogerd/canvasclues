# syntax=docker/dockerfile:1

# --- Build stage: produce the static Vite bundle ---
FROM node:22-alpine AS build
WORKDIR /app

# Cache npm install: only package files invalidate this layer
COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# --- Runtime stage: nginx serves the SPA and proxies /api to the backend ---
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
