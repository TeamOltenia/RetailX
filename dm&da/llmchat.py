import requests
import json
import os
from openai import OpenAI


def create_prompter(increase, product_id, amount, language):
    action = "increase" if increase else "decrease"
    prompt = f"Create a short message which will be exported as JSON to tell a Shop Owner that his product with the product_Id of {product_id} has to {action} the stock of his product by {amount}%.The language of the message should be {language}:  Eg of an generated message in romanian : “Dragă Manager de Stoc X, Bazându-ne pe ultimele analize, vă sugerăm să ajustați stocul pentru Produsul Y. Recomandăm o creștere cu 20% a stocului, având în vedere tendințele de creștere ale cererii. Pentru a evita ruptura de stoc, sugerăm plasarea unei comenzi suplimentare în următoarele Z zile. Vă rugăm să verificați detaliile complete ale recomandării în dashboard-ul nostru de management al stocurilor. De asemenea, vă vom trimite un rezumat detaliat prin email și o alertă prin SMS pentru a asigura o acțiune promptă."
    return prompt

def query_openai(prompt, engine='gpt-3.5-turbo', max_tokens=100):
    api_key = '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'  # Replace with your actual API key
    client = OpenAI(api_key=api_key)

    response = client.chat.completions.create(
        model="gpt-3.5-turbo-1106",
        response_format={"type": "json_object"},
        messages=[
            {"role": "system", "content": "You are a text generator which generates short messages (max: 500 characters) for the users based on their request"},
            {"role": "user", "content": prompt}
        ]
    )
    print(response.choices[0].message.content)


# Example usage
response = query_openai(create_prompter(True, 10002, 20,'romanian'))
if response:
    print(response['choices'][0]['text'])  # Print the actual generated text







