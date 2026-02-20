# RailwayReservation
A desktop-based Railway Reservation System developed using Java Swing for the frontend and MySQL for database management. This application allows users to create accounts, log in, view available trains, book tickets, and view bookings.
📌 Project Overview

This project demonstrates the implementation of:

Java GUI using Swing

Database connectivity using JDBC

MySQL database integration

Basic CRUD operations (Create, Read)

The system provides a simple and user-friendly interface for railway ticket booking.

✨ Features

🔐 User Registration & Login Authentication

🚆 View Available Trains

🎟️ Book Train Tickets

📄 View All Bookings

🛠️ Technologies Used

Java (Swing)

JDBC

MySQL

NetBeans IDE

🗄️ Database Configuration

Database Name: TrainReservation

Required Tables
1. users

username (VARCHAR)

password (VARCHAR)

2. trains

train_id (INT, Primary Key)

train_name (VARCHAR)

3. bookings

passenger_name (VARCHAR)

train_id (INT)

🔌 Database Connection

The application connects using:

DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/TrainReservation",
    "root",
    ""
);

Make sure:

MySQL Server is running

MySQL Connector JAR is added

Database and tables are created before running

▶️ How to Run

Clone the repository

Open in NetBeans / IntelliJ

Add MySQL Connector JAR

Create database and tables

Run RailwayReservation.java

📈 Future Enhancements

Ticket cancellation functionality

Seat availability management

Train source & destination details

Date selection feature

Password encryption

Admin panel

🎯 Purpose

This project is suitable for:

Java Mini Project

DBMS Academic Project

Beginner-level Java + MySQL practice
