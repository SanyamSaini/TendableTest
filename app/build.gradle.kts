plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.tendable.test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tendable.test"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.intuit.ssp:ssp-android:1.0.5")
    implementation ("com.intuit.sdp:sdp-android:1.0.5")

    //VM
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")

    //Kotlin
    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:3.14.9")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.12.1")

    //Room
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    // optional - RxJava2 support for Room
    implementation ("androidx.room:room-rxjava2:2.6.1")
    // optional - RxJava3 support for Room
    implementation ("androidx.room:room-rxjava3:2.6.1")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation ("androidx.room:room-guava:2.6.1")

    //Paging 3 Integration
    implementation ("androidx.room:room-paging:2.6.1")

    implementation ("androidx.room:room-ktx:2.6.1")
}