
# Project Description

This project aims to create a simple tool for inserting watermarks into PDF files.

## Table of Contents

* [Getting Started](#getting-started)
* [System Environment](#prerequisites)
* [Installation](#installation)
* [Usage](#usage)

## Getting Started

To get started with this project, follow these steps:

### Prerequisites

* Git
* java 17 or higher
* maven 3.8 or higher
* A PDF file to demo insert the watermark into
* A xampp for running the project

### Installation

1. Clone the repository using `git clone https://github.com/Minhduc252/pdfwatermark.git`
2. Navigate into the project directory using `cd pdfwatermark`
3. Open xampp and start the server
4. Open you browser and navigate to `http://127.0.0.1/phpmyadmin/`
5. Import the database using the sql file  `db.sql` to phpmyadmin

### Usage

1. Make sure the current directory in your terminal has the pom.xml file
2. Open xampp and start the server
3. Run the script using `mvn tomcat7:run -f "./pom.xml"`
4. Open you browser and navigate to `http://localhost:8080/pdf-watermark/`

