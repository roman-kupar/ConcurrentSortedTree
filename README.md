# 🌳 CST - ConcurrentSortedTree

---

## 📖 Overview

**ConcurrentSortedTree** is a Java-based, thread-safe, sorted in-memory key–value store.

- **Key/Value Type:** `byte[]`
- **API Operations:**
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
| ☕ Java 17    | Core language    |
| 🛠 Gradle     | Build tool       |
| ✅ JUnit      | Testing          |
| 🚀 Spring Boot| REST API (optional) |

---

## ⚙️ Setup

### 1. Clone the Repository

```sh
git clone <repo-url>
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
   java -cp build/classes/java/main Example
   ```

3. **Start REST API (optional)**
   ```sh
   ./gradlew bootRun
   ```

---
---
