import tensorflow as tf
import numpy as np
import cv2
from minio import Minio
import io
import os
import imghdr
from typing import Dict
import matplotlib.pyplot as plt


import numpy as np
from fastapi import FastAPI, File, UploadFile
from PIL import Image
import sys

@app.get("/classify/")
async def classify_image(image_url: str) -> str:
    data = minio_client.get_object(bucket_name, image_url)
    content = io.BytesIO(data.read())
    content.seek(0)
    img_array = read_image(content.getvalue())
    predictions = prediction(img_array)
    if predictions:
        return predictions
    else:
        return 'empty'

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="127.0.0.1", port=8000)
