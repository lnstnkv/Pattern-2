#dev
FROM node:18-alpine As development

RUN mkdir -p /home/node/project

WORKDIR /home/node/project

COPY --chown=node:node package*.json ./

COPY --chown=node:node . .

RUN cp -r ./nest-shared ./../nest-shared \
    && cd ./.. \
    && chown node:node ./ \
    && cd ./nest-shared \
    && npm install \
    && cd ./../project \
    && chown node:node ./ \
    && rm -r ./nest-shared

RUN npm install

USER node

#build
#FROM node:18-alpine As build
#
#WORKDIR /home/node/project
#
#COPY --chown=node:node package*.json ./
#
#COPY --chown=node:node --from=development /home/node/node_modules ./node_modules
#
#COPY --chown=node:node . .
#
#RUN npm run build
#
#RUN npm ci --only=production && npm cache clean --force
#
#USER node
#
##prod
#FROM node:18-alpine As production
#
#COPY --chown=node:node --from=build /home/node/node_modules ./node_modules
#COPY --chown=node:node --from=build /home/node/dist ./dist
#
#CMD [ "node", "dist/main.js" ]
