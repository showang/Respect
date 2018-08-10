[![](https://jitpack.io/v/showang/Respect.svg)](https://jitpack.io/#showang/Respect)

# Respect
A simple RESTful API framework depends on native spec, without invasive.

# How to
To get a Git project into your build:

## Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
## Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.showang:Respect:0.0.1'
	}
