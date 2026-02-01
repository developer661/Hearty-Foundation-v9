# Detailed Azure Portal Configuration Steps

## Step-by-Step Guide: Adding Application Settings in Azure Portal

### Prerequisites
- Azure account with active subscription
- Web App already created and deployed
- Your application JAR file uploaded to the Web App

---

## Step 1: Access Azure Portal

1. Open your web browser
2. Navigate to: **https://portal.azure.com**
3. Sign in with your Azure credentials

---

## Step 2: Navigate to Your Web App

### Option A: Using Search Bar (Fastest)
1. At the top of the Azure Portal, you'll see a search bar that says **"Search resources, services, and docs"**
2. Click on the search bar
3. Type your app name (e.g., `hearthy-foundation`)
4. Under **Resources**, click on your Web App name
   - It will have an icon that looks like a globe/server
   - Type: App Service

### Option B: Using App Services Menu
1. On the left sidebar, click **"App Services"**
   - If you don't see it, click the hamburger menu (☰) at the top left
   - Or click **"All services"** and search for "App Services"
2. You'll see a list of all your App Services
3. Click on your Web App name (e.g., `hearthy-foundation`)

---

## Step 3: Access Configuration Settings

Once you're on your Web App's page:

1. **Look at the left sidebar** - You'll see a menu with many options
2. **Find the "Settings" section** in the left sidebar menu
   - It's usually in the middle of the menu
   - Look for a section header labeled **"Settings"**
3. **Click on "Configuration"** under the Settings section
   - Icon: Usually looks like a gear or settings icon
   - This opens the Configuration page

**What you'll see:**
- The Configuration page will open
- You'll see several tabs at the top:
  - **Application settings** (this is what we need)
  - Connection strings
  - General settings
  - Path mappings
  - Default documents

---

## Step 4: Add Application Settings

### 4.1: Navigate to Application Settings Tab

1. **Click on the "Application settings" tab** at the top
   - This is usually the first tab and may already be selected
2. You'll see a page that shows:
   - A search box at the top
   - A list of existing application settings (if any)
   - A button that says **"+ New application setting"**

### 4.2: Add First Setting - VITE_SUPABASE_URL

1. **Click the "+ New application setting" button**
   - It's usually a blue button near the top of the list

2. **A dialog box will appear** with two fields:
   - **Name** (text box)
   - **Value** (text box)

3. **Fill in the first setting:**
   - **Name**: `VITE_SUPABASE_URL`
   - **Value**: `https://amoumlarjqzihcfoardl.supabase.co`

4. **Click "OK"** at the bottom of the dialog box
   - The dialog will close
   - You'll see the new setting appear in the list

### 4.3: Add Second Setting - VITE_SUPABASE_ANON_KEY

1. **Click "+ New application setting" again**

2. **Fill in:**
   - **Name**: `VITE_SUPABASE_ANON_KEY`
   - **Value**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFtb3VtbGFyanF6aWhjZm9hcmRsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njk2MjcyNjEsImV4cCI6MjA4NTIwMzI2MX0.h-mQotIBfLacqr0CddU3ePTf_iczPMrD8ujjHXz76x8`

3. **Click "OK"**

### 4.4: Add Third Setting - SUPABASE_DATABASE_URL

1. **Click "+ New application setting" again**

2. **Fill in:**
   - **Name**: `SUPABASE_DATABASE_URL`
   - **Value**: `jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.amoumlarjqzihcfoardl&password=M7Qx6sseJ4V!NLY`

3. **Click "OK"**

### 4.5: Add Fourth Setting - SPRING_PROFILES_ACTIVE

1. **Click "+ New application setting" again**

2. **Fill in:**
   - **Name**: `SPRING_PROFILES_ACTIVE`
   - **Value**: `azure`

3. **Click "OK"**

---

## Step 5: Save the Configuration

**CRITICAL**: After adding all settings, you MUST save them:

1. **Look at the top of the Configuration page**
   - You'll see a command bar with buttons
2. **Click the "Save" button**
   - Icon: Usually looks like a floppy disk
   - Color: Usually blue
3. **A confirmation dialog will appear** asking:
   - "Save configuration changes?"
   - "Changes to configuration settings might restart the application"
4. **Click "Continue"**
   - This will save all your settings
   - The web app will automatically restart

**What happens:**
- A notification will appear: "Successfully updated web app settings"
- The settings are now saved
- The application will restart (takes 30-60 seconds)

---

## Step 6: Verify Settings Were Added

To double-check your settings were saved:

1. **Stay on the Configuration page**
2. **Look at the list of Application settings**
3. **You should see all 4 settings:**
   - `SPRING_PROFILES_ACTIVE`
   - `SUPABASE_DATABASE_URL`
   - `VITE_SUPABASE_ANON_KEY`
   - `VITE_SUPABASE_URL`

4. **Each setting should show:**
   - Name (left column)
   - Value (middle column) - may be masked with dots for security
   - Actions (right column) - Edit/Delete buttons

---

## Step 7: Restart the Application

After saving settings, it's good practice to manually restart:

1. **Go back to the Overview page**
   - Click "Overview" in the left sidebar
   - It's usually the first item in the menu

2. **Find the command bar at the top** with buttons

3. **Click the "Restart" button**
   - It may say "Restart" or have a circular arrow icon
   - If prompted, click "Yes" to confirm

4. **Wait 2-3 minutes** for the application to fully restart

---

## Step 8: Verify Application is Running

### 8.1: Check Application Status

1. **On the Overview page**, look at the top section
2. **Find the "Status" field**
   - Should show: **"Running"**
   - Color: Green

### 8.2: Test the Health Endpoint

1. **On the Overview page**, find your **URL**
   - It's usually near the top
   - Format: `https://your-app-name.azurewebsites.net`

2. **Copy the URL**

3. **Open a new browser tab** and visit:
   ```
   https://your-app-name.azurewebsites.net/actuator/health
   ```

4. **You should see:**
   ```json
   {"status":"UP"}
   ```

### 8.3: Test the Home Page

1. **In your browser**, visit:
   ```
   https://your-app-name.azurewebsites.net/
   ```

2. **You should see:**
   - The Hearthy Foundation home page
   - Logo and navigation
   - Hero section
   - Urgent needs section

---

## Troubleshooting

### Issue: I don't see the "Configuration" menu item

**Solution:**
- Make sure you're on the correct Web App resource
- Try refreshing the Azure Portal page
- Check you have proper permissions (Contributor or Owner role)

### Issue: The "+ New application setting" button is grayed out

**Solution:**
- You may not have permission to edit settings
- Contact your Azure subscription administrator
- Check if the app is running (can't edit during deployment)

### Issue: Settings are not saving

**Solution:**
- Make sure you clicked the "Save" button at the top
- Check for any error messages in red at the top of the page
- Try adding settings one at a time
- Check your internet connection

### Issue: Application still won't start after adding settings

**Solution:**
1. **Check the Log Stream:**
   - In left sidebar, go to "Monitoring" → "Log stream"
   - Look for error messages in the logs

2. **Verify all 4 settings are present:**
   - Go back to Configuration → Application settings
   - Count that you have exactly 4 settings

3. **Check for typos:**
   - Setting names are case-sensitive
   - No extra spaces in names or values

4. **Verify the database password:**
   - The most common issue is wrong database password
   - Double-check the password in `SUPABASE_DATABASE_URL`

### Issue: How to find my Supabase database password?

**Solution:**
1. Go to https://supabase.com
2. Log in to your account
3. Select your project (teshvcvyxgcljsroliux)
4. Go to Project Settings → Database
5. Look for "Connection string" or "Database password"
6. Copy the password and use it in the `SUPABASE_DATABASE_URL` setting

---

## Visual Guide: Key Locations in Azure Portal

### Where is the Configuration Menu?
```
Azure Portal
  └── Your Web App
      └── Left Sidebar
          ├── Overview (at top)
          ├── Activity log
          ├── Access control (IAM)
          ├── Tags
          ├── ...
          ├── SETTINGS (section header)
          │   ├── ⚙️ Configuration  ← YOU WANT THIS
          │   ├── TLS/SSL settings
          │   ├── Custom domains
          │   ├── ...
          └── ...
```

### What does the Configuration page look like?
```
Configuration
├── [Application settings] [Connection strings] [General settings] [Path mappings]
│
├── [+ New application setting] [Advanced edit] [🔍 Search]
│
└── Application settings (4)
    ├── Name                      | Value                    | [Edit] [Delete]
    ├── SPRING_PROFILES_ACTIVE    | azure                    | [Edit] [Delete]
    ├── SUPABASE_DATABASE_URL     | jdbc:postgresql://...   | [Edit] [Delete]
    ├── VITE_SUPABASE_ANON_KEY    | eyJhbGciOi...           | [Edit] [Delete]
    └── VITE_SUPABASE_URL         | https://teshvcvyx...    | [Edit] [Delete]

[Save] [Discard]  ← IMPORTANT: Click Save!
```

---

## Quick Reference: All Settings to Add

Copy and paste these exact values:

| Name | Value |
|------|-------|
| `VITE_SUPABASE_URL` | `https://amoumlarjqzihcfoardl.supabase.co` |
| `VITE_SUPABASE_ANON_KEY` | `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFtb3VtbGFyanF6aWhjZm9hcmRsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njk2MjcyNjEsImV4cCI6MjA4NTIwMzI2MX0.h-mQotIBfLacqr0CddU3ePTf_iczPMrD8ujjHXz76x8` |
| `SUPABASE_DATABASE_URL` | `jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.amoumlarjqzihcfoardl&password=M7Qx6sseJ4V!NLY` |
| `SPRING_PROFILES_ACTIVE` | `azure` |

---

## Alternative: Using Advanced Edit

If you prefer to add all settings at once:

1. **On the Configuration page**, click **"Advanced edit"** button
   - It's next to "+ New application setting"

2. **You'll see a JSON editor**

3. **Replace the content with:**

```json
[
  {
    "name": "VITE_SUPABASE_URL",
    "value": "https://amoumlarjqzihcfoardl.supabase.co",
    "slotSetting": false
  },
  {
    "name": "VITE_SUPABASE_ANON_KEY",
    "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFtb3VtbGFyanF6aWhjZm9hcmRsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njk2MjcyNjEsImV4cCI6MjA4NTIwMzI2MX0.h-mQotIBfLacqr0CddU3ePTf_iczPMrD8ujjHXz76x8",
    "slotSetting": false
  },
  {
    "name": "SUPABASE_DATABASE_URL",
    "value": "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.amoumlarjqzihcfoardl&password=M7Qx6sseJ4V!NLY",
    "slotSetting": false
  },
  {
    "name": "SPRING_PROFILES_ACTIVE",
    "value": "azure",
    "slotSetting": false
  }
]
```

5. **Click "OK"**

6. **Click "Save"** at the top

---

## Success Checklist

After completing all steps, verify:

- [ ] All 4 application settings are visible in Azure Portal
- [ ] Settings have been saved (clicked "Save" button)
- [ ] Application has been restarted
- [ ] Status shows "Running" on Overview page
- [ ] Health endpoint returns `{"status":"UP"}`
- [ ] Home page loads successfully
- [ ] No errors in Log stream

---

## Need Help?

If you're stuck at any step:

1. **Take a screenshot** of what you see
2. **Note which step you're on**
3. **Check the Troubleshooting section** above
4. **Review the Log stream** for error messages
5. **Contact support** with the screenshot and step number

---

## Summary

You configured 4 application settings in Azure Portal:
1. ✅ Supabase URL
2. ✅ Supabase API key
3. ✅ Database connection string
4. ✅ Spring profile

Your Java Spring Boot application should now start successfully and connect to your Supabase database!

**Next Steps:**
- Monitor application logs
- Test all endpoints
- Set up Application Insights for monitoring
- Configure custom domain (optional)
- Set up CI/CD pipeline (optional)
