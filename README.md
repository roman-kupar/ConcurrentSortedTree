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
