FROM mysql:8.1

ENV MYSQL_ROOT_PASSWORD=root_password
ENV MYSQL_DATABASE=db_clients
ENV MYSQL_USER=user_clients
ENV MYSQL_PASSWORD=password_clients

EXPOSE 3306
