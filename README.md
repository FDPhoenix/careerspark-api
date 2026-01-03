# CareerSpark – Job Search Platform
**CareerSpark** is a modern job search platform built with **React** (frontend) and **Java Spring Boot** (backend), designed to connect job seekers with employers quickly and efficiently.
The system provides a smooth user experience with strong security, scalability, and real-time interaction.

<br>

**Key Features**
- **Authentication & Authorization:** Secure registration and login with Spring Security, JWT and OAuth2; email verification included.
- **Job Posting Management:** Recruiters can create, update, and remove job postings; admins approve postings.
- **Advanced Job Search:** Search by keyword, category, location, experience level, and company.
- **User Roles & Permissions:** Separate access for admins, recruiters, and candidates.
- **Application Tracking:** Candidates can apply for jobs, manage applications, and receive updates.
- **Saved Jobs:** Users can bookmark job listings for later review.
- **Responsive UI:** Built with React + Vite + Tailwind, delivering a fast and intuitive interface.
- **RESTful API:** Spring Boot service layer ensures clean architecture and efficient data operations using PostgreSQL.

<br>

**Technologies Used**
- **Frontend:** TypeScript, React, Tailwind CSS
- **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security, Flyway.
- **Database:** PostgreSQL
- **Other:** Docker, REST API, Email service (for verification), Payment service (Momo, VNPay).

<br>

## API DOCUMENTATION

- **GUEST:** User without logged in.
- **CANDIDATE:** Job seekers.
- **RECRUITER:** Employer.
- **ADMIN:** System administration.
- **AUTHENTICATED:** Logged-in users (CANDIDATE, RECRUITER, ADMIN).
- **PUBLIC:** Accessible without authentication.

| Endpoint                                       | Role                                  | Description                                                    |
|------------------------------------------------|---------------------------------------|----------------------------------------------------------------|
| `POST /auth/register`                          | PUBLIC                                | This endpoint allows guests to create an account.              |
| `POST /auth/login`                             | PUBLIC                                | Allows users to log into the system using the created account. |
| `POST /auth/logout`                            | AUTHENTICATED                         | Revoke refresh token.                                          |
| `POST /auth/refresh`                           | PUBLIC (REQUIRES VALID REFRESH TOKEN) | Reissue access token using refresh token.                      |
| `POST /auth/resend-otp`                        | PUBLIC                                | Resend OTP for verification.                                   |
| `POST /auth/verify-otp`                        | PUBLIC                                | Verify that the OTP entered by the user is correct.            |
| `PUT /api/users/{userId}/password`             | AUTHENTICATED (OWNER)                 | Change user's password.                                        |
| `PATCH /api/users/{userId}/active`             | ADMIN                                 | Deactivate user account.                                       |
| `GET /api/companies`                           | ADMIN                                 | View all companies.                                            |
| `GET /api/companies?approved=true`             | PUBLIC                                | View all approved companies.                                   |
| `PUT /api/companies/{companyId}`               | RECRUITER (OWNER)                     | Update the existing company information.                       |
| `POST /api/companies`                          | RECRUITER                             | Create a new company on the platform.                          |
| `GET /api/sectors`                             | ADMIN                                 | View all sectors that exist on the platform.                   |
| `GET /api/sectors?available=true`              | PUBLIC                                | View all available sectors.                                    |
| `GET /api/sectors?available=true&popular=true` | PUBLIC                                | View all popular and available sectors.                        |
| `PUT /api/sectors/{sectorId}`                  | ADMIN                                 | Update the existing sector information.                        |
| `POST /api/sectors`                            | ADMIN                                 | Create a new sector on the platform.                           |
| `GET /api/sectors/{sectorId}/positions`        | PUBLIC                                | View all available positions of specific sector.               |
| `GET /api/positions`                           | PUBLIC                                | View all available positions.                                  |
| `PUT /api/positions/{positionId}`              | ADMIN                                 | Update the existing position information.                      |
| `POST /api/positions`                          | ADMIN                                 | Create a new position on the platform.                         |
| `GET /api/packages`                            | PUBLIC                                | View all available packages.                                   |
| `PUT /api/packages/{packageId}`                | ADMIN                                 | Update the existing package information.                       |
| `POST /api/packages`                           | ADMIN                                 | Create a new subscription package.                             |
| `PATCH /api/packages/{packageId}/status`       | ADMIN                                 | Deactivate a subscription package.                             |
