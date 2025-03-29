# Examination Management System

## Project Overview
This is an **Examination Management System** built using **Java Swing** and **MySQL**. The application provides functionalities for both **students** and **teachers** to manage examinations, results, and profiles. It ensures seamless interaction between students and teachers by providing secure authentication and a well-organized UI.

## Features
### **For Teachers**
- **Login System:** Teachers can log in securely using their credentials.
- **Exam Schedule Management:**
  - View, add, delete, and save exam schedules.
  - Changes are reflected in the MySQL database.
- **Results Management:**
  - Add, delete, and display student results.
  - Results are stored and fetched from the MySQL database.
- **Profile Page:** Displays teacher details after login.

### **For Students**
- **Login System:** Secure student authentication with roll number and password.
- **Profile Management:**
  - Displays student details after login.
  - Attractive UI for a professional look.
- **Exam Schedule:**
  - Displays exam schedules retrieved from the database.
- **Results Section:**
  - Fetches and displays student results.
  - Shows an additional row with the percentage calculation.
- **Top Rankers Section:**
  - Displays the top 3 rankers with their roll number, department, and percentage.

## Technologies Used
- **Java Swing** for GUI Development.
- **MySQL** for database management.
- **NetBeans IDE** for project development.
- **JDBC** for database connectivity.

## Installation & Setup
### **Prerequisites**
- Java Development Kit (JDK) installed.
- MySQL Server and MySQL Workbench installed.
- NetBeans IDE (Recommended) or any preferred Java IDE.

### **Steps to Run the Project**
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo-name/Examination-System.git
   ```
2. Import the project into **NetBeans**.
3. Set up the MySQL database:
   - Create a database named `JavaMini`.
   - Import the provided SQL file to create tables (`E_student`, `E_EXAMS`, `result`).
   - Update database credentials in the Java files if necessary.
4. Run the project using NetBeans.
5. Login with teacher or student credentials to access respective features.

## Database Schema
### **Tables Used:**
- **E_student**: Stores student details.
- **E_EXAMS**: Stores exam schedule.
- **result**: Stores exam results.

## Screenshots
*(Add relevant UI screenshots here)*

## Contributors
- Team **Advance Java - Group 9**

## License
This project is licensed under the **MIT License**.

## Contact
For any queries or issues, feel free to open an issue in the GitHub repository.

