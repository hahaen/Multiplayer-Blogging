# Multiplayer-Blogging

创建数据库MySQL
```docker
docker run --name blog -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=blog mysql
```

jenkins
```docker
docker run --name blog-jenkins -p 8080:8080 -v E:/myself/Multiplayer-Blogging/jenkins-data:/var/jenkins_home jenkins/jenkins
```

