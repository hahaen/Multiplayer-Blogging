# Multiplayer-Blogging

创建数据库
```
docker run --name blog -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=blog mysql
```