# E-Commerce Stock Management System
#### OOP Java Development - Grade: 90%

## Table of Contents
- [Summary](#summary)
- [Important Files](#important-files)
- [Specification](#specification)
- [Class Diagram](#class-diagram)
- [Installation](#installation)

## Summary
A Swing GUI-based e-commerce system, allowing for admin and customer access, with various functionalities for each. Data, such as users and products, is stored in local CSV files. Object Orientated Principles are applied, and were described graphically within a UML Class diagram before the development stage.

## Important Files
1. Class Diagram.pdf -> Contains a UML Class diagram, explaining the code structure of this project.
2. Not Completed.pdf -> Explains tasks that haven't been completed according to the specification (hint: all of them were completed)
3. cas.jar -> Executable JAR file to run the Swing-based application

## Specification
Aim -> Client has requested you to design and develop a system in Java for their Computer Accessories Shop. The aim is to facilitate various shop's activities in an efficient and practical manner. 

Products Info -> The shop sells two categories of product: keyboard and mouse. Additionally, there are different types of keyboard (standard, flexible, gaming, UK layout, US layout) and mouse (standard, gaming, n number of buttons).

User Info -> System accomodates two roles: Admin and Customer
##### The admin has permission to: 
- add new product to stock,
- view all products alongside their attributes.
##### Customers have permission to:
- view all product with their attributes (w/o original cost) sorted by retail price,
- search/filter products (search by barcode or filter by mouse number of buttons)
- add items to basket,
- view and cancel basket,
- complete purchase using either Paypal or Credit Card.

Payment Info -> Two methods: Paypal and Credit Card. 
Credit Card requires a 6-digit card number and a 3-digit security code. 
PayPal requires a valid email address. 
The payment function updates the stock, clears the basket and generates a receipt (layout dependent on payment method chosen - all receipts contain amount paid, today's date, customer's address and Card Number OR PayPal email address).

## Class Diagram
<img width="960" alt="Screenshot 2024-10-08 at 12 46 20" src="https://github.com/user-attachments/assets/998d1c29-bf72-49eb-a4c1-8c7494408430">

##### You may need to enlarge your screen or download this image to view properly
**Important Details**:
1. Abstraction is used for different types of Users - Customer and Admin classes inherit from the abstract User class (Inheritance).
2. Abstraction is used for different types of Products - Mouse and Keyboard.
3. An Interface is used for the Payment Method which is implemented by Paypal and Credit Card classes (Polymorphism). The Payment Method Interface provides a way to treat different payment methods uniformly, but have differing implementations. 
4. Encapsulation is used throughout with Private/Protected/Public modifiers, and Getter and Setter methods in most classes.
5. Association, Aggregation and Composition -> see diagram.

## Installation
Ensure that you have Java installed on your local computer. You check by running the following command in Terminal: ```java -version```

To install and run this Java project, follow the instructions below:
1. **Clone this repository**:
   ```bash
   git clone https://github.com/Jeev28/Stock-Management-System-OOP.git
   
2. **Navigate to project directory**:
   In Terminal, run the following: ```cd repo-copy-name```
   This is dependent on how you save this. For example, if you cloned it into a folder
   called "Stock-Management", you would run ```cd Stock-Management```
  
3. **Run the executable JAR file**:
   Execute: ```java -jar cas.jar``` (OR the path to the JAR file). Alternatively, you can
   double click the JAR file and the GUI application should open.
   
