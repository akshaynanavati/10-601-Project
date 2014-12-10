JFLAGS = -g -Werror -Xlint:all -cp "sources/:weka.jar" -d classes
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	sources/preprocess/CIELab.java \
	sources/util/DataIO.java \
	sources/util/ClassifyInstances.java \
	sources/classifier/KNN.java \
	sources/MS1RF.java \
	sources/MS1CR.java \
	sources/RunKNN.java \
	sources/RunSVM.java \
	sources/MilestoneOne.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -r classes/*
