# ConcurrentSortedTree Project

## Overview
ConcurrentSortedTree is a Java-based project implementing a thread-safe, sorted tree data structure. It provides efficient concurrent operations for insertion and retrieval, and is designed for use in high-performance applications. The project also includes RESTful API endpoints and basic database integration for demonstration purposes.

## Features
- **ConcurrentSortedTree**: A custom implementation of a concurrent sorted tree supporting thread-safe put/get operations.
- **REST API**: Exposes endpoints for database-like operations via Spring Boot (if enabled).
- **Utility Classes**: Includes helper classes such as Utf8 for encoding/decoding.
- **Unit Tests**: Comprehensive JUnit tests for correctness and concurrency.

## Technologies Used
- Java 17
- Gradle 
- JUnit 
- Spring Boot (optional, for REST API)

## Setup Instructions
1. **Clone the repository**
   ```sh
   git clone <repo-url>
   cd ConcurrentSortedTree
   ```
2. **Build the project**
   ```sh
   ./gradlew build
   ```
3. **Run the application**
   - To run the main class:
     ```sh
     ./gradlew run
     ```
   - To start the REST API (if Spring Boot is configured):
     ```sh
     ./gradlew bootRun
     ```

## Usage
- **ConcurrentSortedTree**
  - Instantiate and use the tree in your Java code:
    ```java
    IConcurrentSortedTree tree = new ConcurrentSortedTree();
    tree.put(Utf8.bytesOf("key"), Utf8.bytesOf("value"));
    Optional<byte[]> value = tree.get(Utf8.bytesOf("key"));
    ```
- **REST API**
  - Access endpoints via HTTP requests (see `DatabaseController.java` for details).

## Testing
Run all unit tests with:
```sh
./gradlew test
```
Test reports are available in `build/reports/tests/test/index.html`.

## Contribution Guidelines
Feel free to fork the repository and submit pull requests. Please ensure all new code is covered by unit tests.

## License
This project is licensed under the MIT License. See `LICENSE` for details.

