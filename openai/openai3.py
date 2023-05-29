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

prompt = """
    Create an array of weather temperatures data json
"""

response = get_completion(prompt)
print(response)

print(response)
