# Build stage
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /workspace
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
ARG DEPENDENCY=/workspace/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib ./lib/
COPY --from=builder ${DEPENDENCY}/META-INF ./META-INF/
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes ./
ENTRYPOINT ["java", "-cp", ".:lib/*", "com.assookkaa.ClassRecord"]