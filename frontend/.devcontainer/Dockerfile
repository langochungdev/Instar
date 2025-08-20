# Node.js 22.14.0 (current stable)
FROM node:22.14.0

# Thư mục làm việc
WORKDIR /workspace

# Cài tool cơ bản
RUN apt-get update && apt-get install -y git curl vim

# Copy package.json trước để cài dependency
COPY package*.json ./

RUN npm install

# Port mặc định của Vite
EXPOSE 5173

CMD ["npm", "run", "dev"]

