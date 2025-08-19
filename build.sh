#!/bin/bash

# DonateTeleport Build Script
# This script shows how to build the plugin properly with Spigot dependencies

echo "DonateTeleport Minecraft Plugin Build Instructions"
echo "=================================================="
echo ""
echo "This plugin requires the Spigot API to compile properly."
echo "To build this plugin:"
echo ""
echo "1. First, uncomment the repositories and dependencies sections in pom.xml"
echo "2. Make sure you have internet access to download the Spigot API"
echo "3. Run: mvn clean package"
echo ""
echo "Alternatively, you can install Spigot locally:"
echo "1. Download BuildTools.jar from https://hub.spigotmc.org/jenkins/job/BuildTools/"
echo "2. Run: java -jar BuildTools.jar --rev 1.20.1"
echo "3. This will install Spigot to your local Maven repository"
echo "4. Then run: mvn clean package"
echo ""
echo "The compiled plugin JAR will be in the target/ directory"
echo "Copy it to your server's plugins/ folder and restart the server"
echo ""

# Check if we have internet access and can build
echo "Attempting to build (this may fail in restricted environments)..."
mvn clean compile 2>/dev/null
if [ $? -eq 0 ]; then
    echo "Build successful!"
    mvn package
else
    echo "Build failed - likely due to missing Spigot API dependency"
    echo "See instructions above for proper setup"
fi