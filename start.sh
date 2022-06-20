#!/bin/sh

docker build -t triple-mysql .
docker run -d -p 3306:3306 --name triple-mysql triple-mysql:latest
