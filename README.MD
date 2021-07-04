# Choonz

![Badge](http://buildstatus.jenkins.alihamza.co.uk/buildStatus/icon?job=choonz-redev)
![Quality Gate Status](http://badges.sonarqube.alihamza.co.uk/api/project_badges/measure?project=com.qa%3AChoonz&metric=alert_status)
![Bugs](http://badges.sonarqube.alihamza.co.uk/api/project_badges/measure?project=com.qa%3AChoonz&metric=bugs)
![Code Smells](http://badges.sonarqube.alihamza.co.uk/api/project_badges/measure?project=com.qa%3AChoonz&metric=code_smells#)
![Coverage](http://badges.sonarqube.alihamza.co.uk/api/project_badges/measure?project=com.qa%3AChoonz&metric=coverage)


## Intoduction

Choonz is a full stack web app music management tool that allows the user to create, read, update and delete tracks, artists, albums, genres and playlists.

The backend is a rest API written in Java with Spring Boot. Frontend is written in HTML, CSS and JavaScript.

Automated tests have been written for this program, with a test coverage of above 80%. Unit and Integration testing done with Mockito and JUnit. User Acceptance tests also carried out in JMeter and Selenium.

## Requirements

To compile and run the code:

Maven - [Apache Maven](https://maven.apache.org/)  
Java - [Java 14](https://www.oracle.com/uk/java/technologies/javase-downloads.html)  
MySQL, for production use - [MySQL 8](https://dev.mysql.com/downloads/installer/)

## Installation

Method 1: Use precompiled binary  
Download latest release from GitHub packages, and execute as normal Java file. Ensure MySQL server is running prior to execution, see [Properties](Properties) for more information.

Method 2: Compile source  
Clone repository, and compile using `mvn clean package`.  
If tests are failing, skip with `mvn clean package -Dmaven.test.skip=true`

## Properties

If compiling from source, properties files should be located in `src/main/resources`.  
If downloading pre-compiled binary, create folder `config/` in the same directory as the executable, and Spring Boot will automagically pick up the new properties files. If this isn't possible, Spring uses 3 more directories in this order of preference:

- A `/config` subdirectory of the current directory
- The current directory
- A classpath `/config` package
- The classpath root

### Explanation of properties

- `spring.profiles.active=test`  
  Indicates which properties file to use. If set to _test_, `application-test.properties` will be used. If set to _production_, `application-production.properties` will be used.

- `spring.jpa.generate-ddl=true`  
  Generates SQL creation queries. Needed to automtically populate database.

- `spring.jpa.hibernate.ddl-auto=create`  
  Auto creates tables in database. Needed to automatically populate database.

- `spring.datasource.url=jdbc:mysql://localhost:3306/choonz`  
  Which database to use. This can be set to MySQl for production, or H2 for an in-memory testing database. Ensure that database URL is correct.

- `spring.datasource.username=root`  
  Database username.

- `spring.datasource.password=root`  
  Database password.

- `server.port=8082`  
  Which port to start the API and frontend on. Ensure this port is not being used prior to executing.

- `spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`  
  Which database driver to use, most likely JDBC.

- `spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect`  
  Which database dialect to use, most likely MYSQL8.

- `spring.h2.console.enabled=true`, `spring.h2.console.path=/h2`  
  H2-specific settings, to enable the H2 console and which location it should be accessible at.

## Testing

### Functional tests

All backend classes are tested. Unit tests have been written for the Services, Controllers, Mappers, DTOs and Domain classes.  
Frontend tests have been written with Selenium for the sign up and login page.  
Current test coverage is at 87.7%.

### Non-fuctional tests

Load, Spike, Stress, and Soak tests have been written and completed with [Apache JMeter](https://jmeter.apache.org/).  
JMeter test files available at `src/test/resources/jmeter`

### Run tests

Functional tests can be run with `mvn test`  
Non-functional tests can be run with JMeter. Best results are achieved from the command line. Run with `jmeter -n -t <testfile.jmx> -l <log.txt>`. Ensure program is running before executing tests.

### Test Reports

All test reports are located in `reports`.

## Plans

ERD 1st and 2nd iterations, UML, and Risk Matrix can be found in `docs`.

## Authors

- Ali Hamza Mohammed - [alihamzamohammed](https://github.com/alihamzamohammed)
- Peter Colquhoun - [peter5600](https://github.com/peter5600)
- Chris Yiangou - [ChrisQA-GB](https://github.com/ChrisQA-GB)
- Ifan Clarke - [IfanClarke](https://github.com/IfanClarke)

### With thanks to

QA Academy Training Team:

- **Client** - [**Angelica Charry**](https://github.com/acharry) - **Software Delivery Manager**
- **Product Owner** - [**Nick Johnson**](https://github.com/nickrstewarttds) - **Initial work (backend & frontend development, specification)**
- **Product Owner** - [**Edward Reynolds**](https://github.com/Edrz-96) - **Initial work (testing, specification)**
- [**Jordan Harrison**](https://github.com/JHarry444) - **General Java wizardry**
- [**Alan Davies**](https://github.com/MorickClive)
- [**Savannah Vaithilingham**](https://github.com/savannahvaith)
- [**Vinesh Ghela**](https://github.com/vineshghela)
- [**Piers Barber**](https://github.com/PCMBarber)

## License

Licensed under the MIT agreement
