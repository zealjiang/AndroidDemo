import openai
import os
openai.api_key = "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"


def get_completion(prompt, model="gpt-3.5-turbo"):
    messages = [{"role": "user", "content": prompt}]
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=0, #this is the degree of randomness
    )
    return response.choices[0].message["content"]


def get_completion_from_messages(messages, model="gpt-3.5-turbo", temperature=0):
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=temperature, #this is the degree of randomness
    )
    print(str(response.choices[0].message))
    return response.choices[0].message["content"]


messages =[
    {"role": "system",
     "content": "You are an assistant..."
     },
    {"role": "user",
     "content": "tell me a joke"
     },
    {"role": "assistant",
     "content": "Why did the chicken cross the road"
     },
    {"role": "user",
     "content": "I don't know"
     },
]


messages = [
    {'role':'system', 'content':'You are friendly chatbot.'},
    {'role':'user', 'content':'Hi, my name is Isa'}
]

messages = [
    {'role':'system', 'content':'You are friendly chatbot.'},
    {'role':'user', 'content':'What is my name'}
]


response = get_completion_from_messages(messages, temperature=1)
print(response)






















