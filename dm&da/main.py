import tensorflow as tf
import numpy as np
from typing import Dict
import matplotlib.pyplot as plt


import numpy as np
from fastapi import FastAPI, File, UploadFile
from PIL import Image
import sys

@app.get("/classify/")
async def classify_image(image_url: str) -> str:
    pass

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="127.0.0.1", port=8000)
