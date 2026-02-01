# Azure Web App Configuration Guide

## Issue: Application Fails to Start with Missing Environment Variables

If you see this error:
```
Could not resolve placeholder 'VITE_SUPABASE_ANON_KEY' in value "${VITE_SUPABASE_ANON_KEY}"
```

This means the environment variables are not configured in Azure Web App.

## Solution: Configure Environment Variables in Azure

### Option 1: Using Azure Portal (Recommended)

1. **Navigate to your Web App**
   - Go to https://portal.azure.com
   - Find your Web App (hearthy-foundation or your app name)

2. **Go to Configuration**
   - Click on "Configuration" in the left sidebar
   - Click on "Application settings" tab

3. **Add the following Application Settings**

   Click "+ New application setting" for each:

   | Name | Value |
   |------|-------|
   | `VITE_SUPABASE_URL` | `https://amoumlarjqzihcfoardl.supabase.co` |
   | `VITE_SUPABASE_ANON_KEY` | `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFtb3VtbGFyanF6aWhjZm9hcmRsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njk2MjcyNjEsImV4cCI6MjA4NTIwMzI2MX0.h-mQotIBfLacqr0CddU3ePTf_iczPMrD8ujjHXz76x8` |
   | `SUPABASE_DATABASE_URL` | `jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.amoumlarjqzihcfoardl&password=M7Qx6sseJ4V!NLY` |
   | `SPRING_PROFILES_ACTIVE` | `azure` |

   **Important**: Replace `YOUR_DB_PASSWORD` with your actual Supabase database password.

4. **Save the Configuration**
   - Click "Save" at the top
   - Click "Continue" when prompted
   - Wait for the settings to be applied

5. **Restart the Application**
   - Click "Restart" at the top of the Web App overview page
   - Wait 2-3 minutes for the application to start

### Option 2: Using Azure CLI

```bash
# Set your resource group and app name
RESOURCE_GROUP="hearthy-foundation-rg"
APP_NAME="hearthy-foundation"

# Configure all application settings at once
az webapp config appsettings set \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --settings \
    VITE_SUPABASE_URL="https://amoumlarjqzihcfoardl.supabase.co" \
    VITE_SUPABASE_ANON_KEY="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFtb3VtbGFyanF6aWhjZm9hcmRsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njk2MjcyNjEsImV4cCI6MjA4NTIwMzI2MX0.h-mQotIBfLacqr0CddU3ePTf_iczPMrD8ujjHXz76x8" \
    SUPABASE_DATABASE_URL="jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.amoumlarjqzihcfoardl&password=M7Qx6sseJ4V!NLY" \
    SPRING_PROFILES_ACTIVE="azure"

# Restart the web app
az webapp restart --resource-group $RESOURCE_GROUP --name $APP_NAME
```

**Important**: Replace `YOUR_DB_PASSWORD` with your actual password.

## Verify Configuration

### Check if Settings are Applied

```bash
# List all application settings
az webapp config appsettings list \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --query "[].{Name:name, Value:value}" \
  --output table
```

### View Application Logs

```bash
# View live logs
az webapp log tail \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME
```

Look for:
- `Started HearthyFoundationApplication` - Application started successfully
- No error messages about missing placeholders

### Test the Application

Once configured and restarted, test these URLs:

1. **Health Check**: `https://your-app-name.azurewebsites.net/actuator/health`
   - Should return: `{"status":"UP"}`

2. **Home Page**: `https://your-app-name.azurewebsites.net/`
   - Should display the Hearthy Foundation home page

3. **API Test**: `https://your-app-name.azurewebsites.net/api/opportunities/urgent`
   - Should return JSON array of urgent opportunities

## Common Issues and Solutions

### Issue: Still getting "Could not resolve placeholder" error

**Solution**:
- Verify settings are saved in Azure Portal
- Ensure you restarted the app after saving
- Check for typos in environment variable names (they're case-sensitive)

### Issue: Database connection errors

**Solution**:
- Verify `SUPABASE_DATABASE_URL` includes the correct password
- Test database connectivity from Azure Portal Console:
  ```bash
  curl https://teshvcvyxgcljsroliux.supabase.co
  ```
- Check Supabase project is running and accessible

### Issue: Application shows 502 Bad Gateway

**Solution**:
- Wait 2-3 minutes after restart (application startup time)
- Check application logs for startup errors
- Verify Java 17 runtime is selected

### Issue: Port binding errors

**Solution**:
- The application automatically uses `PORT` environment variable from Azure
- No manual configuration needed
- Check `server.port` is set to 80 in application-azure.properties

## Default Values (Development Only)

The application now includes default values for local development:
- **Supabase URL**: Uses the existing project URL as default
- **Supabase Anon Key**: Includes the anon key from .env
- **Database URL**: Uses the Supabase connection string

These defaults allow the application to start even without environment variables set, but you should still configure them properly in Azure for production.

## Security Best Practices

1. **Never commit secrets to Git**
   - The .env file should be in .gitignore
   - Use Azure Application Settings for secrets

2. **Use Azure Key Vault for sensitive data** (Optional but recommended)
   ```bash
   # Create Key Vault
   az keyvault create \
     --name "${APP_NAME}-vault" \
     --resource-group $RESOURCE_GROUP \
     --location eastus

   # Store secrets
   az keyvault secret set \
     --vault-name "${APP_NAME}-vault" \
     --name "SupabaseAnonKey" \
     --value "your-key-here"

   # Reference in App Settings
   # @Microsoft.KeyVault(SecretUri=https://your-vault.vault.azure.net/secrets/SupabaseAnonKey/)
   ```

3. **Rotate credentials regularly**
   - Change database passwords periodically
   - Generate new Supabase API keys if compromised

4. **Use managed identities**
   - Enable system-assigned managed identity in Azure Web App
   - Grant permissions to access Azure resources without storing credentials

## Quick Check Script

Save this as `check-azure-config.sh`:

```bash
#!/bin/bash
RESOURCE_GROUP="hearthy-foundation-rg"
APP_NAME="hearthy-foundation"

echo "Checking Azure Web App configuration..."
echo ""

echo "1. Application Settings:"
az webapp config appsettings list \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --query "[?name=='VITE_SUPABASE_URL' || name=='VITE_SUPABASE_ANON_KEY' || name=='SUPABASE_DATABASE_URL' || name=='SPRING_PROFILES_ACTIVE'].{Name:name, Configured:value != ''}" \
  --output table

echo ""
echo "2. Application Status:"
az webapp show \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --query "{Name:name, State:state, RuntimeStack:linuxFxVersion}" \
  --output table

echo ""
echo "3. Testing Health Endpoint:"
APP_URL=$(az webapp show \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --query "defaultHostName" \
  --output tsv)

curl -s "https://${APP_URL}/actuator/health" | jq .

echo ""
echo "Configuration check complete!"
```

Run with: `chmod +x check-azure-config.sh && ./check-azure-config.sh`

## Next Steps

After configuration:

1. ✅ Verify all environment variables are set
2. ✅ Restart the application
3. ✅ Check application logs for successful startup
4. ✅ Test health endpoint
5. ✅ Test home page
6. ✅ Test API endpoints
7. ✅ Monitor application performance in Azure Portal

## Support

If issues persist after configuration:

1. Check full application logs in Azure Portal
2. Review QUICKSTART.md for deployment steps
3. Verify database connectivity from Azure
4. Check Supabase project status
5. Contact support: contact@hearthy.org
