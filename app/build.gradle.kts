import java.util.Properties
import java.io.FileInputStream



plugins {
    id("com.android.application") version "8.2.0"


    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")

}
val getProperty: (String) -> String = { key ->
    val properties = Properties()
    val propertiesFile = FileInputStream(File("app/api.properties"))

    properties.load(propertiesFile)
    propertiesFile.close()
    properties.getProperty(key, "")
}

android {


    namespace = "com.example.farmguardian"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.farmguardian"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }//test

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Retrieve API_KEY value using getProperty function
            val apiKey = getProperty("API_KEY")

            buildConfigField("String", "API_KEY", "\"$apiKey\"")

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }



    kotlinOptions {
        jvmTarget = "1.8"
    }



}


dependencies {
    testImplementation("io.mockk:mockk:1.12.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    // Espresso core library
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Espresso contrib library
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")

    // Espresso Intents library (for testing intents)
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")

    // Espresso Accessibility library (for testing accessibility features)
    androidTestImplementation("androidx.test.espresso:espresso-accessibility:3.5.1")

    // Espresso Web library (for testing web views)
    androidTestImplementation("androidx.test.espresso:espresso-web:3.5.1")

    // Espresso Idling Resource library (for testing asynchronous operations)
    androidTestImplementation ("androidx.test.espresso:espresso-idling-resource:3.5.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.github.javafaker:javafaker:1.0.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("com.google.truth:truth:1.0.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    // JUnit 4 for unit testing
    testImplementation("junit:junit:4.13.2")

    // Truth library for assertions in unit and instrumentation tests
    testImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("com.google.truth:truth:1.0.1")

    // Kotlin coroutines test dependency for testing suspending functions
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // AndroidX test dependencies
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Mocking framework
    testImplementation("org.mockito:mockito-core:4.0.0")



    // Robolectric for unit testing with Android framework
    testImplementation("org.robolectric:robolectric:4.11.1")
    androidTestImplementation ("androidx.test:core:1.5.0")
    testImplementation("androidx.test:core:1.5.0")

    val kotestVersion =  "4.2.6"


    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")


}
