# importing the libraries
import matplotlib.pyplot as plt
import pandas as pd

# getting the dataset
dataset = pd.read_csv('Mall_Customers.csv',)

# getting the intrstting data
X = dataset.iloc[:,[3,4]].values

# Plot the data
plt.scatter(X[:,0],X[:,1], label = 'Stingy')
plt.title('Cluster of Client')
plt.xlabel('Anual Income')
plt.ylabel('Spending Score')
plt.legend()
plt.show()

# testing the best k
from sklearn.cluster import KMeans
wcss = []
for i in range(1,11):
    kmeans = KMeans(n_clusters = i, init='k-means++',random_state=0)
    kmeans.fit(X)
    wcss.append(kmeans.inertia_)
    
plt.plot(range(1,11),wcss)
plt.show()

# doing KMeans
kmeans = KMeans(n_clusters = 5, init='k-means++',random_state=0)
y_kmeans = kmeans.fit_predict(X)


# plot the clusters
plt.scatter(X[y_kmeans == 0,0],X[y_kmeans == 0,1],s=100 , c = 'red', label = 'Stingy')
plt.scatter(X[y_kmeans == 1,0],X[y_kmeans == 1,1],s=100 , c = 'blue', label = 'Normal')
plt.scatter(X[y_kmeans == 2,0],X[y_kmeans == 2,1],s=100 , c = 'green', label = 'Wasteful')
plt.scatter(X[y_kmeans == 3,0],X[y_kmeans == 3,1],s=100 , c = 'cyan', label = '3ich Hyatek')
plt.scatter(X[y_kmeans == 4,0],X[y_kmeans == 4,1],s=100 , c = 'magenta', label = 'just just')
plt.scatter(kmeans.cluster_centers_[:,0],kmeans.cluster_centers_[:,1],s=300,c='yellow',label='Cnetroids')
plt.title('Cluster of Client')
plt.xlabel('Anual Income')
plt.ylabel('Spending Score')
plt.legend()
plt.show()