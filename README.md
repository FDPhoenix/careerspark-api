# CareerSpark – Job Search Platform
**CareerSpark** is a modern job search platform built with **React** (frontend) and **Java Spring Boot** (backend), designed to connect job seekers with employers quickly and efficiently.
The system provides a smooth user experience with strong security, scalability, and real-time interaction.

<br>

**Key Features**
- **User Authentication:** Secure registration and login with JWT; email verification included.
- **Job Posting Management:** Employers can create, update, approve, or remove job postings.
- **Advanced Job Search:** Search by keyword, category, location, experience level, and company.
- **User Roles & Permissions:** Separate access for admins, employers, and job seekers.
- **Application Tracking:** Candidates can apply for jobs, manage applications, and receive updates.
- **Saved Jobs:** Users can bookmark job listings for later review.
- **Responsive UI:** Built with React + Vite, delivering a fast and intuitive interface.
- **RESTful API:** Spring Boot service layer ensures clean architecture and efficient data operations using PostgreSQL.

<br>

**Technologies Used**
- **Frontend:** React, Vite, CSS Modules
- **Backend:** Spring Boot, Spring Data JPA, Spring Security, JWT
- **Database:** PostgreSQL
- **Other:** Docker, REST API, Email service (for verification)

<br>

## API DOCUMENTATION
| Endpoint | Description |
| --- | --- |
| `POST /auth/register`  | This endpoint allows candidate create their account           |
| `POST /auth/login`     | Allows users to log into the system using the created account |
| `POST /logout`         | Allows users to log out of the system.                        |
| `GET /jobs`            | View all available jobs                                       |
| `GET /job/:id`         | View details about a specific job                             |
