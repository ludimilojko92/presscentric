User Provider application

### Build and run
Prerequisites installed:
Maven 3.9.7, Java 17, Tomcat 10

Navigate to project root where pom.xml file is. 
From terminal run command 'mvn clean install' and wait process to finish.
Open target folder and copy userprovider.war file to <tomcat>/webapps folder.
Navigate to <tomcat>/bin folder and run 'sh catalina.sh run' command to deploy application.

After application is started open web browser and go to http://localhost:8080/userprovider/graphiql