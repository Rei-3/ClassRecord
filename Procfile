release: ./create_keys.sh && java -jar target/ClassRecord-0.0.2-SNAPSHOT.jar --spring.jpa.hibernate.ddl-auto=update
web: java -Dspring.profiles.active=prod -jar target/class-record-backend-app.jar