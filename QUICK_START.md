# Quick Start Guide - Caravan Research Application

## 🚀 Running the Application in IntelliJ IDEA

### Method 1: One-Click Run with Chrome (Recommended)

The easiest way to start the application:

1. **Open IntelliJ IDEA** and load the project
2. **Select Run Configuration** from the dropdown in the toolbar:
   - Choose **"Caravan Research - Spring Boot"**
3. **Click the Run button** (▶️) or press `Shift + F10`
4. **Chrome will automatically open** to http://localhost:8080
5. **Start developing!** Hot reload is enabled

That's it! The application is now running and Chrome is open.

### Method 2: Tomcat 10 Deployment Testing

To test the actual WAR deployment on Tomcat:

1. **Configure Tomcat** in IntelliJ (if not already done):
   - Go to **File** → **Settings** → **Build, Execution, Deployment** → **Application Servers**
   - Click **+** → **Tomcat Server**
   - Browse to your Tomcat 10 installation directory
   - Click **OK**

2. **Select Run Configuration**:
   - Choose **"Caravan Research - Tomcat 10"** from dropdown

3. **Click Run** (▶️)
   - Chrome opens to http://localhost:8080/caravan-research
   - Application deploys as WAR to Tomcat

## 📋 Prerequisites Checklist

Before running, ensure you have:

- ✅ **Java 11** installed (or higher)
- ✅ **MySQL** running on port 3306
- ✅ **Database** `caravan_research` created
- ✅ **MySQL credentials**: username=`root`, password=`root`
- ✅ **IntelliJ IDEA** with project imported

## 🗄️ Database Setup

Quick database setup:

```bash
# Start MySQL
brew services start mysql  # macOS
# or
sudo systemctl start mysql  # Linux

# Create database
mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS caravan_research;"

# Verify
mysql -u root -proot -e "SHOW DATABASES LIKE 'caravan_research';"
```

## 🌐 Access Points

Once running, access the application at:

| Component | URL | Description |
|-----------|-----|-------------|
| **Frontend** | http://localhost:8080 | Main web application |
| **API** | http://localhost:8080/api | REST API endpoints |
| **Caravans API** | http://localhost:8080/api/caravans | Caravan data |
| **Manufacturers API** | http://localhost:8080/api/manufacturers | Manufacturer data |

## 🛠️ Development Features

### Hot Reload Enabled
- **Update Classes and Resources** is configured
- Make changes to Java files → Changes apply immediately
- Edit HTML/JS/CSS → Refresh browser to see changes
- No need to restart for most changes

### Debug Mode
1. Click the **Debug** button (🐛) instead of Run
2. Set breakpoints in your code (click in the gutter)
3. Debug toolbar appears when breakpoint hits

### Database Access
1. **Open Database Tool**: View → Tool Windows → Database
2. **Browse data**: Expand caravan_research → tables
3. **Run queries**: Right-click database → New Query Console

## 📊 Using the Application

### Add a Caravan
1. Click **"📊 Caravans"** in navigation
2. Click **"+ Add New Caravan"** button
3. Fill in the form (Make, Model, Origin, Price, etc.)
4. Click **"Create Caravan"**

### Add a Manufacturer
1. Click **"🏭 Manufacturers"** in navigation
2. Click **"+ Add New Manufacturer"**
3. Enter brand, origin, website, etc.
4. Click **"Create Manufacturer"**

### Compare Caravans
1. Click **"🆚 Comparison"** in navigation
2. Select up to 5 caravans from dropdown
3. View side-by-side comparison table
4. Click × to remove from comparison

## 🔧 Troubleshooting

### Application Won't Start

**Check MySQL is running:**
```bash
mysql -u root -proot -e "SELECT 1;"
```

**Check port 8080 is free:**
```bash
lsof -ti:8080 | xargs kill  # macOS/Linux
```

**Verify Java version:**
```bash
java -version  # Should be 11 or higher
```

### Chrome Doesn't Open Automatically

**Manual open:**
- Visit http://localhost:8080 in any browser
- Check IntelliJ console for "Started CaravanResearchApplication"

**Configure browser in run configuration:**
1. Edit run configuration
2. Look for "Open browser" or "Browser" section
3. Select Chrome and enable

### Database Connection Error

**Update credentials in `application.properties`:**
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

**Verify database exists:**
```bash
mysql -u root -proot -e "USE caravan_research; SHOW TABLES;"
```

### Port Already in Use

**Change server port in `application.properties`:**
```properties
server.port=8081
```

Then access at http://localhost:8081

## 📚 Documentation

For more detailed information, see:

- **INTELLIJ_SETUP.md** - Complete IntelliJ configuration guide
- **TOMCAT_DEPLOYMENT.md** - Tomcat 10 deployment instructions
- **TOMCAT_SETUP.md** - Application setup and configuration
- **PROJECT_SUMMARY.md** - Comprehensive project overview

## 🎯 Next Steps

1. **Run the application** using the run configuration
2. **Add some test data** (caravans and manufacturers)
3. **Explore the comparison feature** with multiple caravans
4. **Check the database** using IntelliJ's Database tool
5. **Start customizing** the application for your needs

## 💡 Tips

- **Use the Spring Boot Dashboard**: View → Tool Windows → Services
  Shows running Spring Boot applications and allows starting/stopping

- **View Logs**: Run tool window shows application logs in real-time
  Look for "Started CaravanResearchApplication" to confirm startup

- **API Testing**: Use IntelliJ's HTTP Client
  Tools → HTTP Client → Create Request

- **Maven Lifecycle**: Maven tool window → Lifecycle
  - `clean` - Remove target directory
  - `compile` - Compile Java code
  - `package` - Build WAR file
  - `install` - Install to local Maven repo

---

**Need Help?** Check the troubleshooting section above or review the detailed documentation files.

**Happy Coding!** 🚀
