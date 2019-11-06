#!/bin/sh
#carbolemons stubborn automation build tool. i mean, i use the standard maven thingamajig anyways
cp ./libs/*.jar ./build/
cd ./build/
find ./ -name "*.jar" -exec jar -xf {} \;
rm *.jar
rm -r META-INF
cd ..
find ./ -name "*.java" > src.txt
javac -cp ".:./libs/*:./src/main/java" @src.txt -d ./build/
cd build
jar cvfm build.jar ../src/main/resources/META-INF/MANIFEST.MF .
cp build.jar ..
rm -r ../build/*
