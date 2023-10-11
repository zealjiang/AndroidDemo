import openai
import os
import panel as pn
openai.api_key = "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"

pn.extension()
panels = []
context = []

def collect_messages(_):
    prompt = inp.value_input
    inp.value = ''
    context.append({'role':'user', 'content':f"{prompt}"})
    response = get_completion_from_messages(context)
    context.append({'role':'assistant', 'content':f"{response}"})
    panels.append(pn.Row('User:', pn.pane.Markdown(prompt, width=600)))
    panels.append(pn.Row('Assistant:', pn.pane.Markdown(response, width=600)))

    return pn.Column(*panels)


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



inp = pn.widgets.TextInput(value='Hi', placeholder='Enter text')
button_conversation = pn.widgets.Button(name="Chat!")

interactive_conversation = pn.bind(collect_messages, button_conversation)

dashboard = pn.Column(
    inp,
    pn.Row(button_conversation),
    pn.panel(interactive_conversation, loading_indicator=True)
)

dashboard