#  CST - ConcurrentSortedTree


---

## 📖 Overview

**ConcurrentSortedTree** is a Java-based, thread-safe, sorted in-memory key–value store.

- Keys and values are stored as `byte[]`.
- Supported operations are:
  - `get(byte[])` → retrieve a value if present.
  - `put(byte[], byte[])` → insert or update and return the previous value if it existed.

This repository includes:

- ✅ Core implementation of the tree in cst package.
- ✅ Demo application (`Example.java`) that simulates a small issue tracker using implemented data structure and validates concurrency.
- ✅ Optional REST Controller (`DBserver.java`) using Spring Boot for showcasing API of a database.
- ✅ JUnit tests for correctness and concurrency.
- ✅ Benchmarks for data structure performance.

---

## 🧩 Why this data structure?

The implementation uses **`TreeMap`** with a **custom byte-array comparator** and a **`ReentrantReadWriteLock`**.

- **TreeMap**  
  Provides a **sorted structure** with `O(log n)` complexity for `get` and `put`, based on a red–black tree.

- **Custom Comparator**  
  Java arrays are compared by reference by default. A **lexicographic unsigned comparator** (`a[i] & 0xFF`) ensures correct byte-wise ordering.

- **ReentrantReadWriteLock**  
  Multiple readers (`get`) can work concurrently, while writers (`put`) acquire the write lock exclusively. This yields better concurrency than coarse `synchronized` blocks.

- **Defensive Copying**  
  Keys and values are **copied on insert** and **copied on retrieval**, preventing external mutation and ensuring thread safety.

---
## 🔍 Comparison with Alternatives

- **ConcurrentHashMap** offers excellent concurrency but does not preserve sorted order, making it unsuitable for a sorted tree.
- **Collections.synchronizedSortedMap(TreeMap)** → Provides thread-safety but relies on a single coarse lock, blocking all readers and writers and limiting scalability.
- **ConcurrentSkipListMap** is both concurrent and sorted, but comes with higher memory overhead and implementation complexity. My goal was a simpler, more transparent solution.
- **Copy-on-Write structures** (e.g., CopyOnWriteArrayList) → Good for read-heavy workloads but inefficient for frequent writes and lack natural key–value semantics.
- **Custom balanced trees** (AVL, B-Tree with locks) is possible but overly complex for the scope of this task and error-prone to implement correctly.

👉 Given these trade-offs, TreeMap + ReentrantReadWriteLock strikes the best balance between simplicity, correctness, and concurrent performance for this assignment.

---

## ✨ Features

- 🔒 **Thread-safe** concurrent access.
- ⚡ **Sorted** storage using a red–black tree.
- 🎯 **Minimal API surface** (`get`, `put`).
- 🧪 **Unit + concurrency tests** included.
- 🌐 **Optional REST API** with Spring Boot.
- 🛠 **Utility helpers** (e.g., `Utf8` conversions).

---

## 🛠 Technologies

- ☕ **Java 17**
- 🛠 **Gradle**
- ✅ **JUnit**
- 🚀 **Spring Boot** (optional, REST API)

---

## ⚙️ Setup

1. **Clone**
   ```sh
   git clone <repo-url>
   cd ConcurrentSortedTree
