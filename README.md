
**多人共享博客平台项目**

**预览地址：**[http://150.158.194.237:5001/](http://150.158.194.237:5001/)

创建数据库MySQL
```docker

docker run --name blog -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=blog mysql

```


flyway

```text

mvn flyway:clean

```

```text

mvn flyway:migrate

```

jenkins
```docker

docker run --name blog-jenkins -p 8081:8080 -v E:/myself/Multiplayer-Blogging/jenkins-data:/var/jenkins_home jenkins/jenkins

```

```
java -Dserver.port=5001 -jar Multiplayer-Blogging-0.0.1-SNAPSHOT.jar
```

