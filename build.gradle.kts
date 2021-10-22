buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.android)
        classpath(BuildPlugins.hilt)
        classpath(BuildPlugins.kotlin)
        classpath(BuildPlugins.nav)
    }
}
