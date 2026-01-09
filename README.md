Spring AI + OpenAI (Chat & Text) — REST API Demo
This repository demonstrates how to build a Spring Boot + Spring AI application that integrates with OpenAI to provide:

✅ Chat completion (prompt → assistant response)
✅ Text generation (simple text prompt → output)
✅ REST APIs to call from Postman / UI
Tech Stack
Java 17+ (works with Java 21 as well)
Spring Boot
Spring AI
OpenAI (Chat Completion)
REST APIs (JSON)
Setup
1) Create OpenAI API Key
   Create an API key from OpenAI and store it safely.

2) Configure Environment Variable (Recommended)
   Windows PowerShell:

setx OPENAI_API_KEY "your_openai_api_key_here" (which we had added in .properties file)

----

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
run:
mvn clean spring-boot:run
http://localhost:8080
http://localhost:8080/ask-ai?prompt=what is your name
http://localhost:8080/images/generate?prompt=The Cup of cofee

Notes / Best Practices
--------------
- Do NOT commit API keys into GitHub.
- Use environment variables or secrets management.
- Add .gitignore for .env, application-local.yml, IDE files.
----

Improved version: (Tricky Text Chat Flow (Answer → Critique → Improve))
-----
## What this flow does

When you call the endpoint:

1) **Draft**: LLM generates an initial answer
2) **Critique**: LLM (as a strict reviewer persona) reviews the draft for gaps, risky assumptions, missing steps, and improvements
3) **Final**: LLM rewrites the answer using the critique

The API returns all 3 outputs so you can see the evolution.

POST `/ask-ai-critique`

**Request**
json
{
  "prompt": "Design a strategy to handle Kafka consumer lag in Spring Boot production."
}

**Response**
{
"draft": "....",
"critique": "• Missing DLQ strategy ...",
"finalAnswer": "...."
}

FYI:
----
Implementation Notes
Uses Spring AI ChatClient for prompt chaining
Uses 3 sequential calls:
draft generation
critique generation (reviewer persona)
final rewrite using critique
The “critic” is intentionally strict to force improvements (similar to code review)



