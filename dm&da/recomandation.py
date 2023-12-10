import pandas as pd
from sklearn.preprocessing import MinMaxScaler

# Load DataFrame
df = pd.read_csv('transactions_sales_df.csv')

# Handle missing data
df.dropna(inplace=True)

# Ensure correct data types (e.g., Customer_Id as string)
df['Customer ID'] = df['Customer ID'].astype(str)

# Normalize 'Price' column
scaler = MinMaxScaler()
df['Price'] = scaler.fit_transform(df[['Price']])

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.decomposition import PCA

# Extract features from 'Description'
vectorizer = TfidfVectorizer(stop_words='english', max_features=100)
X = vectorizer.fit_transform(df['Description'])

# Reduce dimensions (optional, for better performance)
pca = PCA(n_components=20)
X_reduced = pca.fit_transform(X.toarray())


from sklearn.cluster import KMeans

# Cluster for category identification
kmeans = KMeans(n_clusters=10)
kmeans.fit(X_reduced)
df['category'] = kmeans.labels_


from sklearn.cluster import KMeans

# Aici modifica numarul de clusetere daca vrei sa ai mai multe categori posivilie penntru obiecte
kmeans = KMeans(n_clusters=10)
kmeans.fit(X_reduced)
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
    return recommendations['Product_ID']


# Test the recommendation function
test_customer_id = '12346'  # Replace with a valid customer ID from your DataFrame
recommendations = recommend_products(test_customer_id, 5)
print(recommendations)
