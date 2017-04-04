# this script is called when the judge is building your compiler.
# no argument will be passed in.

set -e
cd "$(dirname "$0")"
mkdir -p bin
find ./Meh/src -name *.java | javac -d bin -classpath "Meh/lib/antlr-4.6-complete.jar" @/dev/stdin
