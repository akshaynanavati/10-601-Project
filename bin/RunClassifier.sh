#!/bin/bash

time java -cp classes/:weka.jar $1 ../../train.arff ../../test.arff
