import pandas as pd
from sklearn.preprocessing import MinMaxScaler, StandardScaler
import numpy as np
from sklearn.ensemble import IsolationForest
import matplotlib.pyplot as plt
from fastapi.middleware.cors import CORSMiddleware

from fastapi import FastAPI
from contextlib import asynccontextmanager


sales_doc = "../doccuments/sales_and_eodStocks.xlsx"
transactions_doc = "../doccuments/transactions.xlsx"

sales_df = pd.read_excel(sales_doc)
sales_df['Product_ID'] = sales_df['Product_ID'].astype(str)
transactions_sales_df = pd.read_excel(transactions_doc)
print(sales_df.head())
from fastapi.encoders import jsonable_encoder
import json

class NumpyEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.integer):
            return int(obj)
        elif isinstance(obj, np.floating):
            return float(obj)
        elif isinstance(obj, np.ndarray):
            return obj.tolist()
        else:
            return super(NumpyEncoder, self).default(obj)



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

app = FastAPI()
origins = [
    "http://localhost.tiangolo.com",
    "https://localhost.tiangolo.com",
    "http://localhost",
    "http://localhost:3000",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get("/sales/{start_date}/{end_date}/{product_id}")
async def get_sales(start_date: str, end_date: str, product_id: str):
    product_data = sales_df[(sales_df['Product_ID'] == product_id) & (sales_df['Date'] >= start_date) & (sales_df['Date'] <= end_date)].copy()
    return product_data.to_json(orient="records", date_format="iso")


@app.get("/sales/anomaly/{start_date}/{end_date}/{product_id}")
async def get_anomaly_sales(start_date: str, end_date: str, product_id: str):
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
    return anomalies.to_json(orient="records", date_format="iso")


@app.get("/sales/metrics/{start_date}/{end_date}/{product_id}")
async def get_sales(start_date: str, end_date: str, product_id: str):
    product_data = sales_df[(sales_df['Product_ID'] == product_id) & (sales_df['Date'] >= start_date) & (sales_df['Date'] <= end_date)].copy()
    if product_data.empty:
        return []
    total_sales = product_data['Sales'].sum()
    average_sales = product_data['Sales'].mean()
    total_revenue = product_data['Revenue'].sum()
    max_stock = product_data['EndOfDayStock'].min()
    sales_change_percentage = product_data['Sales'].pct_change().mean() * 100
    average_revenue_per_sale = total_revenue / total_sales

    metrics = {
        'Total Sales': float(total_sales),
        'Average Sales': float(average_sales),
        'Total Revenue': float(total_revenue),
        'Maximum End of Day Stock': float(max_stock),
        'Sales Change Percentage': float(sales_change_percentage),
        'Average Revenue per Sale': float(average_revenue_per_sale)
    }
    return json.dumps(metrics)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8000)
