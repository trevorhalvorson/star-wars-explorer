object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val hilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val nav by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}" }
}

object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val core by lazy { "androidx.core:core-ktx:${Versions.core}" }
    val dataStorePrefs by lazy { "androidx.datastore:datastore-preferences:${Versions.dataStorePrefs}" }
    val fragment by lazy { "androidx.fragment:fragment-ktx:${Versions.fragment}" }
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val legacySupport by lazy { "androidx.legacy:legacy-support-v4:${Versions.legacySupport}" }
    val lifeCycleLiveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}" }
    val lifeCycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}" }
    val lifeCycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val navFrag by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.nav}" }
    val navUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.nav}" }
    val okhttpBom by lazy { "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}" }
    val okhttp by lazy { "com.squareup.okhttp3:okhttp" }
    val okhttpLoggingInt by lazy { "com.squareup.okhttp3:logging-interceptor" }
    val paging by lazy { "androidx.paging:paging-runtime-ktx:${Versions.paging}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}}" }
    val retrofitGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val room by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val swipe by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe}" }
}

object TestDeps {
    val androidX by lazy { "androidx.test.ext:junit-ktx:${TestVersions.androidX}" }
    val archCore by lazy { "androidx.arch.core:core-testing:${TestVersions.archCore}" }
    val core by lazy { "androidx.test:core-ktx:${TestVersions.core}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${TestVersions.espresso}" }
    val hilt by lazy { "com.google.dagger:hilt-android-testing:${Versions.hilt}" }
    val jUnit by lazy { "junit:junit:${TestVersions.jUnit}" }
}