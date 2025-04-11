# === STAGE 1: Build Angular App ===
FROM node:20-alpine AS build

WORKDIR /app

# Salin package info terlebih dulu untuk cache efisiensi
COPY package.json package-lock.json ./
RUN npm install

# Salin semua source code
COPY . .

# Build dengan konfigurasi production
RUN npm run build -- --configuration production

# === STAGE 2: Serve via NGINX ===
FROM nginx:alpine

# Hapus default config nginx dan salin custom config
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Salin hasil build Angular ke folder yang di-serve oleh nginx
COPY --from=build /app/dist/my-simple-app-frontend/browser /usr/share/nginx/html

# Expose port 80 untuk container
EXPOSE 80

# Jalankan nginx di foreground
CMD ["nginx", "-g", "daemon off;"]