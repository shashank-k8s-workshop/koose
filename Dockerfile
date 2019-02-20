FROM openjdk:8-alpine

WORKDIR /usr/src/app
COPY . .

RUN ./gradlew build

CMD ["./gradlew", "bootRun"]