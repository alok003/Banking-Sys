# Banking Application

A secure and scalable **Banking Application** built using **Spring Boot** and **Microservices Architecture**. This project provides functionalities like user authentication, account management, deposits, withdrawals, money transfers, transaction notifications, and daily transaction reporting.

## Features
- **User Authentication**: Implemented using **Spring Security**, **JWT**, **OAuth**, and **Bcrypt** for enhanced security.
- **Account Management**: Secure management of user accounts, including deposits, withdrawals, and money transfers.
- **Daily Transaction Reporting**: Automated reporting of transactions using **Cron Jobs**.
- **Email Notifications**: Alerts for transactions sent directly to users via email.
- **User-Friendly Interface**: Enables users to view account statements and manage banking activities seamlessly.
- **API Testing**: Verified application functionality using **Postman**.
- **Database Management**: Efficient and scalable data handling using **MySQL**, **Hibernate**, and **JPA**.
- **Swagger Integration**: Simplified API documentation for easy understanding and testing.
- **Reduced Boilerplate Code**: Utilized **Lombok** to enhance code readability and maintainability.

## Tech Stack
- **Backend Framework**: Spring Boot
- **Security**: Spring Security, JWT, OAuth, Bcrypt
- **Database**: MySQL, Hibernate, JPA
- **Architecture**: Microservices, MVC
- **Tools**: Postman, Swagger, Lombok
- **Other**: Cron Jobs, Email Notification System

## Architecture
The application follows the **Microservices Architecture** for scalability and modularity, along with **MVC Architecture** to maintain a clear separation of concerns.

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/alok003/BankingSys.git
   cd BankingSys
   ```
2. Build the project:
  ```bash
  mvn clean install
  ```
3. Run the application:
  ```bash
  mvn spring-boot:run
  ```
Access the application at:
  API Documentation: 
  ```bash
  https://documenter.getpostman.com/view/32436386/2sAYQdkAiD
  ```
  Application: 
  ```bash
  http://localhost:8099
  ```
