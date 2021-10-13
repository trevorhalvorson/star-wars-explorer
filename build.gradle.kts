buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
    }
}
