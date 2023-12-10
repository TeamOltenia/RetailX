import pandas as pd
import numpy as np
from xgboost import XGBRegressor
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from sklearn.preprocessing import LabelEncoder
# Sample DataFrame loading (replace with your data loading code)
df = pd.read_csv('sales_data.csv')

# Convert 'Date' to datetime
df['Date'] = pd.to_datetime(df['Date'])

# Extract year, month, and day from 'Date'
df['Year'] = df['Date'].dt.year
df['Month'] = df['Date'].dt.month
df['Day'] = df['Date'].dt.day

# Encode 'Product_ID' if it's categorical
label_encoder = LabelEncoder()
df['Product_ID_Encoded'] = label_encoder.fit_transform(df['Product_ID'].astype(str))

# Select features and target
X = df[['Product_ID_Encoded', 'Year', 'Month', 'Day']]
y = df['Sales']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

model = XGBRegressor(objective ='reg:squarederror', n_estimators=100, learning_rate=0.1)
model.fit(X_train, y_train)

y_pred = model.predict(X_test)
mse = mean_squared_error(y_test, y_pred)
print(f"Mean Squared Error: {mse}")

def predict_sales(product_id, date, model, label_encoder):
    # Convert date to datetime
    date = pd.to_datetime(date)

    # Extract year, month, and day
    year = date.year
    month = date.month
    day = date.day

    # Encode Product_ID
    encoded_product_id = label_encoder.transform([product_id])[0]

    # Create DataFrame for the prediction
    df_pred = pd.DataFrame({'Product_ID_Encoded': [encoded_product_id],
                            'Year': [year], 'Month': [month], 'Day': [day]})

    # Make prediction
    predicted_sales = model.predict(df_pred)[0]

    return predicted_sales

# Example usage
product_id_to_predict = '10002'
date_to_predict = '2010-09-05'  # replace with the date you want to predict for
predicted_sales = predict_sales(product_id_to_predict, date_to_predict, model, label_encoder)
print(f"Predicted Sales: {predicted_sales}")

import matplotlib.pyplot as plt
import pandas as pd


def generate_predictions_for_month(product_id, year, month, model, label_encoder):
    # Number of days in the month
    num_days = pd.Period(f'{year}-{month}').days_in_month

    # Generate predictions for each day
    predictions = []
    dates = []
    for day in range(1, num_days + 1):
        date_str = f'{year}-{month}-{day}'
        prediction = predict_sales(product_id, date_str, model, label_encoder)
        predictions.append(prediction)
        dates.append(date_str)

    return dates, predictions


def getPredictionsPlot(product_id_to_predict,year,month):
    dates, predictions = generate_predictions_for_month(product_id_to_predict, year, month, model, label_encoder)

    print(dates)
    print(predictions)
    # Plotting
    plt.figure(figsize=(12, 6))
    plt.plot(dates, predictions, marker='o')
    plt.xticks(rotation=45)
    plt.title(f"Sales Predictions for Product ID {product_id_to_predict} in {year}-{month}")
    plt.xlabel('Date')
    plt.ylabel('Predicted Sales')
    plt.grid(True)
    plt.show()

getPredictionsPlot('10002',2010,12)