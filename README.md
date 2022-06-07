# Multiplayer-Blogging

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

