# Geek Text
Geek text is an example Spring Boot REST API for browsing and purchasing books.

## Technologies Used
- **Spring Boot**: A Java-based framework for building RESTful APIs.
- **Spring Boot Starter Web**: Provides features to build web applications, including RESTful APIs.
- **Spring Boot Starter Data JDBC**: For JDBC-based data access and repositories.
- **Flyway**: A database migration tool that helps with version control for your database.
- **PostgreSQL**: A relational database used for storing book and user data.
- **Spring Boot DevTools**: A set of tools to enhance development-time experience (e.g., live reload).

## Setup
1. Clone the repository
```bash
git clone https://github.com/AAA-Triple-AAA/geek-text.git
```
2. Add a **.env** file to root project directory with the following parameters:
```text
DB_URL=[insert JDBC connection string here]
```
**Note:** You will need to retrieve the **JDBC** connection string from your database host (i.e. Supabase, Neon, etc.).

4. Open the project in your favorite IDE and let Maven sync the dependencies
