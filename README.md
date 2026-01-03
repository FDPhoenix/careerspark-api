# CareerSpark – Job Search Platform
**CareerSpark** is a modern job search platform built with **React** (frontend) and **Java Spring Boot** (backend), designed to connect job seekers with employers quickly and efficiently.
The system provides a smooth user experience with strong security, scalability, and real-time interaction.

<br>

**Key Features**
- **User Authentication:** Secure registration and login with JWT and OAuth2, email verification included.
- **Job Posting Management:** Recruiter can create, update, approve, or remove job postings.
- **Advanced Job Search:** Search by keyword, category, location, experience level, and company.
- **User Roles & Permissions:** Separate access for admins, recruiters, and candidates.
- **Application Tracking:** Candidates can apply for jobs, manage applications, and receive updates.
- **Saved Jobs:** Users can bookmark job listings for later review.
- **Responsive UI:** Built with React + Vite + Tailwind, delivering a fast and intuitive interface.
- **RESTful API:** Spring Boot service layer ensures clean architecture and efficient data operations using PostgreSQL.

<br>

**Technologies Used**
- **Frontend:** TypeScript, React, Tailwind CSS
- **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security, JWT
- **Database:** PostgreSQL
- **Other:** Docker, REST API, Email service (for verification), Payment service (Momo, VNPay)

<br>

## API DOCUMENTATION
| Endpoint                                | Description                                                    |
|-----------------------------------------|----------------------------------------------------------------|
| `POST /auth/register`                   | This endpoint allows guest create account.                     |
| `POST /auth/login`                      | Allows users to log into the system using the created account. |
| `POST /auth/logout`                     | Revoke refresh token.                                          |
| `POST /auth/refresh`                    | Reissue access token using refresh token.                      |
| `POST /auth/resend-otp`                 | Resend OTP for verification.                                   |
| `POST /auth/verify-otp`                 | Verify that the OTP entered by the user is correct.            |
| `PUT /api/users/{userId}/password`      | Change user's password.                                        |
| `PATCH /api/users/{userId}`             | Deactivate user account.                                       |
| `GET /api/companies`                    | View all available companies.                                  |
| `PUT /api/companies/{companyId}`        | Update the existing company information.                       |
| `POST /api/companies`                   | Create a new company on the platform.                          |
| `GET /api/sectors`                      | View all available sectors.                                    |
| `PUT /api/sectors/{sectorId}`           | Update the existing sector information.                        |
| `POST /api/sectors`                     | Create a new sector on the platform.                           |
| `GET /api/sectors`                      | View all sector that exists on the platform.                   |
| `GET /api/sectors/{sectorId}/positions` | View all available positions of specific sector.               |
| `GET /api/positions`                    | View all available positions.                                  |
| `PUT /api/positions/{positionId}`       | Update the existing position information.                      |
| `POST /api/positions`                   | Create a new position on the platform.                         |
| `GET /api/packages`                     | View all available packages.                                   |
| `PUT /api/packages/{packageId}`         | Update the existing package information.                       |
| `POST /api/packages`                    | Create a new subscription package.                             |
| `PATCH /api/packages/{packageId}`       | Deactivate specific packages if it doesn't used by any users.  |
