JC = javac

all:
	$(JC) src/*.java

clean: 
	rm src/*.class
