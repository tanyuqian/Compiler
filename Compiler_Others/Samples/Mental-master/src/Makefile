JVC = javac -cp "./antlr-4.5.3-complete.jar:.:$$CLASSPATH"

BASE_PATH = cn/edu/sjtu/songyuke/mental

all:
	$(JVC) $(BASE_PATH)/ast/*.java
	$(JVC) $(BASE_PATH)/ast/declarations/*.java
	$(JVC) $(BASE_PATH)/ast/expressions/*.java
	$(JVC) $(BASE_PATH)/ast/statements/*.java
	$(JVC) $(BASE_PATH)/core/*.java
	$(JVC) $(BASE_PATH)/ir/*.java
	$(JVC) $(BASE_PATH)/ir/arithmetic/*.java
	$(JVC) $(BASE_PATH)/ir/branch/*.java
	$(JVC) $(BASE_PATH)/ir/data/*.java
	$(JVC) $(BASE_PATH)/ir/label/*.java
	$(JVC) $(BASE_PATH)/parser/*.java
	$(JVC) $(BASE_PATH)/symbols/*.java
	$(JVC) $(BASE_PATH)/translator/*.java
	$(JVC) $(BASE_PATH)/type/*.java

clean:
	-rm -f $(BASE_PATH)/ast/*.class
	-rm -f $(BASE_PATH)/ast/declarations/*.class
	-rm -f $(BASE_PATH)/ast/expressions/*.class
	-rm -f $(BASE_PATH)/ast/statements/*.class
	-rm -f $(BASE_PATH)/core/*.class
	-rm -f $(BASE_PATH)/ir/*.class
	-rm -f $(BASE_PATH)/ir/arithmetic/*.class
	-rm -f $(BASE_PATH)/ir/branch/*.class
	-rm -f $(BASE_PATH)/ir/data/*.class
	-rm -f $(BASE_PATH)/ir/label/*.class
	-rm -f $(BASE_PATH)/parser/*.class
	-rm -f $(BASE_PATH)/symbols/*.class
	-rm -f $(BASE_PATH)/translator/*.class
	-rm -f $(BASE_PATH)/type/*.class