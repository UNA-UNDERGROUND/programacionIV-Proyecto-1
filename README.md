## Proyecto I - Programacion IV
### Banco PSB(Proyecto Sistema Bancario)

la base de datos se conecta con las credenciales proporcionadas en su archivo de manifiesto, localizado en

```
/backend/src/main/resources/configuraciones/credenciales-bd.properties

📦backend
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┗ 📂configuraciones
 ┃ ┃ ┃ ┃ ┗ 📜credenciales-bd.properties
 ┃ ┗ 📂test
 ┣ 📜.gitignore
 ┣ 📜nbactions.xml
 ┗ 📜pom.xml
```

puede modificarse este archivo para usar las credenciales correctas, o agregar
el usuario requerido mediante 

```sql
CREATE USER 'admin'@'%' IDENTIFIED BY 'changeme';
```
y otorgarle permisos
```sql
GRANT ALL PRIVILEGES ON banco.* TO 'admin'@'%';
```

Finalmente el Proyecto usa maven por lo que el solo genera su lista de 
dependencias y las agrega, las dependencias son las siguientes en caso de ser requeridas

- mysql-connector-java-8.0.18.jar  (Driver JDBC)
- protobuf-java-3.6.1.jar          (Requerido por JDBC)
- commons-lang-2.6.jar             (generacion de contraseñas)
- javaee-api-8.0.jar               (requerido por los jsp y servlets)

Notese que varias de estas dependencias son transitivas