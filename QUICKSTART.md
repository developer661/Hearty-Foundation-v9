# Quick Start Guide

## Build and Deploy to Azure Web App

### Step 1: Prerequisites

Ensure you have installed:
- Java 17 JDK
- Maven 3.6 or higher
- Azure CLI (optional, for command-line deployment)

Verify installations:
```bash
java -version    # Should show Java 17
mvn -version     # Should show Maven 3.6+
```

### Step 2: Build the Application

```bash
# Navigate to project directory
cd hearthy-foundation

# Clean and build the JAR file
mvn clean package -DskipTests

# The JAR file will be created at:
# target/hearthy-foundation-1.0.0.jar
```

### Step 3: Test Locally (Optional)

```bash
# Set environment variables
export VITE_SUPABASE_URL="https://teshvcvyxgcljsroliux.supabase.co"
export VITE_SUPABASE_ANON_KEY="your-anon-key-here"
export SUPABASE_DB_PASSWORD="your-db-password"

# Run the application
mvn spring-boot:run

# Or run the JAR directly
java -jar target/hearthy-foundation-1.0.0.jar

# Access at http://localhost:8080
```

### Step 4: Create Azure Web App

#### Using Azure Portal:

1. Go to https://portal.azure.com
2. Click "+ Create a resource"
3. Search for "Web App" and click Create
4. Fill in the form:
   - **App name**: `hearthy-foundation` (or your preferred name)
   - **Runtime stack**: Java 17
   - **Java web server stack**: Java SE (Embedded Web Server)
   - **Operating System**: Linux
   - **Region**: Choose nearest to your users
   - **Pricing**: Choose appropriate tier (B1 or higher recommended)
5. Click "Review + Create", then "Create"

#### Using Azure CLI:

```bash
# Login to Azure
az login

# Set variables
RESOURCE_GROUP="hearthy-foundation-rg"
APP_NAME="hearthy-foundation"
LOCATION="eastus"

# Create resource group
az group create --name $RESOURCE_GROUP --location $LOCATION

# Create App Service plan
az appservice plan create \
  --name "${APP_NAME}-plan" \
  --resource-group $RESOURCE_GROUP \
  --sku B1 \
  --is-linux

# Create Web App
az webapp create \
  --resource-group $RESOURCE_GROUP \
  --plan "${APP_NAME}-plan" \
  --name $APP_NAME \
  --runtime "JAVA:17-java17"
```

### Step 5: Configure Environment Variables

#### Using Azure Portal:

1. Go to your Web App in Azure Portal
2. Navigate to "Configuration" > "Application settings"
3. Click "+ New application setting" for each variable:
   - **Name**: `VITE_SUPABASE_URL`, **Value**: `https://teshvcvyxgcljsroliux.supabase.co`
   - **Name**: `VITE_SUPABASE_ANON_KEY`, **Value**: `your-anon-key-here`
   - **Name**: `SUPABASE_DB_PASSWORD`, **Value**: `your-db-password`
   - **Name**: `SPRING_PROFILES_ACTIVE`, **Value**: `azure`
4. Click "Save"

#### Using Azure CLI:

```bash
az webapp config appsettings set \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --settings \
    VITE_SUPABASE_URL="https://teshvcvyxgcljsroliux.supabase.co" \
    VITE_SUPABASE_ANON_KEY="your-anon-key-here" \
    SUPABASE_DB_PASSWORD="your-db-password" \
    SPRING_PROFILES_ACTIVE="azure"
```

### Step 6: Deploy the Application

#### Method 1: Using Azure CLI (Recommended)

```bash
az webapp deployment source config-zip \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --src target/hearthy-foundation-1.0.0.jar
```

#### Method 2: Using FTP

1. Get FTP credentials from Azure Portal (Deployment Center)
2. Upload `target/hearthy-foundation-1.0.0.jar` to `/site/wwwroot`
3. Restart the app

#### Method 3: Using Azure Portal

1. Go to your Web App
2. Click "Deployment Center"
3. Choose deployment source
4. Upload the JAR file
5. Start deployment

### Step 7: Verify Deployment

#### Check Application Status:

```bash
# View logs
az webapp log tail --resource-group $RESOURCE_GROUP --name $APP_NAME

# Check if app is running
az webapp show --resource-group $RESOURCE_GROUP --name $APP_NAME --query state
```

#### Access Your Application:

```
https://${APP_NAME}.azurewebsites.net
```

Replace `${APP_NAME}` with your actual app name.

### Step 8: Test Your Application

Visit these URLs to test:

1. **Home Page**: `https://${APP_NAME}.azurewebsites.net/`
2. **Opportunities**: `https://${APP_NAME}.azurewebsites.net/opportunities`
3. **Registration**: `https://${APP_NAME}.azurewebsites.net/volunteer-registration`
4. **Dashboard**: `https://${APP_NAME}.azurewebsites.net/dashboard`
5. **Health Check**: `https://${APP_NAME}.azurewebsites.net/actuator/health`

### API Endpoints to Test:

```bash
# Get urgent opportunities
curl https://${APP_NAME}.azurewebsites.net/api/opportunities/urgent

# Get all opportunities
curl https://${APP_NAME}.azurewebsites.net/api/opportunities

# Submit volunteer registration
curl -X POST https://${APP_NAME}.azurewebsites.net/api/volunteers/registrations \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john@example.com",
    "phone": "+48123456789",
    "dateOfBirth": "1990-01-01",
    "profession": "Teacher",
    "motivation": "I want to help children"
  }'
```

## Troubleshooting

### Application won't start

1. Check Java version is set correctly:
   ```bash
   az webapp config show --resource-group $RESOURCE_GROUP --name $APP_NAME --query linuxFxVersion
   ```

2. View detailed logs:
   ```bash
   az webapp log download --resource-group $RESOURCE_GROUP --name $APP_NAME
   ```

3. Check environment variables are set:
   ```bash
   az webapp config appsettings list --resource-group $RESOURCE_GROUP --name $APP_NAME
   ```

### Database connection issues

1. Test database connection from local machine
2. Verify Supabase allows connections from Azure IP range
3. Check database credentials are correct
4. Ensure SUPABASE_DB_PASSWORD is set correctly

### 502 Bad Gateway errors

1. Wait a few minutes for the application to fully start
2. Restart the web app:
   ```bash
   az webapp restart --resource-group $RESOURCE_GROUP --name $APP_NAME
   ```
3. Check application logs for errors

### Static resources not loading

1. Verify files are in `src/main/resources/static/`
2. Check build includes all resources
3. Clear browser cache

## Monitoring

### View Live Logs:

```bash
az webapp log tail --resource-group $RESOURCE_GROUP --name $APP_NAME
```

### Enable Application Insights:

```bash
az monitor app-insights component create \
  --app "${APP_NAME}-insights" \
  --location $LOCATION \
  --resource-group $RESOURCE_GROUP

# Get instrumentation key
INSTRUMENTATION_KEY=$(az monitor app-insights component show \
  --app "${APP_NAME}-insights" \
  --resource-group $RESOURCE_GROUP \
  --query instrumentationKey -o tsv)

# Set in web app
az webapp config appsettings set \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --settings APPINSIGHTS_INSTRUMENTATIONKEY=$INSTRUMENTATION_KEY
```

## Scaling

### Scale Up (Vertical):

```bash
az appservice plan update \
  --name "${APP_NAME}-plan" \
  --resource-group $RESOURCE_GROUP \
  --sku P1V2
```

### Scale Out (Horizontal):

```bash
az webapp scale \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --instance-count 3
```

### Auto-Scaling:

Configure in Azure Portal under "Scale out (App Service plan)"

## Continuous Deployment

### Set up GitHub Actions:

1. Generate Azure credentials:
   ```bash
   az ad sp create-for-rbac \
     --name "${APP_NAME}-deploy" \
     --role contributor \
     --scopes /subscriptions/{subscription-id}/resourceGroups/${RESOURCE_GROUP} \
     --sdk-auth
   ```

2. Add credentials to GitHub Secrets as `AZURE_CREDENTIALS`

3. Create `.github/workflows/azure-deploy.yml` (see repository for template)

4. Push changes to trigger deployment

## Clean Up Resources

To delete everything and stop charges:

```bash
az group delete --name $RESOURCE_GROUP --yes --no-wait
```

## Need Help?

- **Documentation**: See README-JAVA.md and azure-deploy.md
- **Issues**: Check MIGRATION-SUMMARY.md for known issues
- **Support**: contact@hearthy.org

## Summary

You now have a fully functional Java Spring Boot application running on Azure Web App!

Your application URL: `https://${APP_NAME}.azurewebsites.net`

Enjoy your new enterprise-grade volunteer platform!
