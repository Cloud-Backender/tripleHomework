FROM mysql:latest

ADD ./mysql-init /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD admin
EXPOSE 3306
