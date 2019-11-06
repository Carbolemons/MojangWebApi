#!/bin/bash
#Discord Carbolemons#0001 
#github.com/Carbolemons
#https://carbolemons.com
#set up project working tree
#create directories
mkdir build
mkdir libs
mkdir -p src/main/java
mkdir -p src/main/resources/META-INF
#setup app manifest
touch src/main/resources/META-INF/MANIFEST.MF
echo "Manifest-Version: 1.0" >> src/main/resources/META-INF/MANIFEST.MF
echo "Class-Path: ." >> src/main/resources/META-INF/MANIFEST.MF
#take user input for manifest and directory
echo "== example input for below =="
echo "Main-Class: com.carbolemons.ExamplePackage.ExampleMainClass"
echo "-----------------------------"
read -p "Main-Class: " mainclass
echo "Main-Class: " $mainclass >> src/main/resources/META-INF/MANIFEST.MF #complete manifest
cd src/main/java #enter classpath
#split the main class, and create the project directories
IFS='.' 
read -ra directory <<< "$mainclass"
len=${#directory[@]}
for((i=0; i<$len-1; i++)); do #create directories minus main class
    mkdir "${directory[$i]}"
    cd "${directory[$i]}"
done
touch ${directory[$len-1]}.java #create main class
IFS=' '
