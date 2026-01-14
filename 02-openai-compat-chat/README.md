Spring AI - 02 - Chat with OpenAI Compatible LLMs
Prerequisites
Java 21/24
Docker, Docker Compose
OpenAI compatible LLMs
Gemini, Groq, DeepSeek, OpenRouter, Docker Model Runner

Gemini Config
Go to https://aistudio.google.com/app/apikey and create an API Key
Set environment variable GEMINI_API_KEY=YOUR_TOKEN_VALUE_HERE

Groq Config
Go to https://console.groq.com/keys and create an API Key
Set environment variable GROQ_API_KEY=YOUR_TOKEN_VALUE_HERE

DeepSeek Config - not free (you can achive by using the openrouter)
Go to https://platform.deepseek.com/api_keys and create an API Key
Set environment variable DEEPSEEK_API_KEY=YOUR_TOKEN_VALUE_HERE

OpenRouter Config
Go to https://openrouter.ai/settings/keys and create an API Key
Set environment variable OPENROUTER_API_KEY=YOUR_TOKEN_VALUE_HERE
To view the free models, go to https://openrouter.ai/models and search for "free" - search free
***NOTE ***
OpenRouter is a unified API platform that aggregates access to **multiple large language models (LLMs)** from different providers, simplifying integration and offering practical advantages for developers and businesses
### 1. **Simplified Multi-Model Access**
- Access **70+ LLMs** (e.g., OpenAI GPT-4, Anthropic Claude, Meta Llama, Mistral, Google Gemini) through a **single API endpoint**.
- Avoid managing separate API keys, rate limits, or billing for each provider.
---------------------------


Docker Model Runner
Install Docker Desktop and setup Docker Model Runner by following https://docs.docker.com/ai/model-runner/