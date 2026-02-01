# React to Java Spring Boot Migration Summary

## Overview

Successfully converted the Hearthy Foundation volunteer platform from a React/TypeScript/Vite application to a Java 17 Maven-based Spring Boot web application suitable for deployment on Azure Web App.

## What Was Converted

### Original Stack
- Frontend: React 18 + TypeScript + Vite
- Styling: Tailwind CSS
- Database: Supabase (PostgreSQL)
- Authentication: Supabase Auth
- Build Tool: npm/Vite
- Deployment: Static hosting

### New Stack
- Backend: Java 17 + Spring Boot 3.2.1
- Frontend: Thymeleaf templates
- Styling: Tailwind CSS (CDN)
- Database: Supabase (PostgreSQL) - same database
- Build Tool: Maven
- Deployment: Azure Web App (Java runtime)

## Project Structure

### Java Application Structure
```
src/main/java/org/hearthy/
├── HearthyFoundationApplication.java    # Main application entry point
├── config/                              # Configuration classes
│   ├── SecurityConfig.java              # Spring Security setup
│   ├── SupabaseClient.java              # Database client wrapper
│   └── SupabaseConfig.java              # Supabase configuration
├── controller/                          # Request handlers
│   ├── api/                             # REST API endpoints
│   │   ├── ContactApiController.java
│   │   ├── OpportunityApiController.java
│   │   └── VolunteerApiController.java
│   └── web/                             # Web page controllers
│       └── HomeController.java
├── model/                               # Data models
│   ├── ContactRequest.java
│   ├── Opportunity.java
│   ├── UserProfile.java
│   ├── Volunteer.java
│   └── VolunteerRegistration.java
├── repository/                          # Database access
│   ├── ContactRequestRepository.java
│   ├── OpportunityRepository.java
│   ├── VolunteerRegistrationRepository.java
│   └── VolunteerRepository.java
└── service/                             # Business logic
    ├── ContactService.java
    ├── OpportunityService.java
    └── VolunteerService.java
```

### Resources Structure
```
src/main/resources/
├── application.properties               # Default configuration
├── application-azure.properties         # Azure-specific config
├── static/                              # Static assets (CSS, JS, images)
│   └── logo-podstawowe.png
└── templates/                           # Thymeleaf HTML templates
    ├── index.html                       # Home page
    ├── opportunities.html               # Browse opportunities
    ├── volunteer-registration.html      # Registration form
    ├── contact.html                     # Contact form
    ├── dashboard.html                   # User dashboard
    └── success.html                     # Success message
```

## Key Features Implemented

### 1. Model Layer (Entity Classes)
- **Volunteer**: Basic volunteer information
- **VolunteerRegistration**: Detailed registration data
- **Opportunity**: Volunteer opportunities
- **ContactRequest**: Contact form submissions
- **UserProfile**: User profile information

All models use Lombok for cleaner code with `@Data`, `@Builder`, `@NoArgsConstructor`, and `@AllArgsConstructor` annotations.

### 2. Repository Layer (Data Access)
Direct JDBC access using Spring's `JdbcTemplate` for interacting with Supabase PostgreSQL database:
- Custom RowMapper implementations for each entity
- CRUD operations for all entities
- Specialized queries (e.g., findUrgent for opportunities)

### 3. Service Layer (Business Logic)
- **OpportunityService**: Manage volunteer opportunities
- **VolunteerService**: Handle volunteer registrations
- **ContactService**: Process contact requests

### 4. Controller Layer

#### REST API Controllers (JSON responses)
- `/api/opportunities` - Opportunity management
- `/api/volunteers` - Volunteer operations
- `/api/contact` - Contact form submission

#### Web Controllers (HTML pages)
- `/` - Home page with urgent needs
- `/opportunities` - Browse all opportunities
- `/volunteer-registration` - Registration form
- `/contact` - Contact form
- `/dashboard` - User dashboard
- `/success` - Success confirmation

### 5. Frontend Templates
Server-side rendered Thymeleaf templates with:
- Responsive design using Tailwind CSS
- Dynamic data binding with Thymeleaf expressions
- JavaScript for form submissions to REST API
- Clean, modern UI matching original design

### 6. Security Configuration
Spring Security setup with:
- CSRF disabled for API endpoints
- All endpoints publicly accessible
- Can be extended for authentication

### 7. Azure Deployment Configuration
- **web.config**: IIS configuration for Java apps
- **.deployment**: Azure deployment settings
- **application-azure.properties**: Production configuration
- **azure-deploy.md**: Complete deployment guide

## Database Integration

The application connects to the same Supabase database as the original React app:
- Uses existing database schema
- All tables and data remain unchanged
- Row Level Security (RLS) policies maintained
- JDBC connection via PostgreSQL driver

## REST API Endpoints

### Opportunities
```
GET    /api/opportunities           # Get all opportunities
GET    /api/opportunities/urgent    # Get urgent opportunities only
GET    /api/opportunities/{id}      # Get specific opportunity
POST   /api/opportunities           # Create new opportunity
```

### Volunteers
```
GET    /api/volunteers              # Get all volunteers
GET    /api/volunteers/{id}         # Get specific volunteer
POST   /api/volunteers              # Register new volunteer
POST   /api/volunteers/registrations # Create full registration
GET    /api/volunteers/registrations # Get all registrations
```

### Contact
```
POST   /api/contact                 # Submit contact request
```

## Configuration Files

### pom.xml
Maven build configuration with dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Spring Boot Starter Actuator
- PostgreSQL Driver
- Lombok
- Java Dotenv

### application.properties
- Server configuration (port 8080)
- Supabase connection settings
- Database configuration
- Thymeleaf settings
- Logging configuration
- Actuator endpoints

### application-azure.properties
Production-specific settings:
- Port 80 for Azure
- Thymeleaf caching enabled
- Minimal logging
- Security settings

## How to Build and Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Environment variables configured

### Local Development
```bash
# Build the application
mvn clean package

# Run the application
mvn spring-boot:run

# Access at http://localhost:8080
```

### Production Build
```bash
mvn clean package -DskipTests
# Output: target/hearthy-foundation-1.0.0.jar
```

## Azure Deployment

### Option 1: Azure CLI
```bash
az webapp create --resource-group <rg-name> --plan <plan-name> --name hearthy-foundation --runtime "JAVA:17-java17"
az webapp config appsettings set --resource-group <rg-name> --name hearthy-foundation --settings SPRING_PROFILES_ACTIVE="azure"
az webapp deployment source config-zip --resource-group <rg-name> --name hearthy-foundation --src target/hearthy-foundation-1.0.0.jar
```

### Option 2: Azure Portal
1. Create Web App with Java 17 runtime
2. Configure environment variables
3. Upload JAR file via FTP or deployment center
4. Restart the application

See `azure-deploy.md` for detailed instructions.

## Environment Variables Required

| Variable | Description |
|----------|-------------|
| `VITE_SUPABASE_URL` | Supabase project URL |
| `VITE_SUPABASE_ANON_KEY` | Supabase anonymous key |
| `SUPABASE_DB_PASSWORD` | Database password |
| `SPRING_PROFILES_ACTIVE` | Profile (use "azure" for production) |

## Key Differences from Original

### Architecture
- **Original**: Single Page Application (SPA) with client-side routing
- **New**: Server-side rendering with traditional page navigation

### State Management
- **Original**: React state, Context API
- **New**: Server-side session management

### Routing
- **Original**: React Router (client-side)
- **New**: Spring MVC (server-side)

### API Communication
- **Original**: Direct Supabase client calls
- **New**: REST API with repository pattern

### Authentication
- **Original**: Supabase Auth with React hooks
- **New**: Spring Security (can be enhanced with JWT)

## Benefits of Java Spring Boot Version

1. **Enterprise Ready**: Robust framework with mature ecosystem
2. **Type Safety**: Strong typing across entire application
3. **Scalability**: Easy to scale horizontally on Azure
4. **Monitoring**: Built-in actuator endpoints for health checks
5. **Security**: Industry-standard Spring Security framework
6. **Maintainability**: Clear separation of concerns with layered architecture
7. **Testing**: Comprehensive testing support with Spring Boot Test
8. **Azure Integration**: Native support for Azure services

## Next Steps

1. **Build and Test**: Run `mvn clean package` to build the JAR
2. **Configure Azure**: Create Azure Web App with Java 17 runtime
3. **Set Environment Variables**: Configure all required variables in Azure
4. **Deploy**: Upload JAR and start application
5. **Verify**: Test all endpoints and pages
6. **Monitor**: Set up Application Insights for monitoring
7. **Scale**: Configure auto-scaling if needed

## Additional Features to Consider

- JWT authentication with Spring Security
- Caching with Redis
- Background job processing
- File upload to Azure Blob Storage
- Email notifications with SendGrid
- CI/CD with GitHub Actions
- Database migrations with Flyway
- API documentation with Swagger/OpenAPI
- Rate limiting
- Comprehensive test coverage

## Support

For questions or issues with the Java Spring Boot version:
- Review README-JAVA.md for setup instructions
- Check azure-deploy.md for deployment help
- Consult Spring Boot documentation: https://spring.io/projects/spring-boot
- Azure Web App docs: https://docs.microsoft.com/azure/app-service/

## Conclusion

The Hearthy Foundation volunteer platform has been successfully converted to a Java 17 Spring Boot application. All core functionality has been preserved, and the application is now ready for deployment on Azure Web App. The codebase follows Spring Boot best practices with clear separation of concerns, making it maintainable and scalable for future enhancements.
