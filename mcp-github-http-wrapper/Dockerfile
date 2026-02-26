FROM node:20-alpine

WORKDIR /app

# Install wrapper deps
COPY package.json package-lock.json* ./
RUN npm install --omit=dev

# Install the GitHub MCP server globally so npx finds it instantly
RUN npm install -g @modelcontextprotocol/server-github

# Copy wrapper source
COPY src ./src

EXPOSE 3333
ENV PORT=3333
ENV MCP_PATH=/mcp

CMD ["npm","start"]