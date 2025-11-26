# IntelliJ IDEA Setup Guide

## Database Setup

### 1. Database Created
The MySQL database `caravan_research` has been created and is ready to use.

### 2. Configure DataSource in IntelliJ IDEA

#### Step 1: Open Database Tool Window
1. In IntelliJ IDEA, go to **View** → **Tool Windows** → **Database** (or press `⌘ + Shift + D` on Mac)
2. This opens the Database tool window on the right side

#### Step 2: Add New Data Source
1. Click the **+** button in the Database tool window
2. Select **Data Source** → **MySQL**

#### Step 3: Configure Connection Settings

Fill in the following connection details:

**General Tab:**
- **Name**: `caravan_research`
- **Host**: `localhost`
- **Port**: `3306`
- **Database**: `caravan_research`
- **User**: `root`
- **Password**: *(leave empty if no password is set)*

**Connection String:**
```
jdbc:mysql://localhost:3306/caravan_research?useSSL=false&serverTimezone=UTC
```

#### Step 4: Download MySQL Driver
1. If prompted, click **Download missing driver files**
2. IntelliJ will automatically download the MySQL JDBC driver

#### Step 5: Test Connection
1. Click **Test Connection** button at the bottom
2. You should see "Succeeded" message
3. If successful, click **OK** to save the datasource

#### Step 6: View Database Schema
1. Once connected, expand the `caravan_research` database in the Database tool window
2. After running the application for the first time, you'll see these tables:
   - `caravans`
   - `manufacturers`

## Running the Application in IntelliJ

### Option 1: Using Maven
1. Open the **Maven** tool window (View → Tool Windows → Maven)
2. Expand **caravan-research** → **Plugins** → **spring-boot**
3. Double-click **spring-boot:run**

### Option 2: Run Main Class
1. Navigate to `src/main/java/com/caravan/CaravanResearchApplication.java`
2. Right-click on the file
3. Select **Run 'CaravanResearchApplication'**
4. Or click the green play button in the gutter next to the main method

### Option 3: Using Run Configuration
1. Go to **Run** → **Edit Configurations**
2. Click **+** → **Spring Boot**
3. Configure:
   - **Name**: `Caravan Research`
   - **Main class**: `com.caravan.CaravanResearchApplication`
   - **Working directory**: `/Users/oweinmann/Workspace/Campers`
   - **JRE**: Java 11 or higher
4. Click **OK** and run

## Accessing the Application

Once the application starts successfully:

1. **Backend API**: http://localhost:8080/api
2. **Frontend**: http://localhost:8080
3. **H2 Console** (if enabled): http://localhost:8080/h2-console

## Database Management in IntelliJ

### View Tables and Data
1. In the Database tool window, expand:
   - `caravan_research` → `schemas` → `caravan_research` → `tables`
2. Double-click any table to view data
3. Right-click on table for more options:
   - **Edit** - Modify table structure
   - **Dump Data** - Export data
   - **Import Data** - Import data from CSV/SQL

### Run SQL Queries
1. Right-click on `caravan_research` database
2. Select **New** → **Query Console**
3. Write SQL queries, for example:
```sql
-- View all caravans
SELECT * FROM caravans;

-- View all manufacturers
SELECT * FROM manufacturers;

-- Insert sample manufacturer
INSERT INTO manufacturers (brand, origin, website, known_for, key_models)
VALUES ('Lotus Caravans', 'Australian Made', 'https://www.lotuscaravans.com.au',
        'Off-road capability', 'Trooper, Freelander');

-- Insert sample caravan
INSERT INTO caravans (make, model, origin, price, priority, status, bunk_beds, deleted)
VALUES ('New Age Caravans', 'Manta Ray 16ft', 'Australian Made', 89000, 'HIGH', 'RESEARCHING', 2, false);
```
4. Select the query and press `⌘ + Enter` (Mac) or `Ctrl + Enter` (Windows) to execute

### Database Synchronization
After starting the application:
1. Right-click on `caravan_research` in Database tool window
2. Select **Synchronize** to refresh the schema
3. You should see the auto-generated tables from JPA entities

## Useful IntelliJ Database Features

### 1. Data Editor
- Double-click on a table to open the data editor
- Edit cells directly (like a spreadsheet)
- Press `⌘ + Enter` to commit changes

### 2. Export Data
- Right-click on table → **Dump Data to File**
- Choose format: SQL INSERT, CSV, JSON, etc.
- Useful for backing up or sharing data

### 3. Import Data
- Right-click on table → **Import Data from File**
- Select CSV or SQL file
- Map columns and import

### 4. Compare Data Sources
- Right-click database → **Compare with...**
- Select another datasource to compare schemas

### 5. Generate DDL
- Right-click on table → **SQL Scripts** → **DDL**
- View or copy the CREATE TABLE statement

## Troubleshooting

### Issue: "Access denied for user 'root'@'localhost'"
**Solution**: Update MySQL password in `application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_actual_password
```

### Issue: "Communications link failure"
**Solution**:
1. Verify MySQL is running: `brew services list` (Mac)
2. Start MySQL: `brew services start mysql`
3. Check port 3306 is not blocked

### Issue: Driver not found
**Solution**:
1. In IntelliJ datasource settings, click **Download missing driver files**
2. Or manually add MySQL Connector JAR to project

### Issue: Tables not appearing
**Solution**:
1. Run the Spring Boot application at least once
2. Hibernate will auto-create tables (ddl-auto=update)
3. Refresh/Synchronize the database in IntelliJ

## Recommended IntelliJ Plugins

1. **JPA Buddy** - Enhanced JPA/Hibernate support
2. **Database Navigator** - Alternative database tool
3. **Spring Boot Assistant** - Better Spring Boot support

## Next Steps

1. ✅ Database created
2. ✅ Datasource configured in IntelliJ
3. ⏭️ Run the application to generate tables
4. ⏭️ Use the frontend at http://localhost:8080 to add data
5. ⏭️ View and query data using IntelliJ Database tools

---

**Note**: The application uses `spring.jpa.hibernate.ddl-auto=update` which means:
- Tables will be auto-created on first run
- Schema will be auto-updated when entities change
- Existing data will be preserved
- For production, change this to `validate` or `none`
