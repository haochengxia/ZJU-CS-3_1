# Author: Haocheng Xia
# Date: 2020/1/4
# Computer Vision 光荣题

import sklearn.datasets
from sklearn.datasets import fetch_openml
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
%matplotlib inline


mnist = fetch_openml("mnist_784")

x = mnist["data"]
y = mnist["target"]

m = len(x)
perm = np.random.permutation(m)
x_train, x_test = x[perm][10000:],x[perm][:10000]
y_train, y_test = y[perm][10000:],y[perm][:10000]

x_train = x_train/255
x_test = x_test/255

# check content of the dataset
f, ax = plt.subplots(2,2)
ax[0, 0].imshow(x[50000].reshape(28, 28), cmap = 'Greys')
ax[0, 1].imshow(x[50001].reshape(28, 28), cmap = 'Greys')
ax[1, 0].imshow(x[50002].reshape(28, 28), cmap = 'Greys')
ax[1, 1].imshow(x[50003].reshape(28, 28), cmap = 'Greys')

from scipy.ndimage.interpolation import shift

# func: shift pic to enlarge dataset
def shift_image(image, dx, dy):
    image = image.reshape((28,28))
    shifted_image = shift(image, [dx, dy], cval = 0, mode = 'constant')
    return shifted_image.reshape([-1])

# image & label 
x_train_shifted = []
y_train_augmented = []

for dx, dy in ((1,0),(-1,0),(0,1),(0,-1)):
    for image, label in zip(x_train, y_train):
        x_train_shifted.append(shift_image(image, dx, dy))
        y_train_augmented.append(label)

x_train_shifted = np.array(x_train_shifted)
y_train_augmented = np.array(y_train_augmented)

# show the img shifted in 4 different directions
f, ax = plt.subplots(2,2,figsize = (12, 10))
ax[0,0].imshow(x_train_shifted[1].reshape(28,28), cmap = 'Greys')
ax[1,0].imshow(x_train_shifted[60001].reshape(28,28), cmap = 'Greys')
ax[0,1].imshow(x_train_shifted[120001].reshape(28,28), cmap = 'Greys')
ax[1,1].imshow(x_train_shifted[180001].reshape(28,28), cmap = 'Greys')
ax[0,0].grid()
ax[1,0].grid()
ax[0,1].grid()
ax[1,1].grid()

# check y_train_augmented
y_train_augmented[1],y_train_augmented[60001],y_train_augmented[120001],y_train_augmented[180001]

import cv2

# get the flipped img to enlarge the dataset
def horizontal_flip(images):
    flipped_images = []
    for img in images:
        flipped_img = cv2.flip(img, flipCode = 1)
        flipped_images.append(flipped_img)
    return (flipped_images)

flipped_imgs = horizontal_flip(x_train.reshape(-1, 28, 28))

flipped_imgs = np.array(flipped_imgs)
flipped_lables = np.array(y_train[:])

flipped_imgs = flipped_imgs.reshape(-1, 28, 28)
flipped_imgs.shape, flipped_lables.shape

# check the flipped effect
f, ax = plt.subplots(2,2,figsize = (12, 10))
ax[0,0].imshow(flipped_imgs[100].reshape(28,28), cmap = 'Greys')
ax[0,1].imshow(x_train[100].reshape(28,28), cmap = 'Greys')
ax[1,0].imshow(flipped_imgs[200].reshape(28,28), cmap = 'Greys')
ax[1,1].imshow(x_train[200].reshape(28,28), cmap = 'Greys')

# assistant function
def shuffle_batch(x, y, batch_size):
    rnd_idx = np.random.permutation(len(x))
    n_batches = len(x)//batch_size
    for batch_idx in np.array_split(rnd_idx, n_batches):
        x_batch, y_batch = x[batch_idx], y[batch_idx]
        yield x_batch, y_batch

# begin to create network
tf.compat.v1.disable_eager_execution()
tf.compat.v1.reset_default_graph()

with tf.name_scope('placeholders'):  
    x = tf.compat.v1.placeholder(np.float32, shape = [None, 28, 28], name = 'x')
    x_reshaped = tf.reshape(x, [-1, 28, 28, 1], name = 'x_reshaped')
    y = tf.compat.v1.placeholder(np.int32, shape = None, name = 'y')

with tf.name_scope('conv'):
    # conv layer
    conv1 = tf.keras.layers.Conv2D(12, [3,3], strides = 1, padding = 'SAME', name = 'conv1')
    tmpRes = conv1(x_reshaped)
    pool1 = tf.keras.layers.MaxPool2D([3,3], strides = 2, name = 'pool1') # [3,3] 
    tmpRes = pool1(tmpRes)
    conv2 = tf.keras.layers.Conv2D(16, [3,3], strides = 1, padding = 'SAME', name = 'conv2')
    tmpRes = conv2(tmpRes)
    pool2 = tf.keras.layers.MaxPool2D([3,3], strides = 2, name = 'pool2')
    tmpRes = pool2(tmpRes)
    pool2_flatten = tf.reshape(tmpRes, shape = (-1, 6*6*16))
    
    fc1 = tf.compat.v1.layers.Dense(256, activation = tf.nn.selu, name = 'fc1')
    tmpRes = fc1(pool2_flatten)
    res_fc1 = tmpRes
    fc2 = tf.keras.layers.Dense(100, activation = tf.nn.selu, name = 'fc2')
    tmpRes = fc2(tmpRes)
    res_fc2 = tmpRes
    logits = tf.keras.layers.Dense(10, activation = tf.nn.selu, name = 'output')
    tmpRes = logits(res_fc2)
    res_logits = tmpRes

# create loss function
with tf.name_scope('loss'):
    xentropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits = res_logits, labels = y)
    loss = tf.reduce_mean(xentropy)

# train -> minimize the loss
with tf.name_scope('train'):
    optimizer = tf.compat.v1.train.AdamOptimizer(0.001)
    training_op = optimizer.minimize(loss)

# eval effect
with tf.name_scope('eval'):
    correct = tf.math.in_top_k(y, res_logits, 1, name = 'correct')
    accuracy = tf.reduce_mean(tf.cast(correct, np.float32))

batch_size = 128
out = []

with tf.compat.v1.Session() as sess:
    sess.run(tf.compat.v1.global_variables_initializer())
    out = []
    for epoch in range(20):
        for x_batch, y_batch in shuffle_batch(x_train, y_train, batch_size):
            x_batch = np.reshape(x_batch, [-1, 28, 28])
            sess.run(training_op, feed_dict = {x:x_batch, y:y_batch})
        if epoch % 1 == 0:
            batch_acc = accuracy.eval(feed_dict = {x:x_batch, y:y_batch})
            x_test = np.reshape(x_test, [-1, 28, 28])
            val_acc = accuracy.eval(feed_dict = {x:x_test, y:y_test})
            print(epoch, "Batch Accuracy = ",batch_acc,"  Validation Accuracy = ",val_acc)
            outputs = sess.run(res_logits, feed_dict = {x:x_test})
            out.append(outputs)
        
y_hat = np.argmax(outputs, axis = 1)
y_int_test = list(map(int,y_test))
y_hat[:20]
y_test[:20]

from sklearn.metrics import accuracy_score
acc_score = accuracy_score(y_int_test, y_hat)
print(acc_score)

# use the early shifted and flipped data !!!
tf.compat.v1.reset_default_graph()

with tf.name_scope('placeholders'):
    
    x = tf.compat.v1.placeholder(np.float32, shape = [None, 28, 28], name = 'x')
    x_reshaped = tf.reshape(x, [-1, 28, 28, 1], name = 'x_reshaped')
    y = tf.compat.v1.placeholder(np.int32, shape = None, name = 'y')
    
    bn1_train = tf.compat.v1.placeholder_with_default(True, shape = (None), name = 'bn1_holder')
    bn2_train = tf.compat.v1.placeholder_with_default(True, shape = (None), name = 'bn2_holder')
    
    drop1 = tf.compat.v1.placeholder_with_default(True, shape = (None), name = 'Drop1')
    drop2 = tf.compat.v1.placeholder_with_default(True, shape = (None), name = 'Drop2')

# new with shifted & flipped
with tf.name_scope('conv'):
    # conv layer
    conv1 = tf.keras.layers.Conv2D(12, [3,3], strides = 1, padding = 'SAME', name = 'conv1')
    tmpRes = conv1(x_reshaped)
    pool1 = tf.keras.layers.MaxPool2D([3,3], strides = 2, name = 'pool1') # [3,3] is ?
    tmpRes = pool1(tmpRes)
    res_pool1 = tmpRes
    # momentum & renorm_momentum
    bn1 = tf.compat.v1.layers.batch_normalization(res_pool1, momentum = 0.9, training = bn1_train)
    # tmpRes = bn1(res_pool1, training = bn1_train)
    dropout1 = tf.compat.v1.keras.layers.Dropout(0.5)
    tmpRes = dropout1(bn1, training = drop1)
    
    conv2 = tf.keras.layers.Conv2D(16, [3,3], strides = 1, padding = 'SAME', name = 'conv2')
    tmpRes = conv2(res_pool1) #Attention! use pool1
    pool2 = tf.keras.layers.MaxPool2D([3,3], strides = 2, name = 'pool2')
    tmpRes = pool2(tmpRes)
    res_pool2 = tmpRes
    #bn2 = tf.keras.layers.BatchNormalization(momentum = 0.9)
    bn2 = tf.compat.v1.layers.batch_normalization(res_pool2, momentum = 0.9, training = bn2_train)
    # tmpRes = bn2(tmpRes, training = bn2_train)
    # res_bn2 = tmpRes
    dropout2 = tf.compat.v1.keras.layers.Dropout(0.5)
    tmpRes = dropout2(bn2, training = drop2)
    res_dropout2 = tmpRes
    
    bn2_flatten = tf.reshape(tmpRes, shape = (-1, 6*6*16)) #res_pool2
    
    fc1 = tf.compat.v1.layers.Dense(256, activation = tf.nn.selu, name = 'fc1')
    tmpRes = fc1(bn2_flatten)
    res_fc1 = tmpRes
    fc2 = tf.keras.layers.Dense(100, activation = tf.nn.selu, name = 'fc2')
    tmpRes = fc2(res_fc1)
    res_fc2 = tmpRes
    logits = tf.keras.layers.Dense(10, activation = tf.nn.selu, name = 'output')
    tmpRes = logits(res_fc2)
    res_logits = tmpRes

# create loss function
with tf.name_scope('loss'):
    xentropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits = res_logits, labels = y)
    loss = tf.reduce_mean(xentropy)

# train -> minimize the loss
with tf.name_scope('train'):
    optimizer = tf.compat.v1.train.AdamOptimizer(0.001)
    training_op = optimizer.minimize(loss)

# eval effect
with tf.name_scope('eval'):
    correct = tf.math.in_top_k(y, res_logits, 1, name = 'correct')
    accuracy = tf.reduce_mean(tf.cast(correct, np.float32))

flipped_imgs_784 = flipped_imgs.reshape((-1,784))

final_x = np.concatenate((x_train,flipped_imgs_784,x_train_shifted),axis=0)

final_y = np.concatenate((y_train,flipped_lables,y_train_augmented),axis=0)

batch_size = 128
out = []
extra_update_ops = tf.compat.v1.get_collection(tf.compat.v1.GraphKeys.UPDATE_OPS)

with tf.compat.v1.Session() as sess:
    sess.run(tf.compat.v1.global_variables_initializer())
    out = []
    for epoch in range(20):
        for x_batch, y_batch in shuffle_batch(final_x, final_y, batch_size):
            x_batch = np.reshape(x_batch, [-1, 28, 28])
            sess.run([training_op,extra_update_ops], feed_dict = {x:x_batch, y:y_batch})
        if epoch % 1 == 0:
            batch_acc = accuracy.eval(feed_dict = {x:x_batch, y:y_batch})
            x_test = np.reshape(x_test, [-1, 28, 28])
            val_acc = accuracy.eval(feed_dict = {bn1_train: False, bn2_train: False,
                                                 drop1: False, drop2: False, 
                                                 x:x_test, y:y_test})
            print(epoch, "Batch Accuracy = ",batch_acc,"  Validation Accuracy = ",val_acc)
            outputs = sess.run(res_logits, feed_dict = {bn1_train: False, bn2_train: False,
                                                        drop1: False, drop2: False, 
                                                        x:x_test})
            out.append(outputs)

y_hat = np.argmax(outputs, axis = 1)
y_int_test = list(map(int,y_test))
y_hat[:20]
y_test[:20]

acc_score = accuracy_score(y_int_test, y_hat)
print(acc_score)