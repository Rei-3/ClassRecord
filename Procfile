release: ./create_keys.sh && java -jar target/class-record-backend-app.jar --spring.jpa.hibernate.ddl-auto=update
web: java -Dspring.profiles.active=prod -jar target/class-record-backend-app.jar