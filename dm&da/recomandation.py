import pandas as pd
from sklearn.preprocessing import MinMaxScaler
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans

# Example DataFrame loading
df = pd.read_csv('transactions_sales_df.csv')

# Handling missing data
df = df.dropna() # or use other strategies like filling missing values

# Data normalization for 'price' column
scaler = MinMaxScaler()
df['Price'] = scaler.fit_transform(df[['Price']])



# Extracting features from descriptions
vectorizer = TfidfVectorizer(stop_words='english')
X = vectorizer.fit_transform(df['Description'])

# Clustering (for category identification)
kmeans = KMeans(n_clusters=10) # number of clusters/categories
kmeans.fit(X)
df['category'] = kmeans.labels_

# Grouping by customer and aggregating data
user_profile = df.groupby('Customer ID').agg({
    'category': lambda x: x.mode()[0],  # most common category
    'Price': 'mean',  # average spending
    # add more aggregations as needed
})

print(user_profile)


from sklearn.metrics.pairwise import cosine_similarity

def recommend_products(customer_id, num_recommendations=5):
    # Assuming 'user_profile' and 'df' from previous steps are available
    user_cat = user_profile.loc[customer_id, 'category']
    similar_products = df[df['category'] == user_cat]

    # Recommend products from the same category
    recommendations = similar_products.sample(n=num_recommendations)
    return recommendations['Product_Id']

recommend_products('12346',3)

