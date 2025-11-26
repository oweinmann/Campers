# Manual IntelliJ Run Configuration Setup

If the pre-configured run configurations don't work, follow these steps to create them manually:

## Option 1: Spring Boot Run Configuration (Recommended)

### Step 1: Create Spring Boot Configuration
1. Click **Run** → **Edit Configurations...**
2. Click **+** (Add New Configuration)
3. Select **Spring Boot**

### Step 2: Configure Settings
Fill in the following:

| Field | Value |
|-------|-------|
| **Name** | `Caravan Research` |
| **Main class** | `com.caravan.CaravanResearchApplication` |
| **Working directory** | `$PROJECT_DIR$` |
| **JRE** | 17 or higher |
| **Use classpath of module** | `caravan-research` (should auto-detect) |

### Step 3: Configure Browser Opening
1. In the same dialog, look for **Before launch** section at the bottom
2. Click **+** → **Run External tool**
3. Click **+** to add a new external tool
4. Configure:
   - **Name**: `Open Chrome`
   - **Program**: `open` (macOS) or `start` (Windows)
   - **Arguments**: `-a "Google Chrome" http://localhost:8080` (macOS) or `chrome http://localhost:8080` (Windows)
   - **Working directory**: `$PROJECT_DIR$`
5. Click **OK**
6. In the Before launch section, move "Open Chrome" to run AFTER "Build"

### Step 4: Apply and Run
1. Click **OK** to save
2. Select your configuration from the dropdown
3. Click **Run** (green play button)

## Option 2: Application Run Configuration

### Step 1: Create Application Configuration
1. Click **Run** → **Edit Configurations...**
2. Click **+** (Add New Configuration)
3. Select **Application**

### Step 2: Configure Settings

| Field | Value |
|-------|-------|
| **Name** | `Caravan Research - App` |
| **Main class** | Click **...** and search for `CaravanResearchApplication` |
| **Use classpath of module** | Select `caravan-research` from dropdown |
| **Working directory** | `$PROJECT_DIR$` |
| **JRE** | 17 or higher |

### Step 3: Apply and Run
1. Click **OK** to save
2. Select your configuration from the dropdown
3. Click **Run** (green play button)
4. **Manually open** Chrome to http://localhost:8080

## Option 3: Maven Run Configuration

### Step 1: Open Maven Tool Window
1. **View** → **Tool Windows** → **Maven**
2. Expand **caravan-research**
3. Expand **Plugins**
4. Expand **spring-boot**

### Step 2: Run Maven Goal
1. Double-click **spring-boot:run**
2. Application starts in console
3. **Manually open** Chrome to http://localhost:8080

### Step 3: (Optional) Create Run Configuration from Maven
1. Right-click on **spring-boot:run**
2. Select **Create 'caravan-research [spring-boot:run]'...**
3. Click **OK**
4. This creates a reusable Maven run configuration

## Option 4: Terminal/Script Method

Use the provided script:

```bash
# Make it executable (first time only)
chmod +x run-and-open-chrome.sh

# Run the script
./run-and-open-chrome.sh
```

Or run manually:
```bash
# Start application
mvn spring-boot:run

# In another terminal, open Chrome
open -a "Google Chrome" http://localhost:8080  # macOS
start chrome http://localhost:8080              # Windows
```

## Troubleshooting

### "Cannot resolve symbol 'SpringBootApplication'"

**Solution**: Reload Maven project
1. **View** → **Tool Windows** → **Maven**
2. Click **Reload All Maven Projects** (circular arrows icon)
3. Wait for indexing to complete
4. Try creating run configuration again

### "Module not specified"

**Solution**: IntelliJ needs to import the project as Maven
1. **File** → **Close Project**
2. On welcome screen, click **Open**
3. Navigate to `/Users/oweinmann/Workspace/Campers`
4. Select the **pom.xml** file
5. Choose **Open as Project**
6. Wait for Maven import and indexing

### "Main class not found"

**Solution**: Rebuild the project
```bash
mvn clean install -DskipTests
```
Then in IntelliJ:
1. **Build** → **Rebuild Project**
2. Try creating run configuration again

### Java Version Mismatch

**Solution**: Ensure Java 17 is configured
1. **File** → **Project Structure** → **Project**
2. Set **SDK**: 17 or higher
3. Set **Language level**: 17
4. Click **Apply**

## Verifying Configuration

After creating the run configuration:

1. **Check compilation**: 
   - Look at **Problems** tool window
   - Should have no errors

2. **Check dependencies**:
   - Open **External Libraries** in Project view
   - Should see Spring Boot and MySQL libraries

3. **Test run**:
   - Select your run configuration
   - Click Debug (bug icon) to see detailed output
   - Look for "Started CaravanResearchApplication in X seconds"

## Quick Reference: What Each Method Does

| Method | Startup Time | Chrome Auto-Open | Hot Reload | Best For |
|--------|--------------|------------------|------------|----------|
| Spring Boot Config | Fast | Yes (with tool) | Yes | Development |
| Application Config | Fast | No (manual) | Yes | Simple setup |
| Maven Plugin | Medium | No (manual) | No | Testing |
| Terminal/Script | Medium | Yes | No | Quick runs |

## Recommended Workflow

1. **First time**: Use Maven method to verify everything works
2. **Development**: Create Spring Boot run configuration
3. **Quick tests**: Use the shell script
4. **Deployment testing**: Use Tomcat configuration

---

**Still having issues?** 
- Check that Maven dependencies are downloaded: `mvn dependency:resolve`
- Verify Java 17 is installed: `java -version`
- Check MySQL is running: `mysql -u root -proot -e "SELECT 1;"`
