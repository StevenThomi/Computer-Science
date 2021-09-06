JC=/usr/bin/javac
total_words=10
screen_words=2
input_file="dict.txt"

.SUFFIXES: .java .class

.java.class:
	$(JC) $*.java

CLASSES=Compare.java FallingWords.java Score.java WordApp.java WordDictionary.java WordPanel.java WordRecord.java

default: classes
classes: $(CLASSES:.java=.class)
clean:
	$(RM) *.class
run:
	java WordApp $(total_words) $(screen_words) $(input_file)
