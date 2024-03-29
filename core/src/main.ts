import {NestFactory} from "@nestjs/core";
import {AppModule} from "./app.module";
import {DocumentBuilder, SwaggerModule} from "@nestjs/swagger";
import {ValidationPipe, VersioningType} from "@nestjs/common";
import {AppExceptionsFilter} from "~shared/utils/exceptions.filter";
import {MicroserviceOptions, Transport} from "@nestjs/microservices";

async function start() {
    const PORT = process.env.PORT || 3000;
    const app = await NestFactory.create(AppModule, {
        logger: ["error", "warn", "log", "debug"],
    });

    const microservice = app.connectMicroservice<MicroserviceOptions>({
        transport: Transport.KAFKA,
        options: {
            client: {
                brokers: ["kafka:19092"],
            },
            consumer: {
                groupId: "operations-consumer"
            },
        }
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
        .setTitle("Core service")
        .setVersion("1.0.0")
        .addBearerAuth()
        .build();

    const document = SwaggerModule.createDocument(app, config);
    SwaggerModule.setup("/docs", app, document);

    await app.startAllMicroservices();
    await app.listen(PORT, () => console.log(`Server started on port ${PORT}`));
}

start();


