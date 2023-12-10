import pandas as pd
from sklearn.preprocessing import MinMaxScaler

# Load DataFrame
df = pd.read_csv('transactions_sales_df.csv')

# Handle missing data
df.dropna(inplace=True)

# Normalize 'Price' column
scaler = MinMaxScaler()
df['Price'] = scaler.fit_transform(df[['Price']])

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans

# Extract features from 'Description'
vectorizer = TfidfVectorizer(stop_words='english')
X = vectorizer.fit_transform(df['Description'])

# Cluster for category identification
kmeans = KMeans(n_clusters=10)
kmeans.fit(X)
df['category'] = kmeans.labels_

# Create user profiles by aggregating data
user_profile = df.groupby('Customer ID').agg({
    'category': lambda x: x.mode()[0],  # Most common category
    'Price': 'mean',  # Average spending
})

def recommend_products(customer_id, num_recommendations=5):
    if customer_id not in user_profile.index:
        print(f"Customer ID {customer_id} not found.")
        return None

    user_cat = user_profile.loc[customer_id, 'category']
    similar_products = df[df['category'] == user_cat]

    # Recommend products from the same category
    recommendations = similar_products.sample(n=num_recommendations)
    return recommendations['Product_Id']

# Replace '12346' with a valid customer ID from your DataFrame
# Make sure the format (string or integer) matches your DataFrame
recommend_products('16321', 3)

