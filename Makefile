JFLAGS = -g -Werror -Xlint:all -cp "sources/:weka.jar" -d classes
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	sources/MS1RF.java \
	sources/MS1CR.java \
	sources/MilestoneOne.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) classes/*
