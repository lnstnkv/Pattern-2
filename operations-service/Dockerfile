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
