#!/bin/bash

cd /home/$USER/Documents/Programming/Java/My\ Plugins/SpellBook

echo Compiling classes...
javac -cp deps/Bukkit/target/bukkit-1.8.3-R0.1-SNAPSHOT.jar \
	io/github/harryprotist/*.java \
	io/github/harryprotist/spellfunction/*.java \
|| exit

echo Compiling jar...
jar -cf SpellBook.jar \
	io/github/harryprotist/*.class \
	io/github/harryprotist/spellfunction/*.class \
	plugin.yml \
|| exit

echo Installing plugin...
cp SpellBook.jar ../BukkitTest/plugins
cp spell.txt ../BukkitTest/

echo Cleaning up...
rm io/github/harryprotist/*.class
rm io/github/harryprotist/spellfunction/*.class
