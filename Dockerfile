# 基于官方OpenJDK镜像
FROM openjdk:11-jre-slim

# 指定维护者信息
LABEL authors="reone"

# 在镜像中创建一个目录存放我们的应用
VOLUME /tmp

# 将jar包添加到容器中并更名为app.jar
ADD ./*.jar app.jar

# 暴露容器内的端口给外部访问
EXPOSE 8080

# 定义环境变量
ENV JAVA_OPTS=""

# 在容器启动时运行jar包
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar