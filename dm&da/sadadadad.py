from sklearn.ensemble import IsolationForest

from main import label_anomalies
import pandas as pd
import numpy as np

sales_doc = "../doccuments/sales_and_eodStocks.xlsx"

sales_df = pd.read_excel(sales_doc)
def generate_emails_manager(customer_id, customer_email, startDate, endDate, language='english'):
    product_data = sales_df[(sales_df['Product_ID'] == customer_id) & (sales_df['Date'] >= startDate) & (
            sales_df['Date'] <= endDate)].copy()

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
    product_data['Anomaly'] = product_data.apply(
        lambda row: label_anomalies(row, too_much_stock_threshold, too_small_stock_threshold,
                                    not_enough_stock_threshold), axis=1)

    # Step 5: Machine Learning Model (Isolation Forest)
    X = product_data[['EndOfDayStock', 'StockDiff']]  # Remove 'Date' column

    model = IsolationForest(contamination=0.05)
    model.fit(X)

    # Step 6: Anomaly Detection
    product_data['AnomalyDetection'] = model.predict(X)
    # Step 7: Visualization
    anomalies = product_data[product_data['AnomalyDetection'] == -1]

    print(anomalies[anomalies.size - 1])

#     messages = []
#     recs = recommendation(customer_id)
#     for key, value in recs.items():
#         print(value)
#         description = get_product_name(value)
#         print(description)
#         prompt = create_prompter(customer_id, 20, language, False, description, customer_email, False)
#         print(prompt)
#         response = query_openai(prompt)
#         print(response)
#         if response:
#             messages.append(response.choices[0].message.content)
#
#
# return messages
generate_emails_manager('10002','lefterioancristian@gmail.com',np.datetime64(pd.Timestamp('2009-10-28')),np.datetime64(pd.Timestamp('2010-10-28')))