JAVAC   =       /usr/bin/javac
.SUFFIXES:      .java .class    
default:
	$(JAVAC)	*.java
run:
	java	OS1Assignment OS1sequence
clean:
	rm	*.class *.txt
