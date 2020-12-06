#!/usr/bin/env bash

docker stop ieseuropa
docker rm ieseuropa
docker rmi ieseuropa

cd /root/ieseuropa/project
bash -x gradlew buildDocker --no-daemon --stacktrace -Dprod -Pprofile=prod -x test -Dkotlin.compiler.execution.strategy=in-process -Dkotlin.incremental=false

docker logs -f ieseuropa