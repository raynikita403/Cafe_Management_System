# Cafe Management System

This project is a Cafe Management System, demonstrating role-based login and core CRUD functionalities for Admin and Customer users.
The project uses Spring Boot, Spring Security with JWT, Spring Data JPA, Spring Web MVC, Thymeleaf, MySQL, Bootstrap, AJAX, and jQuery to build a dynamic and secure web application.
All user passwords are securely stored in the database in encrypted form using Spring Security.

## **Technologies Used**
- **Backend:** Spring Boot, Spring Security, Spring Data JPA, Spring Web MVC  
- **Frontend:** Thymeleaf, Bootstrap, AJAX, jQuery  
- **Database:** MySQL  
- **Authentication:** JWT Tokens for secure login, passwords stored encrypted  
- **Build Tool:** Maven  

---

## **Project Features**
- **Role-based login:** Separate access for **Admin** and **Customer**
- **Secure password storage** using encryption (Spring Security)  
- **JWT token generation for authenticated users**

## User Features ##
      Dynamic Cart Management (Customer side):
      Add/Remove products to cart dynamically
      Manage product quantity with increase/decrease buttons
      Live total calculation in cart modal
     Order Placement Flow: 
      Place order directly from the cart
      Orders saved to the database with quantity and bill details
      Cart automatically cleared after successful order
      SweetAlert2 popups for order confirmation & error handling
      Spring Boot Email Integration (User Side):
      Users can send msg to admin by using mail (Contact Us)
      Inactive User can send msg to the Admin and that msg will be display in Admin Page
      Responsive UI with Bootstrap
      Pages rendered dynamically with Thymeleaf templates

 ## Admin Features ##
      CRUD operations for products (Admin side)
      Order Table Management:
      View all orders with user, product, quantity, and bill details
      User Management: 
            View all registered users
            Activate/Deactivate users
      Sales Analytics & Charts:
            Weekly, monthly, and yearly sales charts
            Display highest-selling products 
            Interactive charts for quick analysis     
## **Learning Outcomes**
- Implementing **Spring Security with JWT** for secure role-based access  
- Understanding **Spring Data JPA entity mapping and MVC architecture**  
- Using **Thymeleaf** for dynamic front-end rendering 
- Connecting backend with **MySQL database** for CRUD operations
- Integrating SweetAlert2 for user-friendly notifications 
- Ensuring **secure password storage and authentication**
- Admin-side analytics using charts (weekly, monthly, yearly)
- Managing users dynamically (active/inactive)
- Sending emails via Spring Boot (user-side notifications)  

## **Screenshots**
### User Registration
[![User Registration](Screenshots/Registeration.png)](Screenshots/Registeration.png)

### User Login Page
[![User Login](Screenshots/Login.png)](Screenshots/Login.png)

-----Admin Dashboard-------------------------

### Admin Login
[![Admin Login](Screenshots/AdminDashboard.png)](screenshots/AdminDashboard.png)

### Add Product Form
[![Add Product Form](Screenshots/AddForm.png)](screenshots/AddForm.png)

### Update Product Form
[![Update Product Form](Screenshots/UpdateForm.png)](screenshots/UpdateForm.png)

### Order List
[![ Order List ](Screenshots/OrderList.png)](screenshots/OrderList.png)

### User Manage
[![ User Manage ](Screenshots/UserManage.png)](screenshots/UserManage.png)

### Chart
[![Chart ](Screenshots/Chart.png)](screenshots/Chart.png)

### InActiveUserMsg
[![InActiveUserMsg ](Screenshots/InactiveUserMsg.png)](screenshots/InactiveUserMsg.png)

---------------------Customer Page ----------------------------

### Customer Page
[![Customer Page](Screenshots/CustomerPage.png)](screenshots/CustomerPage.png)

### About Page
[![About Page](Screenshots/AboutPage.png)](screenshots/AboutPage.png)

### Add To Cart
[![Add To Cart](Screenshots/AddToCart.png)](screenshots/AddToCart.png)

### Orders Bills
[![Orders Bills](Screenshots/Order.png)](screenshots/Order.png)

### Order Placed
[![Order Placed](Screenshots/OrderPlaced.png)](screenshots/OrderPlaced.png)

### Inactive User 
[![Inactive User](Screenshots/InActiveUserScreen.png)](Screenshots/InActiveUserScreen.png)

### Inactive User MsgBox 
[![Inactive User](Screenshots/InActiveUserMsgBox.png)](Screenshots/InActiveUserMsgBox.png)







