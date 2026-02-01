# Azure Web App Deployment Guide

## Prerequisites
- Azure account
- Azure CLI installed
- Maven installed
- Java 17 installed

## Step 1: Build the Application
```bash
mvn clean package -DskipTests
```

## Step 2: Create Azure Web App

### Using Azure Portal:
1. Go to Azure Portal (https://portal.azure.com)
2. Click "Create a resource"
3. Search for "Web App"
4. Fill in the following:
   - App name: hearthy-foundation
   - Runtime: Java 17
   - Java web server stack: Java SE (Embedded Web Server)
   - Operating System: Linux
   - Region: Choose your preferred region

### Using Azure CLI:
```bash
# Login to Azure
az login

# Create a resource group
az group create --name hearthy-foundation-rg --location eastus

# Create an App Service plan
az appservice plan create --name hearthy-foundation-plan --resource-group hearthy-foundation-rg --sku B1 --is-linux

# Create the web app
az webapp create --resource-group hearthy-foundation-rg --plan hearthy-foundation-plan --name hearthy-foundation --runtime "JAVA:17-java17"
```

## Step 3: Configure Environment Variables

Set the following environment variables in Azure Portal or using Azure CLI:

```bash
az webapp config appsettings set --resource-group hearthy-foundation-rg --name hearthy-foundation --settings \
  VITE_SUPABASE_URL="https://teshvcvyxgcljsroliux.supabase.co" \
  VITE_SUPABASE_ANON_KEY="your-anon-key-here" \
  SUPABASE_DATABASE_URL="jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.teshvcvyxgcljsroliux&password=YOUR_PASSWORD" \
  SPRING_PROFILES_ACTIVE="azure"
```

## Step 4: Deploy the Application

### Using Maven Plugin:
Add the Azure Web App Maven plugin to pom.xml, then:
```bash
mvn azure-webapp:deploy
```

### Using Azure CLI:
```bash
az webapp deployment source config-zip --resource-group hearthy-foundation-rg --name hearthy-foundation --src target/hearthy-foundation-1.0.0.jar
```

### Using FTP/Git:
1. Get deployment credentials from Azure Portal
2. Upload the JAR file from target/ directory
3. Restart the web app

## Step 5: Verify Deployment

Visit your application at: https://hearthy-foundation.azurewebsites.net

Check application logs:
```bash
az webapp log tail --resource-group hearthy-foundation-rg --name hearthy-foundation
```

## Troubleshooting

### Application won't start:
- Check Java version is set to 17
- Verify all environment variables are set correctly
- Check application logs

### Database connection issues:
- Verify SUPABASE_DATABASE_URL is correct
- Check Supabase firewall rules allow Azure IP addresses
- Test database connection from Azure Portal Console

### Static resources not loading:
- Ensure static files are in src/main/resources/static
- Check application.properties for correct paths
- Verify build includes all resources

## Continuous Deployment

To set up CI/CD with GitHub Actions:

1. Create `.github/workflows/azure-deploy.yml`
2. Configure GitHub secrets with Azure credentials
3. Push changes to trigger automatic deployment

## Scaling

To scale your application:
```bash
az appservice plan update --name hearthy-foundation-plan --resource-group hearthy-foundation-rg --sku P1V2
az webapp scale --resource-group hearthy-foundation-rg --name hearthy-foundation --instance-count 2
```

## Monitoring

Enable Application Insights:
```bash
az monitor app-insights component create --app hearthy-foundation-insights --location eastus --resource-group hearthy-foundation-rg
```
