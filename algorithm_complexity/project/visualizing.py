# File About Visualizing What PCA Does

import pandas as pd
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA


################## The Fashion Mnist Daata set

data = pd.read_csv('fashion-mnist_train.csv')
X = data.iloc[:,1:].values
Y = data.iloc[:,0].values


pca = PCA(2)
new = pca.fit_transform(X)

#Plot in 3D
plt.scatter(new[:,0],new[:,1],c = Y)
plt.show()

# Plot in 2D
pca = PCA(3)
new3d = pca.fit_transform(X)

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

ax.scatter(new3d[:,0],new3d[:,1],new3d[:,2] ,c = Y)
plt.show()

################## The Mnist Daata set
mnist = pd.read_csv('mnist_train.csv')
X = mnist.iloc[:,1:].values
Y = mnist.iloc[:,0].values

from sklearn.decomposition import PCA

pca = PCA(2)
new = pca.fit_transform(X)

#Plot in 2D
plt.scatter(new[:,0],new[:,1],c = Y, alpha = 0.3 , s = 100)
plt.show()

# Plot in 3D
pca = PCA(3)
new3d = pca.fit_transform(X)

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

ax.scatter(new3d[:,0],new3d[:,1],new3d[:,2] ,c = Y, alpha = 0.3 , s = 100)
plt.show()

####################### Dam Wine Dataset
wine = pd.read_csv('Wine.csv')

X = wine.iloc[:,:13].values
Y = wine.iloc[:,13].values

from sklearn.preprocessing import StandardScaler
std = StandardScaler()
X = std.fit_transform(X)

#Plot in 2D
pca = PCA(2)
new = pca.fit_transform(X)

plt.scatter(new[:,0],new[:,1],c = Y, alpha = 0.3 , s = 100)
plt.show()

# Plot in 3D
pca = PCA(3)
new3d = pca.fit_transform(X)

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

ax.scatter(new3d[:,0],new3d[:,1],new3d[:,2] ,c = Y, alpha = 0.3 , s = 100)
plt.show()