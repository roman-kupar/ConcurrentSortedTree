# 🌳 CST - ConcurrentSortedTree

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Gradle](https://img.shields.io/badge/Gradle-Build-green?logo=gradle) 
![JUnit](https://img.shields.io/badge/Tests-JUnit5-yellow?logo=JUnit5)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-Optional_API-brightgreen?logo=springboot)
![Concurrency](https://img.shields.io/badge/Thread_Safe-ReentrantReadWriteLock-critical?logo=datadog)


This is my solution to Jetbrains YouTrackDB development internship project. It shows the implementation of a sorted in-memory tree with put and get methods, where keys and values are byte arrays 

## 🎥 Detailed project review

### Watch a **full review** of the project commented by me. 
Video is devided into **sections**, so please refer to **timecodes** if you are interested in any specific part. 

[![Watch the video](https://img.youtube.com/vi/q0rP4Ri3zsI/maxresdefault.jpg)](https://www.youtube.com/watch?v=q0rP4Ri3zsI)

---

## 📖 Overview

**ConcurrentSortedTree** is a Java-based, thread-safe, sorted in-memory key–value store.

- **Key/Value Type:** `byte[]`
- **Operations:**
  - `get(byte[])` → Retrieve value by key.
  - `put(byte[], byte[])` → Insert/update value by key; returns previous value if replaced.

**This repository includes:**

- ✅ Core implementation of the tree in the `cst` package.
- ✅ **Demo application:** `Example.java` simulates an issue tracker and validates concurrency.
- ✅ **Optional REST Controller:** `DBserver.java` (Spring Boot) exposes a simple API for the database.
- ✅ **JUnit tests:** Thorough correctness and concurrency validation.
- ✅ **Benchmarks:** Performance measurement utilities.

---

## 🧩 Why This Data Structure?

The implementation uses **`TreeMap`** with a custom byte-array comparator and a **`ReentrantReadWriteLock`**.

### TreeMap
- Provides **sorted structure** with `O(log n)` complexity for `get`/`put`, based on red–black tree.

### Custom Comparator
- Java arrays are compared by reference by default.
- Lexicographic, **unsigned comparator** (`a[i] & 0xFF`) ensures correct byte-wise ordering.

### ReentrantReadWriteLock
- Multiple readers (`get`) work concurrently.
- Writers (`put`) acquire the write lock exclusively.
- **Improved concurrency** over coarse `synchronized` blocks.

### Defensive Copying
- Keys/values are **copied on insert/retrieval**.
- Prevents external mutation, ensuring thread safety.

---

## 🔍 Comparison With Alternatives

| Alternative                                | Sorted | Concurrent | Notes                                                                                         |
|---------------------------------------------|:------:|:----------:|-----------------------------------------------------------------------------------------------|
| **ConcurrentHashMap**                       |   ❌   |    ✅      | Excellent concurrency, but unsorted.                                                          |
| **Collections.synchronizedSortedMap**       |   ✅   |    ⚠️      | Coarse lock blocks all readers/writers; limited scalability.                                  |
| **ConcurrentSkipListMap**                   |   ✅   |    ✅      | Good, but higher memory overhead and complexity.                                              |
| **Copy-on-Write structures**                |   ❌   |    ⚠️      | Inefficient for frequent writes; not a natural fit for key–value semantics.                   |
| **Custom balanced trees (AVL, B-Tree, etc)**|   ✅   |    ⚠️      | Possible, but complex and error-prone for scope of this task.                                 |

**Conclusion:**  
**TreeMap + ReentrantReadWriteLock** offers a pragmatic balance between simplicity, correctness, and performance.

---

## ✨ Features

- 🔒 **Thread-safe** concurrent access
- ⚡ **Sorted** storage (red–black tree)
- 🎯 **Minimal API**: `get`, `put`
- 🧪 **Unit & concurrency tests**
- 🌐 **Optional REST API** (Spring Boot)
- 🛠 **Utility helpers** (`Utf8` conversions, etc.)

---

## 🛠 Technologies

| Tech         | Use Case         |
|--------------|------------------|
| ☕ Java 17+    | Core language    |
| 🛠 Gradle     | Build tool       |
| ✅ JUnit      | Testing          |
| 🚀 Spring Boot| REST API (optional) |

---

## ⚙️ Setup

### 1. Clone the Repository

```sh
git clone https://github.com/roman-kupar/ConcurrentSortedTree.git
cd ConcurrentSortedTree
```

---

## 🚀 Quick Start

1. **Build & Test**
   ```sh
   ./gradlew build
   ./gradlew test
   ```

2. **Run Demo Application**
   ```sh
   java -cp build/classes/java/main org.example.Example
   ```

3. **Run Benchmark**
   ```sh
   java -cp build/classes/java/main org.example.TreeBenchmark
   ```

4. **Run Comparison**
   ```sh
   java -cp build/classes/java/main org.example.Comparison
   ```

5. **Start REST API (optional)**
   ```sh
   java -cp build/classes/java/main org.example.DBserver
   ```
   or (if using Spring Boot)
   ```sh
   ./gradlew bootRun
   ```

---

## 📡 REST API Usage Examples

### Using curl

- **PUT (insert/update a value):**
  ```sh
  curl -X POST http://localhost:8080/db/put \
    -H "Content-Type: application/json" \
    -d '{"key":"myKey","value":"myValue"}'
  ```
  - Response: previous value for the key (or "null" if none)

- **GET (retrieve a value):**
  ```sh
  curl http://localhost:8080/db/get/myKey
  ```
  - Response: value for the key (or 404 if not found)

### Testing POST request in Postman
1. Open Postman and create a new request.
2. Set the method to **POST** and the URL to `http://localhost:8080/db/put`.
3. Go to the **Body** tab, select **raw**, and choose **JSON** from the dropdown.
4. Enter the following JSON:
   ```json
   {
     "key": "myKey",
     "value": "myValue"
   }
   ```
5. Click **Send**. You should see the previous value (or "null") in the response.

### Testing GET request in Postman
1. Open Postman and create a new request.
2. Set the method to **GET** and the URL to `http://localhost:8080/db/get/myKey` (replace `myKey` with your actual key).
3. Click **Send**. You should see the value for the key in the response, or a 404 if not found)

---
