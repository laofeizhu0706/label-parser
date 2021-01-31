#!/usr/bin/env bash

function log() {
    echo "======================= $1 ========================="
}

JAVA_OPS="-Xms1024m -Xmx1024m"
NGINX_STATIC_DATA_PATH = "/data"
current_path = `pwd`
log "start"
git pull origin master
mvn clean install -Dmaven.test.skip=true
# 部署 label-service
log "start label-service.jar"
cp ./label-service/target/label-service-1.0.jar .
jar_name=label-service-1.0
pid=$(ps -ef|grep ${jar_name}|grep -v grep|awk '{print $2}')
if [ -n "${pid}" ];then
    kill -9 $pid
    print_log "存在${jar_name}的进程 ${pid}"
fi
nohup java -jar -server ${JAVA_OPS} ${jar_name}.jar --spring.profiles.active=${environment} >/logs/${jar}-run.log 2>&1 &
log "end label-service.jar"

# 部署 label-admin-web
log "start label-admin-web.jar"
cp ./label-admin-parent/label-admin-web/target/label-admin-web.jar .
jar_name=label-admin-web
pid=$(ps -ef|grep ${jar_name}|grep -v grep|awk '{print $2}')
if [ -n "${pid}" ];then
    kill -9 $pid
    print_log "存在${jar_name}的进程 ${pid}"
fi
nohup java -jar -server ${JAVA_OPS} ${jar_name}.jar --spring.profiles.active=${environment} >/logs/${jar}-run.log 2>&1 &
log "end label-admin-web.jar"


# 部署  label-admin-vue
log "start label-admin-vue"
cd ./label-admin-parent/label-admin/
yarn install
yarn build
cp dist ${NGINX_STATIC_DATA_PATH}/
log "end label-admin-vue"


log "finish"