import pandas as pd
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.model_selection import train_test_split


# Load data
data = pd.read_csv('sales_data.csv')  # Replace with your file path
data['Product_ID'] = data['Product_ID'].astype(str)

# Convert 'Date' to datetime and extract features
data['Date'] = pd.to_datetime(data['Date'])
data['day_of_week'] = data['Date'].dt.dayofweek
data['month'] = data['Date'].dt.month
data['year'] = data['Date'].dt.year

# Season calculation
def get_season(month):
    if month in [12, 1, 2]:
        return 'Winter'
    elif month in [3, 4, 5]:
        return 'Spring'
    elif month in [6, 7, 8]:
        return 'Summer'
    else:
        return 'Fall'

data['season'] = data['month'].apply(get_season)
data = data.drop('Date',axis=1)

print(data)

value_counts = data['Product_ID'].value_counts()
data = data[data['Product_ID'].isin(value_counts[value_counts > 1].index)]

train, test = train_test_split(data, test_size=0.2, random_state=42, stratify=data['Product_ID'])

input_layers = [tf.keras.Input(shape=(1,), name=f'input_{feature}') for feature in train.columns]

# Concatenate inputs and build model
concatenated_inputs = tf.keras.layers.concatenate(input_layers)
dense_layer = tf.keras.layers.Dense(64, activation='relu')(concatenated_inputs)
output_layer = tf.keras.layers.Dense(1, activation='sigmoid')(dense_layer)

# Initialize model
model = tf.keras.Model(inputs=input_layers, outputs=output_layer)

print(input_layers)
print(output_layer)
# Compile the model
model.compile(loss='mean_squared_error', optimizer='adam', metrics=['accuracy'])


# Fit the model
target = 'Sales'

# Normalize or preprocess your features here (if needed)
# For simplicity, let's assume no further preprocessing is needed

# Split data into features and target
train_X = train.drop(target, axis=1)
train_y = train[target]
test_X = test.drop(target, axis=1)
test_y = test[target]

# # Prepare data for the model
train_data = [train_X[feature].values.reshape(-1, 1) for feature in train_X.columns]
test_data = [test_X[feature].values.reshape(-1, 1) for feature in test_X.columns]

# Fit the model
history = model.fit((train_X,train_y), epochs=10, batch_size=32, validation_data=(test_X,test_y))

# Forecasting
forecast = model.predict(test)

# Plot results
plt.figure(figsize=(10, 6))
plt.plot(range(len(test_y)), forecast, color='red', label='Predicted')
plt.plot(range(len(test_y)), test, color='blue', label='Actual')
plt.xlabel('Index')
plt.ylabel('Sales')
plt.legend()
plt.show()