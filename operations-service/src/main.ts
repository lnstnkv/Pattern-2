import {NestFactory} from "@nestjs/core";
import {AppModule} from "./app.module";
import {DocumentBuilder, SwaggerModule} from "@nestjs/swagger";
import {ValidationPipe, VersioningType} from "@nestjs/common";
import * as process from "process";
import {AppExceptionsFilter} from "~shared/utils/exceptions.filter";

async function start() {
    console.log(process.env.PORT);
    const PORT = process.env.PORT || 3000;
    const app = await NestFactory.create(AppModule, {
        logger: ["error", "warn", "log", "debug"],
    });

    app.useGlobalPipes(new ValidationPipe({
      transform: true
    }));
    app.enableCors();
    app.useGlobalFilters(new AppExceptionsFilter());
    app.enableVersioning({
        type: VersioningType.URI,
    });

    const config = new DocumentBuilder()
        .setTitle("Operations service")
        .setVersion("1.0.0")
        .addBearerAuth()
        .build();

    const document = SwaggerModule.createDocument(app, config);
    SwaggerModule.setup("/docs", app, document);

    await app.listen(PORT, () => console.log(`Server started on port ${PORT}`));
}

start();
