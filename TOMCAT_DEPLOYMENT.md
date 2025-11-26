# Tomcat 10 Deployment Guide

## Application Overview

**Caravan Research** is a Spring Boot 3.2 web application packaged as a WAR file for deployment on Apache Tomcat 10.x.

- **Technology**: Spring Boot 3.2.0 + Jakarta EE 10
- **Java Version**: 17
- **Tomcat Version**: 10.1.x or higher
- **Servlet API**: Jakarta Servlet 6.0
- **Database**: MySQL 8.0+

## Prerequisites

### 1. Install Java 17
```bash
# Verify Java version
java -version  # Should show version 17 or higher

# On macOS with Homebrew
brew install openjdk@17

# Set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### 2. Install Apache Tomcat 10
Download from: https://tomcat.apache.org/download-10.cgi

```bash
# Example installation on macOS
cd ~/Downloads
tar -xzf apache-tomcat-10.1.x.tar.gz
mv apache-tomcat-10.1.x /usr/local/tomcat10

# Set environment variables
export CATALINA_HOME=/usr/local/tomcat10
export PATH=$CATALINA_HOME/bin:$PATH
```

### 3. MySQL Database
Ensure MySQL is running with the `caravan_research` database:
```bash
mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS caravan_research;"
```

## Building the WAR File

```bash
# Navigate to project directory
cd /Users/oweinmann/Workspace/Campers

# Build the WAR file
mvn clean package -DskipTests

# WAR file location
ls -lh target/caravan-research.war
```

## Deploying to Tomcat 10

### Method 1: Manual Deployment (Recommended for Development)

1. **Stop Tomcat** (if running):
```bash
$CATALINA_HOME/bin/shutdown.sh
# Or on Windows: %CATALINA_HOME%\bin\shutdown.bat
```

2. **Copy WAR to webapps directory**:
```bash
cp target/caravan-research.war $CATALINA_HOME/webapps/
```

3. **Start Tomcat**:
```bash
$CATALINA_HOME/bin/startup.sh
# Or on Windows: %CATALINA_HOME%\bin\startup.bat
```

4. **Monitor deployment** (watch logs):
```bash
tail -f $CATALINA_HOME/logs/catalina.out
```

5. **Access application**:
   - URL: http://localhost:8080/caravan-research
   - API: http://localhost:8080/caravan-research/api

### Method 2: Tomcat Manager Web Application

1. **Configure Tomcat users** (`$CATALINA_HOME/conf/tomcat-users.xml`):
```xml
<tomcat-users>
  <role rolename="manager-gui"/>
  <role rolename="manager-script"/>
  <user username="admin" password="admin" roles="manager-gui,manager-script"/>
</tomcat-users>
```

2. **Restart Tomcat** to apply changes

3. **Access Tomcat Manager**:
   - URL: http://localhost:8080/manager/html
   - Login with credentials (admin/admin)

4. **Deploy WAR**:
   - Scroll to "WAR file to deploy" section
   - Click "Choose File" and select `caravan-research.war`
   - Click "Deploy"

### Method 3: Deploy as ROOT Application

To deploy at http://localhost:8080/ instead of http://localhost:8080/caravan-research:

1. **Remove default ROOT app**:
```bash
rm -rf $CATALINA_HOME/webapps/ROOT
```

2. **Rename and deploy**:
```bash
cp target/caravan-research.war $CATALINA_HOME/webapps/ROOT.war
```

3. **Start Tomcat** - application will be available at http://localhost:8080/

## Configuration

### Application Properties

Edit `src/main/resources/application.properties` before building:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/caravan_research
spring.datasource.username=root
spring.datasource.password=root

# Server Configuration (for standalone)
server.port=8080
server.servlet.context-path=/

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
```

### Tomcat Server Configuration

Edit `$CATALINA_HOME/conf/server.xml`:

```xml
<!-- Change default port if needed -->
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```

## Verification

### 1. Check Application Status

**View logs**:
```bash
tail -f $CATALINA_HOME/logs/catalina.out
```

**Look for**:
```
INFO: Deployment of web application archive [/path/to/caravan-research.war] has finished in [X] ms
Started CaravanResearchApplication in X.XXX seconds
```

### 2. Test Endpoints

**Frontend**:
```bash
curl http://localhost:8080/caravan-research/
```

**API Health Check**:
```bash
curl http://localhost:8080/caravan-research/api/caravans
curl http://localhost:8080/caravan-research/api/manufacturers
```

### 3. Access Web Interface

Open browser and navigate to:
- **Application**: http://localhost:8080/caravan-research
- **Home Page**: Should display the dashboard with navigation cards
- **Caravans**: http://localhost:8080/caravan-research/#!/caravans
- **Manufacturers**: http://localhost:8080/caravan-research/#!/manufacturers
- **Comparison**: http://localhost:8080/caravan-research/#!/comparison

## Troubleshooting

### Issue: ClassNotFoundException or NoClassDefFoundError

**Cause**: Using wrong Tomcat version (< 10) or wrong Servlet API

**Solution**:
- Verify Tomcat 10.x is installed
- Check `pom.xml` uses Jakarta EE dependencies (not javax)
- Ensure Java 17 is being used

### Issue: 404 Not Found

**Cause**: WAR not deployed or incorrect context path

**Solution**:
```bash
# Check if WAR is deployed
ls $CATALINA_HOME/webapps/

# Check Tomcat logs
tail -f $CATALINA_HOME/logs/catalina.out

# Verify URL includes context path
http://localhost:8080/caravan-research/
```

### Issue: Database Connection Failed

**Cause**: MySQL not running or wrong credentials

**Solution**:
```bash
# Check MySQL status
mysql -u root -proot -e "SHOW DATABASES;"

# Verify database exists
mysql -u root -proot -e "USE caravan_research; SHOW TABLES;"

# Update credentials in application.properties if needed
```

### Issue: Port 8080 Already in Use

**Solution**:
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process or change Tomcat port in server.xml
```

### Issue: Application Starts but Frontend Not Loading

**Cause**: Static resources not found

**Solution**:
- Verify `src/main/webapp` contains all HTML/JS/CSS files
- Rebuild WAR: `mvn clean package -DskipTests`
- Check browser console for 404 errors on static resources

## Production Deployment Best Practices

### 1. Security Configuration

**Update Tomcat users** (`tomcat-users.xml`):
- Use strong passwords
- Disable manager app in production

**Configure HTTPS**:
```xml
<!-- In server.xml -->
<Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
           maxThreads="150" SSLEnabled="true">
    <SSLHostConfig>
        <Certificate certificateKeystoreFile="conf/keystore.jks"
                     certificateKeystorePassword="changeit"
                     type="RSA" />
    </SSLHostConfig>
</Connector>
```

### 2. Database Configuration

**Production properties**:
```properties
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.org.hibernate.SQL=WARN
```

### 3. Performance Tuning

**Tomcat JVM Options** (`$CATALINA_HOME/bin/setenv.sh`):
```bash
export CATALINA_OPTS="-Xms512M -Xmx2048M -XX:+UseG1GC"
```

### 4. Logging

**Configure log rotation** (`$CATALINA_HOME/conf/logging.properties`):
```properties
1catalina.org.apache.juli.AsyncFileHandler.maxDays = 90
```

## Undeployment

```bash
# Stop Tomcat
$CATALINA_HOME/bin/shutdown.sh

# Remove WAR and expanded directory
rm $CATALINA_HOME/webapps/caravan-research.war
rm -rf $CATALINA_HOME/webapps/caravan-research

# Start Tomcat
$CATALINA_HOME/bin/startup.sh
```

## IntelliJ IDEA Integration

### Configure Tomcat in IntelliJ

1. **Go to**: Run → Edit Configurations
2. **Click**: + → Tomcat Server → Local
3. **Configure**:
   - **Name**: Tomcat 10 - Caravan Research
   - **Application server**: Browse to Tomcat installation
   - **HTTP port**: 8080
4. **Deployment Tab**:
   - **Click**: + → Artifact → caravan-research:war exploded
   - **Application context**: /caravan-research
5. **Server Tab**:
   - **On 'Update' action**: Update classes and resources
   - **On frame deactivation**: Update classes and resources
6. **Click**: OK

### Run from IntelliJ

1. Select "Tomcat 10 - Caravan Research" configuration
2. Click Run button (or Shift+F10)
3. Application will deploy and browser will open automatically

## Additional Resources

- **Tomcat Documentation**: https://tomcat.apache.org/tomcat-10.1-doc/
- **Spring Boot Reference**: https://docs.spring.io/spring-boot/docs/3.2.0/reference/html/
- **Jakarta EE**: https://jakarta.ee/specifications/servlet/6.0/

## Support

For issues or questions:
- Check application logs: `$CATALINA_HOME/logs/catalina.out`
- Check Tomcat logs: `$CATALINA_HOME/logs/localhost.*.log`
- Review this deployment guide
- Verify all prerequisites are met
