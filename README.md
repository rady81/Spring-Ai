# Spring AI + OpenAI (Chat & Text) — REST API Demo

This repository demonstrates how to build a **Spring Boot + Spring AI** application that integrates with **OpenAI** to provide:
- ✅ **Chat completion** (prompt → assistant response)
- ✅ **Text generation** (simple text prompt → output)
- ✅ **REST APIs** to call from Postman / UI

---

## Tech Stack
- Java 17+ (works with Java 21 as well)
- Spring Boot
- Spring AI
- OpenAI (Chat Completion)
- REST APIs (JSON)

---

## Features
### 1) Chat API (REST)
Send a user message and get an AI response.
- Endpoint: `POST /api/ai/chat`
- Request: `{ "message": "..." }`
- Response: `{ "response": "..." }`

### 2) Text Generation API (REST)
Generate text output for a prompt.
- Endpoint: `POST /api/ai/text`
- Request: `{ "prompt": "..." }`
- Response: `{ "result": "..." }`

---

## Project Structure (Suggested)
