import java.util.Properties
import java.io.FileInputStream



plugins {
    id("com.android.application") version "8.2.0"
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}
val getProperty: (String) -> String = { key ->
    val properties = Properties()
    val propertiesFile = FileInputStream(File("app/src/main/assets/api.properties"))

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
    
    val kotestVersion =  "4.2.6"


    // add the dependency for the Google AI client SDK for Android
    implementation("com.google.ai.client.generativeai:generativeai:0.2.2")

    //compose
    implementation("androidx.compose.ui:ui-tooling-preview-android:1.6.4")
    testImplementation("io.mockk:mockk:1.12.0")

    // Espresso Idling Resource library (for testing asynchronous operations)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-idling-resource:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")

    // Espresso contrib library
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")

    // Espresso Accessibility library (for testing accessibility features)
    androidTestImplementation("androidx.test.espresso:espresso-accessibility:3.5.1")

    // Espresso Web library (for testing web views)
    androidTestImplementation("androidx.test.espresso:espresso-web:3.5.1")


    //firebase
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

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // JUnit
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("com.google.truth:truth:1.0.1")

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

    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")

    //Jcompose
    implementation("androidx.compose.ui:ui:1.6.4")
    implementation("androidx.compose.material:material:1.6.4")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.4")

    //picasso
    implementation ("com.squareup.picasso:picasso:2.8")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

}
