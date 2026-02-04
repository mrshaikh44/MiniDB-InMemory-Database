# MiniDB – Command-Driven In-Memory Database (Java)

## Overview
MiniDB is a command-driven, thread-safe in-memory database implemented in Java.  
It supports TTL (Time-To-Live), concurrent access, and lifecycle control using commands.

This project was developed as a mini project to demonstrate:
- OOP principles
- Multithreading
- Synchronization & concurrency
- Custom exception handling

---

## Features
- Integer keys with generic values
- Command-based interaction (`PUT`, `GET`, `DELETE`, `START`, `STOP`)
- TTL support for automatic key expiration
- Lazy expiration + background cleanup thread
- Thread-safe using `ConcurrentHashMap`
- Clean exception handling



