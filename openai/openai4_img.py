import os
import openai
openai.api_key = "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"

response = openai.Image.create(
    prompt="坐在电脑前的男孩",
    n=1,
    size="1024x1024"
)
image_url = response['data'][0]['url']
print(image_url)
