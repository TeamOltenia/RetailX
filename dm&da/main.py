# import tensorflow as tf
import ssl

import app
import numpy as np
from typing import Dict
import matplotlib.pyplot as plt

import numpy as np
from fastapi import  FastAPI, Form, HTTPException
from PIL import Image
import sys

import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

from pydantic import BaseModel
import aiosmtplib
import asyncio
from smtplib import SMTP


app = FastAPI()

@app.post("/send_email")
async def send_email(to_email: str = Form(...), subject: str = Form(...), message: str = Form(...)):
    # Set your Yahoo email credentials
    sender_email = "fazalunga200@yahoo.com"
    sender_password = "ParolaSerioasa10."  # replace with your Yahoo password

    # Compose the email
    msg = MIMEText(message)
    msg['Subject'] = subject
    msg['From'] = sender_email
    msg['To'] = to_email

    try:
        # Connect to the SMTP server
        with smtplib.SMTP("smtp.mail.yahoo.com", 587) as server:
            server.starttls()
            # Log in to your Yahoo email account
            server.login(sender_email, sender_password)
            # Send the email
            server.sendmail(sender_email, to_email, msg.as_string())

        return {"message": "Email sent successfully"}

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Failed to send email: {str(e)}")

@app.get("/classify/")
async def classify_image(image_url: str) -> str:
    pass

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="127.0.0.1", port=8000)