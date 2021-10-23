plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Sdks.compileSdkVersion

    defaultConfig {
        applicationId = "dev.trev.starwarsexplorer"
        minSdk = Sdks.minSdkVersion
        targetSdk = Sdks.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "PREFS_DS_FILE_NAME",
            "\"preferences\""
        )
        buildConfigField(
            "String",
            "SWAPI_BASE_URL",
            "\"https://www.swapi.tech/api/\""
        )
        buildConfigField(
            "String",
            "SW_DB_NAME",
            "\"sw_db\""
        )
        buildConfigField(
            "int",
            "SW_DB_VERSION",
            "1"
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
    }
}

dependencies {
    kapt(Deps.hiltCompiler)
    kapt(Deps.roomCompiler)

    implementation(Deps.appCompat)
    implementation(Deps.constraintLayout)
    implementation(Deps.core)
    implementation(Deps.dataStorePrefs)
    implementation(Deps.fragment)
    implementation(Deps.gson)
    implementation(Deps.hilt)
    implementation(Deps.legacySupport)
    implementation(Deps.lifeCycleLiveData)
    implementation(Deps.lifeCycleViewModel)
    implementation(Deps.lifeCycleRuntime)
    implementation(Deps.material)
    implementation(Deps.navFrag)
    implementation(Deps.navUi)
    implementation(platform(Deps.okhttpBom))
    implementation(Deps.okhttp)
    implementation(Deps.okhttpLoggingInt)
    implementation(Deps.paging)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGson)
    implementation(Deps.room)
    implementation(Deps.roomRuntime)
    implementation(Deps.swipe)

    kaptAndroidTest(Deps.hiltCompiler)

    androidTestImplementation(TestDeps.androidX)
    androidTestImplementation(TestDeps.archCore)
    androidTestImplementation(TestDeps.core)
    androidTestImplementation(TestDeps.espresso)
    androidTestImplementation(TestDeps.hilt)

    testImplementation(TestDeps.jUnit)
}
