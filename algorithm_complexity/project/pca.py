# The File Contains Algorithm For PCA

import numpy as np
from sklearn.preprocessing import StandardScaler
    
def pca(number_compontents, data , is_scaled = True):
    if not 0 <= number_compontents <= data.shape[1]:
        raise ValueError('The number of features are less than the number of components')
    
    if not is_scaled:
        scaler = StandardScaler()
        data = scaler.fit_transform(data)
    
    # calculate the covariance matrix
    cov_matrix = np.cov(data.T)
    
    # calculate the eigen things
    eig_vals , eig_vect = np.linalg.eigh(cov_matrix)
    
    indexes = np.argsort(-eig_vals)
    eig_vals = eig_vals[indexes] # sort in proper order
    eig_vals = np.maximum(eig_vals, 0) # get rid of negatives
    eig_vecs = eig_vecs[:,indexes]

    # Forming a pair of Eign Values / Pairs
    """eig_pairs = []
    for i in range(len(eig_vals)):
        eig_pairs.append((np.abs(eig_vals[i]) , eig_vect[:,i])))"""
    
    # codes are the same
    # eig_pairs = [(np.abs(eig_vals[i]) , eig_vect[:,i]) for i in range(len(eig_vals))]

    # Sorting All of Them
    # eig_pairs.sort(key = lambda x : x[0] , reverse= True)
    
    # final = []
    # for i in range(number_compontents):
    #    final.append(eig_pairs[i][1].reshape(data.shape[1],1))

    # Creating the Projection Matrix
    # projection_matrix = np.hstack((final))
    
    # transforming the data
    Y = data.dot(eig_vecs)
    
    return Y[:,:number_compontents]
    
        
        

