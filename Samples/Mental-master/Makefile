BASE_PATH = cn/edu/sjtu/songyuke/mental

all:
	$(MAKE) -C src all
	if [ ! -d bin ]; then mkdir bin; fi
	if [ ! -d bin/cn ]; then mkdir bin/cn; fi
	if [ ! -d bin/cn/edu ]; then mkdir bin/cn/edu; fi
	if [ ! -d bin/cn/edu/sjtu ]; then mkdir bin/cn/edu/sjtu; fi
	if [ ! -d bin/cn/edu/sjtu/songyuke ]; then mkdir bin/cn/edu/sjtu/songyuke; fi
	if [ ! -d bin/cn/edu/sjtu/songyuke/mental ]; then mkdir bin/cn/edu/sjtu/songyuke/mental; fi
	if [ ! -d bin/$(BASE_PATH)/ast ]; then mkdir bin/$(BASE_PATH)/ast; fi
	if [ ! -d bin/$(BASE_PATH)/ast/declarations ]; then mkdir bin/$(BASE_PATH)/ast/declarations; fi
	if [ ! -d bin/$(BASE_PATH)/ast/expressions ]; then mkdir bin/$(BASE_PATH)/ast/expressions; fi
	if [ ! -d bin/$(BASE_PATH)/ast/statements ]; then mkdir bin/$(BASE_PATH)/ast/statements; fi
	if [ ! -d bin/$(BASE_PATH)/core ]; then mkdir bin/$(BASE_PATH)/core; fi
	if [ ! -d bin/$(BASE_PATH)/ir ]; then mkdir bin/$(BASE_PATH)/ir; fi
	if [ ! -d bin/$(BASE_PATH)/ir/Arithmetic ]; then mkdir bin/$(BASE_PATH)/ir/Arithmetic; fi
	if [ ! -d bin/$(BASE_PATH)/ir/Branch ]; then mkdir bin/$(BASE_PATH)/ir/Branch; fi
	if [ ! -d bin/$(BASE_PATH)/ir/Data ]; then mkdir bin/$(BASE_PATH)/ir/Data; fi
	if [ ! -d bin/$(BASE_PATH)/ir/Label ]; then mkdir bin/$(BASE_PATH)/ir/Label; fi
	if [ ! -d bin/$(BASE_PATH)/parser ]; then mkdir bin/$(BASE_PATH)/parser; fi
	if [ ! -d bin/$(BASE_PATH)/symbols ]; then mkdir bin/$(BASE_PATH)/symbols; fi
	if [ ! -d bin/$(BASE_PATH)/translator ]; then mkdir bin/$(BASE_PATH)/translator; fi
	if [ ! -d bin/$(BASE_PATH)/type ]; then mkdir bin/$(BASE_PATH)/type; fi
	cp src/$(BASE_PATH)/ast/*.class bin/$(BASE_PATH)/ast/
	cp src/$(BASE_PATH)/ast/declarations/*.class bin/$(BASE_PATH)/ast/declarations/
	cp src/$(BASE_PATH)/ast/expressions/*.class bin/$(BASE_PATH)/ast/expressions/
	cp src/$(BASE_PATH)/ast/statements/*.class bin/$(BASE_PATH)/ast/statements/
	cp src/$(BASE_PATH)/core/*.class bin/$(BASE_PATH)/core/
	cp src/$(BASE_PATH)/ir/*.class bin/$(BASE_PATH)/ir/
	cp src/$(BASE_PATH)/ir/Arithmetic/*.class bin/$(BASE_PATH)/ir/Arithmetic/
	cp src/$(BASE_PATH)/ir/Branch/*.class bin/$(BASE_PATH)/ir/Branch/
	cp src/$(BASE_PATH)/ir/Data/*.class bin/$(BASE_PATH)/ir/Data/
	cp src/$(BASE_PATH)/ir/Label/*.class bin/$(BASE_PATH)/ir/Label/
	cp src/$(BASE_PATH)/parser/*.class bin/$(BASE_PATH)/parser/
	cp src/$(BASE_PATH)/symbols/*.class bin/$(BASE_PATH)/symbols/
	cp src/$(BASE_PATH)/type/*.class bin/$(BASE_PATH)/type/
	cp src/$(BASE_PATH)/translator/*.class bin/$(BASE_PATH)/translator/
	cp src/antlr-4.5.3-complete.jar bin/
	cp mips_built_in.s bin/
	cp built_in.mx bin/
	$(MAKE) -C src clean
clean:
	$(MAKE) -C src clean
	-rm -rf bin