FROM java:8
VOLUME /usr/app/project/
ADD msb-dongbao-portal-web.jar dongbao-portal-web.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/project/dongbao-portal-web.jar"]
# 指定维护者的名字
MAINTAINER msb-pro