# QuizMap

QuizMap is a web application designed to address student engagement issues during class. It allows lecturers to upload lecture materials in PDF format, and it automatically generates questions based on the content using Natural Language Processing (NLP) techniques. The application is built using the Spring Boot framework and Java libraries, with Python libraries for NLP, and Thymeleaf for the front-end.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK) version 11 or higher
- Apache Maven 3.6 or higher
- Python 3.7 or higher

### Installation
1. Clone the repository: git clone `https://github.com/Khankee/QuizMap.git`
2. Navigate to the project directory: `cd QuizMap`
3. Install the required Python libraries: `pip install -r requirements.txt`

### Building the Project

1. Inside the project directory, run the following Maven command to build the project: `mvn clean install`

This command will compile the project, run tests, and create an executable JAR file in the `target` directory.

### Running the Application

1. To run the application, execute the following command from the project directory: `java -jar target/QuizMap-0.0.1-SNAPSHOT.jar`
2. Open a web browser and navigate to `http://localhost:8080` to access the application.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.