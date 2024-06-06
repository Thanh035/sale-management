Project Document
Sales management system
 
- Hanoi, 3/2024 -

Project’s goal
The Sales Management System is a software developed to assist businesses in managing the sales process from order intake, transaction processing, to report generation and business performance analysis.
Efficient sales management is a decisive factor in a business's success. However, traditional sales management methods often have limitations, such as low efficiency, confusion, and the demand for significant human resources and capabilities.
Therefore, in today's business environment, the need for an automated, flexible, and user-friendly sales management system is extremely critical. This system will help improve the order recording process, enhance interaction with customers, provide quick feedback to businesses, and optimize sales performance.
The project's goal is to develop a comprehensive sales management system, including key features such as customer management, employee management, product management, order management, inventory management, and business performance reporting.
The system will address sales management issues, making it easy for businesses to manage their sales systems. It will provide flexibility, high work efficiency, and help manage necessary sales information without requiring significant human resources. It will also address issues of inaccuracies leading to significant cost losses for businesses. The system provides detailed feedback and analysis on business performance, aiding in effective strategic decision-making.
The system ensures user-friendliness, easy maintenance with low system management costs, suitable for various sales formats. It will enhance customer experience through better support services and quick order processing times.

The current system
Some current systems with common objectives that we have researched include: Salesforce Sales Cloud, Zoho CRM, HubSpot CRM, Microsoft Dynamics 365 Sales. These systems all have their own advantages and limitations:

Salesforce Sales Cloud:
Pros:
User-friendly interface.
Strong integration with other applications and services, such as Marketing Cloud and Service Cloud.
Offers rich features from customer management to data analysis.
Cons:
High cost, especially for small and newly established businesses.
Requires time and resources for training to fully utilize its features.

Zoho CRM:
Pros:
Affordable pricing, especially for small businesses.
Well-integrated with other applications within the Zoho ecosystem.
Provides flexible and customizable features to meet specific business needs.
Cons:
Interface may not be as modern and intuitive as competitors'.
Limited scalability and customization for larger enterprises with complex needs.

HubSpot CRM:
Pros:
Free and easy to deploy.
Well-integrated with HubSpot's marketing and sales tools.
User-friendly interface.
Cons:
Limited features compared to paid solutions.
Not suitable for businesses with more complex sales and marketing needs.

Microsoft Dynamics 365 Sales:
Pros:
Tight integration with other applications and services within the Dynamics 365 ecosystem.
Offers flexible and customizable features to meet specific business needs.
Strong support from Microsoft and user community.
Cons:
High deployment and maintenance costs for small businesses.
Requires deep knowledge to deploy and fully utilize its features.

Considering the limitations of these systems, our team aims to design a sales management system with low cost, user-friendly interface, rich features, suitable for small and medium-sized businesses, and scalable to larger enterprises in the future.

The proposed system
Boundaries of the system
The project scope will include:
Requirements analysis and system design.
Software development and deployment.
User training and support.
Maintenance and system upgrades post-deployment.
User target:
The system is intended for businesses in retail, wholesale, and e-commerce sectors, including small and medium-sized enterprises.

Hardware and software requirement
Hardware: 
RAM: Minimum 4GB RAM.
ROM: Minimum 128GB storage capacity.
CPU: Minimum an Intel Core i5 or equivalent processor.
Operating System: Windows 10 or macOS.
Software:
Language: java, typescript
Framework: bootstrap,Angular,Spring
Database: postgreSQL
Security: JWT
Customer Requirements Specification
Customer can:
		+ View and edit personal information
		+ Viewing products
		+ Add products in cart
		+ Order products
		+ Choosing payment method
		+ Check order information
		+ Check bill
		+ Giving feedback
Employee can:
+ View and edit personal information
		+ Viewing products
		+ Selling products
		+ Customer management (CRUD)
		+ Orders management (CRUD)
		+ Bill management (CRUD)
Manager can:
+ View and edit personal information
		+ Products/Inventory management (CRUD)
		+ Customer management (CRUD)
		+ Orders management (CRUD)
		+ Employee management (CRUD)
		+ Bill management (CRUD)
		+ View the revenue, cost
Admin can:
		+ Assign permissions to accounts (role Manager, Employee, Customer)
		+ Running and maintaining the websites
+ View and edit personal/store information
		+ Products/Inventory management (CRUD)
		+ Add products in cart
		+ Order products
+ Choosing payment method
		+ Check order information
		+ Check bill
		+ Giving feedback
		+ Customers management (CRUD)
		+ Orders management (CRUD)
		+ Employees management (CRUD)
		+ Managers management (CRUD)
		+ Bill management (CRUD)
		+ View the revenue, cost
System Designs
Entity Relationship Diagram


Database Design


Sitemap
Employee / Manager / Admin



Customer

System functions design
Sale managing flowchart


Login/Register Flowchart

Creating orders flowchart

Creating products flowchart

Validation Checklists
Login (Employee) function

+ Enter information about: username, password
+ This information can’t be empty. If left empty, send the error message "Please enter username / Please enter password".
+ After entering all the information, press the Login button and system will:
      	- Check if the username exists or not, if it does not exist, send error message "This username hasn't been existed"
      	- If the username is correct, check to see if the password matches the data in the database. If not, send error message "Incorrect password".
- If all the information is correct, open the Home page (for Employee).
Return Password (Employee) fucntion

+ Enter Email information
+ This information cannot be empty. If it is empty, can not press Send button
+ After entering information, press the Send button and system will:
- Check if the email exists or not, if it does not exist, send error message "This email hasn't been existed"
- Check if the email is linked to any account in the database, if not, error message "This email is not linked with any accounts"
- If the information is correct, send request to Admin/Manager.

+ Admin/Manager press button “Send password recovery email to account” and the email have password recovery will send to user's email address.
Login (Customer) function

+ Enter information about: email address, password
+ This information can’t be empty. If left empty, send the error message "Please enter email address / Please enter password".
+ After entering all the information, press the Login button and system will:
      	- Check if the email address exists or not, if it does not exist, send error message "This email address hasn't been existed"
      	- If the email address is correct, check to see if the password matches the data in the database. If not, send error message "Incorrect password".
- If all the information is correct, open the Home page (for customer).
Register (Customer) function

+ Enter information about: fullname, email address, phone number, password
+ This information can’t be empty. If left empty, send the error message "Please enter fullname / Please enter email address/ ...".
+ After entering all the information, press the Register button and system will:
      	- Check if the email address exists or not, if it exists, send error message "This email address has existed"
- If the information is correct, save the data into the table and:
+ Display success message
           		+ Return to the Login page (for customer)
Add customer function

+ Enter information about: fullname, email address, phone number, address, delivery phone number,...
+ Delivery phone number can be null, other	 information cannot be empty. If left empty, the error message "Please enter fullname / Please enter email address/ ....."
+ After entering all the information, press the Save button and system will:
      - Check to see if the email or phone number already exists. If it exists, report the error "This email / phone number has existed" and delete the previously entered email / phone number.
      - If the information is correct, save the data into the table and:
           + Display success message
           + Return to the Customer Management page
Update account information

+ Enter information about: fullname, phone number 
+ This information cannot be empty. If left empty, the error message "Please enter fullname / Please enter phone number."
+ After entering all the information, press the Update button and system will:
           + Display success message
           + Return to the User information page

Add user function

+ Enter full information about: fullname, email address
+ This information cannot be empty. If left empty, the error message "Please enter fullname / Please enter email"
+ After entering all the information, press the Save button and system will:
      - Check to see if the email already exists. If it exists, report the error "This email has existed" and delete the previously entered email.
      - If the information is correct, save the data into the table and:
           + Display success message
           + Return to the User Management page
Edit store information function

+ Enter full information about: store name, email address, store address, phone number, business license registration name of the enterprise
+ This information cannot be empty. If left empty, the error message "Please enter store name / Please enter email address/ ...."
+ After entering all the information, press the Save button and system will:
           - Display success message
           - Return to the Home page
Add product function

+ Input the informations about the products: name, price, category, supplier, description, image
+ These informations must not be null, if any field except for description is left null, inform the error “Please enter name / Please enter price/ ....”
+ After the informations are inputted, choose Save:
Check if the name of the product is already existed or not, if it is existed, inform the error “This product has existed”
If the informations are right, save to the database and the system will:
Display the Success alert
Return to home page
Add category function

+ Input the name and description of the category, if the name field is null, inform the error “Please enter category name”
+ Check whether the name of the category is already existed or not, if it is existed, inform the error “This category has existed”
+ If the informations are inputted, save to the database and the system will display the Success alert, return to home page
Update category function
+ Find by name and display all the product filtered by name
+ Choose the existing category to update
+ Change the name of the category, if the name field is null or existed, inform the error
+ If the informations are right, update to the database and the system will:
Display the Success alert
Return to home page


Add order function

+ Find the name of the product and choose the product, if this field is null, inform the error. Add the description if necessary
+ Choose the product, input the amount, the system will calculate the price, discount, shipping cost, advance pay and the final cost
+  Choose either paid or pay later and save the draft
+ If the informations are right, update to the database and the system will display the Success alert and return to home page
