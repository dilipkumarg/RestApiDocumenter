RESTApi Documenter
===========================
Purpose
--------
To document a given jax-rs api service 

Modules:
<br>
Core
=====
1. This module serves the core functionality of parsing the given classpaths that contain list of classes
   and jars where it will search for @path annotations.
<br>
Apiservice
===========
1. This is a web module which will be used for documenting the RestApi as a web project.<br>

SETUP:<br>
1. Copy the core jar and apiService jar into the lib folder of your project<br>
2. Set the properties base.path.url and jar.files.path in ApiDoumenterConfig.properties file 
<br>
ApiDocumenter-Plugin
====================
1. This module can be used as a plugin with any existing jax-rs rest api. The purpose of this plugin is to parse all the class files in the target location,jar files given in the configuration and generate a html file conating the REST Api Documentation.

SETUP:<br>
1.Add the plugin to the existing pom of your project as :<br>

<!-- Optional folder name if not given system will generate it by name ProjectVersion--><br>

`<finalName>RESTfulExample</finalName>
<plugin>
<groupId>com.imaginea.documenter</groupId>
<artifactId>apidocumenter-maven-plugin</artifactId>
<version>1.0-SNAPSHOT</version>
<executions>
<execution>
<id>execution1</id>
<phase>install</phase>
<configuration>

<basePath>IS A STATIC WEB URL TO BE DISPLAYED IN THE HTML GENERATED </basePath>
<!-- Optional if not given the html file will be created inside the project build directory-->
<docOutDir>THE DIRECTORY WHERE THE OUTPUT HTML WILL BE WRITTEN</docOutDir>
<!-- Optional folder path if not given inly the target/classes folder will be scanned-->
<includeJarFolders>
<includeJarFolder>JAR LOCATION TO BE SCANNED FOR @Path ANNOTATIONS </includeJarFolder>
<includeJarFolder>JAR LOCATION TO BE SCANNED FOR @Path ANNOTATIONS </includeJarFolder>
</includeJarFolders>
</configuration>
<goals>
<goal>document</goal>
</goals>
</execution>
</executions>
</plugin>'
