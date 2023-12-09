import pandas as pd
from sklearn.preprocessing import MinMaxScaler, StandardScaler
import numpy as np
from sklearn.ensemble import IsolationForest
import matplotlib.pyplot as plt

from fastapi import FastAPI
from contextlib import asynccontextmanager


sales_doc = "../doccuments/sales_and_eodStocks.xlsx"
transactions_doc = "../doccuments/transactions.xlsx"

sales_df = pd.DataFrame()
transactions_sales_df = pd.DataFrame()

# Step 4: Labeling Anomalies
def label_anomalies(row, too_much_stock_threshold, too_small_stock_threshold, not_enough_stock_threshold):
    if row['StockDiff'] > too_much_stock_threshold:
        return 'Too Much Stock'
    elif row['StockDiff'] < too_small_stock_threshold:
        return 'Too Small Stock'
    elif row['EndOfDayStock'] < not_enough_stock_threshold:
        return 'Not Enough Stock'
    else:
        return 'Normal'

# def detectAnomalies(product_id, start_date, end_date, data):

#     product_data = data[(data['Product_ID'] == product_id) & (data['Date'] >= start_date) & (data['Date'] <= end_date)].copy()

#     product_data = product_data.sort_values(by=['Product_ID', 'Date'])

#     # Step 2: Feature Engineering
#     # Calculate StockDiff as the difference in stock levels between consecutive days within the same product
#     product_data['StockDiff'] = product_data.groupby('Product_ID')['EndOfDayStock'].diff()
#     product_data = product_data.dropna(subset=['Product_ID', 'StockDiff', 'EndOfDayStock'])

#     # Adjusted quantile values for "Normal" classification
#     too_much_stock_threshold = product_data['EndOfDayStock'].quantile(0.95)
#     too_small_stock_threshold = product_data['EndOfDayStock'].quantile(0.05)
#     not_enough_stock_threshold = product_data['EndOfDayStock'].quantile(0.25)
#     print(too_much_stock_threshold, too_small_stock_threshold, not_enough_stock_threshold)

#     # Step 3: Define Anomalies
#     product_data['Anomaly'] = product_data.apply(lambda row: label_anomalies(row, too_much_stock_threshold, too_small_stock_threshold, not_enough_stock_threshold), axis=1)

#     # Step 5: Machine Learning Model (Isolation Forest)
#     X = product_data[['EndOfDayStock', 'StockDiff']]  # Remove 'Date' column

#     model = IsolationForest(contamination=0.05)
#     model.fit(X)

#     # Step 6: Anomaly Detection
#     product_data['AnomalyDetection'] = model.predict(X)
#     # Step 7: Visualization
#     anomalies = product_data[product_data['AnomalyDetection'] == -1]
#     print(anomalies)
#     return anomalies
    #print(anomalies)
# Implement alerting or actions based on detected anomalies.

@asynccontextmanager
async def lifespan(app: FastAPI):
    sales_df = pd.read_excel(sales_doc)
    print(sales_df.head())
    sales_df['Product_ID'] = sales_df['Product_ID'].astype(str)
    # transactions_sales_df = pd.read_excel(transactions_doc)
    yield

app = FastAPI(lifespan=lifespan)

@app.get("/sales/{start_date}/{end_date}/{product_id}")
async def get_sales(start_date: str, end_date: str, product_id: str):
    product_data = sales_df[(sales_df['Product_ID'] == product_id) & (sales_df['Date'] >= start_date) & (sales_df['Date'] <= end_date)].copy()
    return product_data


@app.get("/sales/anomaly/{start_date}/{end_date}/{product_id}")
async def get_anomaly_sales(start_date: str, end_date: str, product_id: str):
    print(sales_df.head())
    product_data = sales_df[(sales_df['Product_ID'] == product_id) & (sales_df['Date'] >= start_date) & (sales_df['Date'] <= end_date)].copy()

    product_data = product_data.sort_values(by=['Product_ID', 'Date'])

    # Step 2: Feature Engineering
    # Calculate StockDiff as the difference in stock levels between consecutive days within the same product
    product_data['StockDiff'] = product_data.groupby('Product_ID')['EndOfDayStock'].diff()
    product_data = product_data.dropna(subset=['Product_ID', 'StockDiff', 'EndOfDayStock'])

    # Adjusted quantile values for "Normal" classification
    too_much_stock_threshold = product_data['EndOfDayStock'].quantile(0.95)
    too_small_stock_threshold = product_data['EndOfDayStock'].quantile(0.05)
    not_enough_stock_threshold = product_data['EndOfDayStock'].quantile(0.25)
    print(too_much_stock_threshold, too_small_stock_threshold, not_enough_stock_threshold)

    # Step 3: Define Anomalies
    product_data['Anomaly'] = product_data.apply(lambda row: label_anomalies(row, too_much_stock_threshold, too_small_stock_threshold, not_enough_stock_threshold), axis=1)

    # Step 5: Machine Learning Model (Isolation Forest)
    X = product_data[['EndOfDayStock', 'StockDiff']]  # Remove 'Date' column

    model = IsolationForest(contamination=0.05)
    model.fit(X)

    # Step 6: Anomaly Detection
    product_data['AnomalyDetection'] = model.predict(X)
    # Step 7: Visualization
    anomalies = product_data[product_data['AnomalyDetection'] == -1]
    print(anomalies)
    return anomalies


@app.get("/classify/")
async def classify_image(image_url: str) -> str:
    pass

