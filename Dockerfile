# Base images 基础镜像
FROM java:8
# 挂载目录
VOLUME /tmp
# 文件放在当前目录下，拷过去会自动解压
ADD SubsidizeSystem-0.0.1-SNAPSHOT.jar /app.jar
# 配置容器，使其可执行化。配合CMD可省去"application"，只使用参数。
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]