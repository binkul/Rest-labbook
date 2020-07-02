#LabBook APPLICATION

## 1. DESCRIPTION
The application for laboratory staff. It allows you to keep notes, prepare recipes and save prepared recipes to a database.

## 2. USED TECHNOLOGIES
- Spring Web
- Hibernate

## 3. DEMO
https://labbook-backend-api.herokuapp.com/swagger-ui.html

## 4. REQUIREMENTS
Locally installed IntelliJ or another JAVA IDE environment.

## 5. FRONTEND
Vaadin FrontEnd to this application: https://github.com/binkul/Rest-labbook-frontend.git

## 6. HOW TO RUN
1. Clone the FrontEnd -> https://github.com/binkul/Rest-labbook-frontend.git
2. Create local database and make the appropriate changes in application.properties
3. Add Your data for email client in application.properties
4. Run Backend first, then Frontend
5. In browser go to http://localhost:8081/
6. Register in API, then login -> you have 'USER' privileges
7. If You want to be an 'ADMIN':
- login with 'user: user', 'password: sata'
- go to menu 'Users' and change role for your account on 'ADMIN'
- changing 'Is observer' to true - You will receive emails from API
- log-out from user and login on Your account.

## 7. Troubleshooting 
Remember to add correct email address when You register. 
Logs will be sent to this address when you set the "Is observer" field to true.

## 8. FUTURE PLANS
- Archiving recipes
- Adding logic for CLP-calculation
