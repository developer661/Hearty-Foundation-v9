# Hearthy Foundation - Java Spring Boot Application

This is a Java 17 Maven-based Spring Boot web application converted from the original React/TypeScript application. It's designed to be deployed on Azure Web App.

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Maven** - Build tool
- **Thymeleaf** - Server-side template engine
- **PostgreSQL** - Database (via Supabase)
- **Tailwind CSS** - Styling (CDN)
- **Azure Web App** - Deployment platform

## Project Structure

```
hearthy-foundation/
├── src/
│   ├── main/
│   │   ├── java/org/hearthy/
│   │   │   ├── HearthyFoundationApplication.java  # Main application class
│   │   │   ├── config/                            # Configuration classes
│   │   │   │   ├── SecurityConfig.java            # Spring Security configuration
│   │   │   │   ├── SupabaseClient.java            # Supabase client wrapper
│   │   │   │   └── SupabaseConfig.java            # Supabase configuration
│   │   │   ├── controller/                        # Controllers
│   │   │   │   ├── api/                           # REST API controllers
│   │   │   │   │   ├── ContactApiController.java
│   │   │   │   │   ├── OpportunityApiController.java
│   │   │   │   │   └── VolunteerApiController.java
│   │   │   │   └── web/                           # Web page controllers
│   │   │   │       └── HomeController.java
│   │   │   ├── model/                             # Domain models
│   │   │   │   ├── ContactRequest.java
│   │   │   │   ├── Opportunity.java
│   │   │   │   ├── UserProfile.java
│   │   │   │   ├── Volunteer.java
│   │   │   │   └── VolunteerRegistration.java
│   │   │   ├── repository/                        # Data access layer
│   │   │   │   ├── ContactRequestRepository.java
│   │   │   │   ├── OpportunityRepository.java
│   │   │   │   ├── VolunteerRegistrationRepository.java
│   │   │   │   └── VolunteerRepository.java
│   │   │   └── service/                           # Business logic layer
│   │   │       ├── ContactService.java
│   │   │       ├── OpportunityService.java
│   │   │       └── VolunteerService.java
│   │   └── resources/
│   │       ├── application.properties              # Default configuration
│   │       ├── application-azure.properties        # Azure-specific configuration
│   │       ├── static/                             # Static resources (CSS, JS, images)
│   │       └── templates/                          # Thymeleaf templates
│   │           ├── index.html
│   │           ├── opportunities.html
│   │           ├── volunteer-registration.html
│   │           ├── contact.html
│   │           ├── dashboard.html
│   │           └── success.html
│   └── test/                                       # Test files
├── pom.xml                                         # Maven configuration
├── web.config                                      # Azure Web App configuration
├── .deployment                                     # Azure deployment settings
└── azure-deploy.md                                 # Deployment guide

## Features

- **Home Page**: Hero section, urgent needs display, features overview
- **Volunteer Opportunities**: Browse all available volunteer opportunities
- **Volunteer Registration**: Complete registration form with validation
- **Contact Form**: Submit inquiries and contact requests
- **Dashboard**: View statistics and recent activity
- **REST API**: Full REST API for all operations

## API Endpoints

### Opportunities
- `GET /api/opportunities` - Get all opportunities
- `GET /api/opportunities/urgent` - Get urgent opportunities
- `GET /api/opportunities/{id}` - Get opportunity by ID
- `POST /api/opportunities` - Create new opportunity

### Volunteers
- `GET /api/volunteers` - Get all volunteers
- `GET /api/volunteers/{id}` - Get volunteer by ID
- `POST /api/volunteers` - Register new volunteer
- `POST /api/volunteers/registrations` - Create volunteer registration
- `GET /api/volunteers/registrations` - Get all registrations

### Contact
- `POST /api/contact` - Submit contact request

## Local Development

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL (or use existing Supabase database)

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd hearthy-foundation
   ```

2. **Configure environment variables**
   Create a `.env` file or set environment variables:
   ```
   VITE_SUPABASE_URL=https://teshvcvyxgcljsroliux.supabase.co
   VITE_SUPABASE_ANON_KEY=your-anon-key
   SUPABASE_DB_PASSWORD=your-database-password
   ```

3. **Build the application**
   ```bash
   mvn clean package
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   Open http://localhost:8080 in your browser

## Database Configuration

The application uses Supabase (PostgreSQL) for data persistence. The database schema includes:

- `volunteers` - Basic volunteer information
- `volunteer_registrations` - Detailed volunteer registrations
- `opportunities` - Volunteer opportunities
- `contact_requests` - Contact form submissions
- `user_profiles` - User profile information

All database tables are already created via Supabase migrations.

## Building for Production

```bash
mvn clean package -DskipTests
```

The JAR file will be created in `target/hearthy-foundation-1.0.0.jar`

## Azure Deployment

See [azure-deploy.md](azure-deploy.md) for detailed deployment instructions.

Quick deployment steps:
1. Build the application: `mvn clean package`
2. Create Azure Web App with Java 17 runtime
3. Configure environment variables in Azure Portal
4. Deploy using Azure CLI, Maven plugin, or FTP
5. Access your application at: `https://<your-app-name>.azurewebsites.net`

## Environment Variables

Required environment variables:

| Variable | Description | Example |
|----------|-------------|---------|
| `VITE_SUPABASE_URL` | Supabase project URL | `https://xxx.supabase.co` |
| `VITE_SUPABASE_ANON_KEY` | Supabase anonymous key | `eyJhbGci...` |
| `SUPABASE_DB_PASSWORD` | Database password | `your-password` |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `azure` (for production) |

## Configuration Profiles

- **default**: Local development configuration
- **azure**: Production configuration for Azure Web App

## Security

The application uses Spring Security with the following configuration:
- CSRF disabled for API endpoints
- All endpoints are publicly accessible (authentication can be added as needed)
- Database Row Level Security (RLS) enforced at Supabase level

## Key Differences from React Version

1. **Server-Side Rendering**: Uses Thymeleaf instead of React for rendering
2. **Backend Framework**: Spring Boot instead of Node.js/Express
3. **Build Tool**: Maven instead of npm/Vite
4. **Deployment**: Azure Web App optimized for Java applications
5. **Static Resources**: Served directly by Spring Boot
6. **State Management**: Server-side session management instead of React state

## Monitoring and Logging

- Application logs available via `az webapp log tail`
- Health check endpoint: `/actuator/health`
- Application info: `/actuator/info`

## Troubleshooting

### Application won't start
- Verify Java 17 is installed: `java -version`
- Check Maven version: `mvn -version`
- Ensure all environment variables are set

### Database connection errors
- Verify Supabase credentials
- Check network connectivity to Supabase
- Ensure database URL is correctly formatted

### Build errors
- Clean Maven cache: `mvn clean`
- Update dependencies: `mvn dependency:resolve`
- Check for compilation errors in Java files

## Testing

Run tests:
```bash
mvn test
```

Run integration tests:
```bash
mvn verify
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

Copyright 2024 Hearthy Foundation. All rights reserved.

## Support

For issues and questions:
- Email: contact@hearthy.org
- Phone: +48 123 456 789
