# Caravan Research Application - Setup Guide

A full-stack web application for researching and comparing off-road caravans in Australia. Built with Spring Boot, AngularJS, and MySQL.

## Project Overview

This application helps users research, track, and compare off-road caravans that meet specific requirements:
- **Condition**: New only (not used)
- **Length**: Minimum 15 feet, maximum 18 feet
- **Price**: Maximum $100,000 AUD
- **Sleeping**: At least 2 bunk beds for children
- **Roof**: Hard top only (no hybrid roofs)

## Technology Stack

### Backend
- **Java**: 11
- **Spring Boot**: 2.7.14
- **Spring Data JPA**: For database operations
- **MySQL**: 8.0.33
- **Lombok**: For reducing boilerplate code
- **Maven**: Build and dependency management

### Frontend
- **AngularJS**: 1.8.2
- **Bootstrap**: 5.1.3
- **HTML5/CSS3**: For UI structure and styling

## Project Structure

```
Campers/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── caravan/
│   │   │           ├── CaravanResearchApplication.java
│   │   │           ├── controller/
│   │   │           │   ├── CaravanController.java
│   │   │           │   └── ManufacturerController.java
│   │   │           ├── model/
│   │   │           │   ├── Caravan.java
│   │   │           │   └── Manufacturer.java
│   │   │           ├── repository/
│   │   │           │   ├── CaravanRepository.java
│   │   │           │   └── ManufacturerRepository.java
│   │   │           └── service/
│   │   │               ├── CaravanService.java
│   │   │               └── ManufacturerService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── index.html
│   │           ├── css/
│   │           │   └── style.css
│   │           ├── js/
│   │           │   ├── app.js
│   │           │   ├── controllers/
│   │           │   │   ├── caravanController.js
│   │           │   │   ├── manufacturerController.js
│   │           │   │   └── comparisonController.js
│   │           │   └── services/
│   │           │       ├── caravanService.js
│   │           │       └── manufacturerService.js
│   │           └── views/
│   │               ├── home.html
│   │               ├── caravans.html
│   │               ├── caravan-form.html
│   │               ├── manufacturers.html
│   │               ├── manufacturer-form.html
│   │               └── comparison.html
│   └── test/
│       └── java/
│           └── com/
│               └── caravan/
└── pom.xml
```

## Prerequisites

Before running this application, ensure you have the following installed:

1. **Java Development Kit (JDK) 11** or higher
   - Download from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use OpenJDK
   - Verify installation: `java -version`

2. **Maven 3.6+**
   - Download from [Apache Maven](https://maven.apache.org/download.cgi)
   - Verify installation: `mvn -version`

3. **MySQL 8.0+**
   - Download from [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
   - Verify installation: `mysql --version`

4. **Git** (optional, for version control)
   - Download from [Git](https://git-scm.com/downloads)

## Database Setup

1. **Start MySQL Server**
   ```bash
   # On macOS with Homebrew
   brew services start mysql

   # On Linux
   sudo systemctl start mysql

   # On Windows
   # Start MySQL from Services or MySQL Workbench
   ```

2. **Create Database** (optional - application will auto-create)
   ```sql
   CREATE DATABASE caravan_research;
   ```

3. **Configure Database Credentials**

   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password_here
   ```

## Installation & Setup

1. **Clone or navigate to the project directory**
   ```bash
   cd /Users/oweinmann/Workspace/Campers
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Alternatively, run the JAR file directly:
   ```bash
   java -jar target/caravan-research-1.0.0.jar
   ```

4. **Access the application**

   Open your web browser and navigate to:
   ```
   http://localhost:8080
   ```

## API Endpoints

### Caravan Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/caravans` | Get all caravans |
| GET | `/api/caravans/{id}` | Get caravan by ID |
| GET | `/api/caravans/priority/{priority}` | Get caravans by priority |
| GET | `/api/caravans/origin/{origin}` | Get caravans by origin |
| GET | `/api/caravans/make/{make}` | Get caravans by make |
| GET | `/api/caravans/price-range?min={min}&max={max}` | Get caravans by price range |
| GET | `/api/caravans/bunk-beds/{minBunkBeds}` | Get caravans with minimum bunk beds |
| POST | `/api/caravans` | Create new caravan |
| PUT | `/api/caravans/{id}` | Update caravan |
| DELETE | `/api/caravans/{id}` | Delete caravan (soft delete) |

### Manufacturer Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/manufacturers` | Get all manufacturers |
| GET | `/api/manufacturers/{id}` | Get manufacturer by ID |
| GET | `/api/manufacturers/origin/{origin}` | Get manufacturers by origin |
| GET | `/api/manufacturers/search?query={query}` | Search manufacturers by brand |
| POST | `/api/manufacturers` | Create new manufacturer |
| PUT | `/api/manufacturers/{id}` | Update manufacturer |
| DELETE | `/api/manufacturers/{id}` | Delete manufacturer |

## Features

### 1. Home Dashboard
- Quick access to all sections
- Display of caravan requirements
- Navigation cards for Caravans, Manufacturers, and Comparison

### 2. Caravan Management
- View all caravans in a filterable table
- Filter by priority, origin, and price
- Add new caravans with comprehensive specifications
- Edit existing caravan details
- Soft delete caravans (mark as deleted without removing from database)

### 3. Manufacturer Management
- Browse Australian-made and imported manufacturers
- Search manufacturers by brand name
- Filter by origin (Australian Made / Imported)
- Add and edit manufacturer information
- Clickable website links for each manufacturer

### 4. Caravan Comparison
- Compare up to 5 caravans side-by-side
- View all specifications in a comparison table
- Easy add/remove caravans from comparison
- Comprehensive specification categories:
  - Basic Information
  - Dimensions
  - Weight Specifications
  - Sleeping Arrangements
  - Additional Features

## Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/caravan_research?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Environment Variables (Optional)

You can override properties using environment variables:

```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export SERVER_PORT=8090
```

## Development

### Running in Development Mode

Spring Boot DevTools is included for automatic application restarts:

```bash
mvn spring-boot:run
```

Changes to Java files will trigger automatic restart.

### Building for Production

```bash
mvn clean package -DskipTests
java -jar target/caravan-research-1.0.0.jar
```

## Troubleshooting

### Common Issues

**1. Port 8080 already in use**
```
Solution: Change the port in application.properties or kill the process using port 8080
netstat -ano | findstr :8080  # Windows
lsof -ti:8080 | xargs kill    # macOS/Linux
```

**2. Cannot connect to MySQL**
```
Solution:
- Verify MySQL is running: sudo systemctl status mysql
- Check credentials in application.properties
- Ensure database exists or createDatabaseIfNotExist=true is set
```

**3. CORS errors in browser**
```
Solution: CORS is enabled by default with @CrossOrigin(origins = "*")
If needed, configure specific origins in the controllers
```

**4. AngularJS not loading**
```
Solution:
- Check browser console for errors
- Verify all script files are loaded in index.html
- Clear browser cache
```

## Data Model

### Caravan Entity
- id (Long, Primary Key)
- make (String, required)
- model (String, required)
- origin (String, required) - "Australian Made" or "Imported"
- price (BigDecimal, required)
- website (String)
- externalLength, externalWidth, externalHeight, internalHeight (String)
- tareWeight, atm, ballWeight, gtm (Integer)
- mainBed (String)
- bunkBeds (Integer)
- bunkType (String)
- priority (Enum) - HIGH, MEDIUM, BUDGET, PREMIUM
- status (Enum) - RESEARCHING, SHORTLISTED, EXCLUDED, PURCHASED
- features, notes (String, text)
- deleted (Boolean)
- timestamps (createdAt, updatedAt)

### Manufacturer Entity
- id (Long, Primary Key)
- brand (String, required, unique)
- origin (String, required)
- website (String)
- knownFor (String)
- keyModels (String)
- notes (String, text)

## Future Enhancements

- [ ] User authentication and authorization
- [ ] Image upload for caravans
- [ ] Advanced search with multiple filters
- [ ] Export comparison to PDF
- [ ] Email notifications for price changes
- [ ] Dealer integration
- [ ] Review and rating system
- [ ] Mobile app version

## Contributing

This is a personal research project. If you'd like to contribute:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

This project is for personal use in researching off-road caravans.

## Support

For issues or questions:
- Check the Troubleshooting section
- Review the API documentation
- Check application logs in the console

## Acknowledgments

- Australian caravan manufacturers for comprehensive specifications
- Spring Boot community for excellent documentation
- AngularJS team for the frontend framework
- Bootstrap for responsive design components
