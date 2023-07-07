#!/bin/bash

# This copies the .env file, if exists, to the submodules directories
# so the test can load environment properties.
# Make sure you have a .env file in the root directory of the project

if [ ! -f .env ]; then
    echo "No .env file found - configure it first"
    exit 1
fi
# source the modules from the pom file and remove the brackets and any other character after the </modules> tag
MODULES=$(sed -n '/<modules>/,/<\/modules>/p' pom.xml | sed 's/<modules>//g' | sed 's/<\/modules>//g' | sed 's/<\/module>//g' | sed 's/<module>//g' | sed 's/ //g')
for module in $MODULES
do
    echo "Copying .env file to module directory: $module"
    cp .env $module
done