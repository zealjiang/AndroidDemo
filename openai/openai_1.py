import openai
import os
openai.api_key = "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"
#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


def get_completion(prompt, model="gpt-3.5-turbo"):
    messages = [{"role": "user", "content": prompt}]
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=0, #this is the degree of randomness
    )
    return response.choices[0].message["content"]

text = f"""
在开发Android Studio插件时，你可以使用Android Studio提供的丰富的开发工具和API，包括插件开发工具包（Plugin SDK）
、Gradle构建系统、插件开发文档、示例代码等。这些资源可以帮助你更好地了解和应用Android Studio插件开发的技术和最佳实践。
"""

prompt = f"""
    总结一下text说了些什么
"""

response = get_completion(prompt)
print(response)




