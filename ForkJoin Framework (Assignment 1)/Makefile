JC=/usr/bin/javac
input_file_serial="input_sequential.txt"
output_file_serial="output_sequential.txt"
input_file_parallel="input_parallel.txt"
output_file_parallel="output_parallel.txt"
filter_size=3
data_size=100

.SUFFIXES: .java .class

.java.class:
	$(JC) $*.java

CLASSES=MedianFilterAll.java MedianFilterArray.java SerialMedianFilter.java

default: classes
classes: $(CLASSES:.java=.class)
clean:
	$(RM) *.class
runserial:
	java SerialMedianFilter $(input_file_serial) $(output_file_serial) $(filter_size) $(data_size)
runparallel:
	java MedianFilterAll $(input_file_parallel) $(output_file_parallel) $(filter_size) $(data_size)
