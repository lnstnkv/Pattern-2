#dev
FROM node:18-alpine As development

RUN mkdir -p /home/node

WORKDIR /home/node

COPY --chown=node:node package*.json ./

RUN npm install

COPY --chown=node:node . .

USER node

#build
FROM node:18-alpine As build

WORKDIR /home/node

COPY --chown=node:node package*.json ./

COPY --chown=node:node --from=development /home/node/node_modules ./node_modules

COPY --chown=node:node . .

RUN npm run build

RUN npm ci --only=production && npm cache clean --force

USER node

#prod
FROM node:18-alpine As production

COPY --chown=node:node --from=build /home/node/node_modules ./node_modules
COPY --chown=node:node --from=build /home/node/dist ./dist

CMD [ "node", "dist/main.js" ]
