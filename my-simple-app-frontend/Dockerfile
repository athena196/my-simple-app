# Angular Dockerfile
FROM node:20-alpine AS build

WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
COPY --from=build /app/dist/my-simple-app-frontend /usr/share/nginx/html