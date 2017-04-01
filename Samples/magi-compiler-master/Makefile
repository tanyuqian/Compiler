all: clean
	@mkdir -p ./bin
	@mkdir -p ./bin/lib
	@cd ./src && javac -cp \
		../lib/antlr-4.5.2-complete.jar \
		./Compiler/*/*/*/*/*/*.java \
		./Compiler/*/*/*/*/*.java \
		./Compiler/*/*/*/*.java \
		./Compiler/*/*/*.java \
		./Compiler/*/*.java \
		./Compiler/*.java \
		-d ../bin
	@cp ./lib/antlr-4.5.2-complete.jar ./bin
	@cp ./lib/library.s ./bin/lib
	@cd ./bin && jar xf ./antlr-4.5.2-complete.jar \
			  && rm -rf ./META-INF \
			  && jar cef Compiler/Main Compiler.jar ./ \
			  && rm -rf ./antlr-4.5.2-complete.jar ./Compiler ./org

clean:
	rm -rf ./bin