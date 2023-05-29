import os
import openai
#openai.api_key = os.getenv("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")
openai.api_key = "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"
prompt =  """
Decide whether a Mike's sentiment is positive, neutral, or negative.
Mike: I don't like homework!
Sentiment:
"""
#response = openai.ChatCompletion.create(model="gpt-3.5-turbo", prompt=prompt, max_tokens=100, temperature=0)
response = openai.Completion.create( model="text-davinci-003", prompt=prompt, max_tokens=100, temperature=0  )
print(response)
