plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.todolist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todolist"
        minSdk = 30
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    //implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.core.ktx.v1131)
    //implementation("androidx.core:core-ktx:1.13.1")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.recyclerview:recyclerview:1.2.1")


//    implementation("androidx.recyclerview:recyclerview:1.3.2")
//    // For control over item selection of both touch and mouse driven selection
//    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

//    implementation ("androidx.appcompat:appcompat:1.4.1")
//    implementation ("androidx.recyclerview:recyclerview:1.2.1")


//    implementation ("androidx.core:core:1.14.0")
//    androidTestImplementation ("androidx.core:core-ktx:1.14.0")


}

//dependencies {
//    implementation("androidx.core:core-ktx:1.14.0") // Latest compatible for SDK 34
//    implementation("androidx.appcompat:appcompat:1.4.1") // Stable and compatible with SDK 34
//    implementation("com.google.android.material:material:1.4.0") // Material design components
//    implementation("androidx.activity:activity-ktx:1.4.0") // Activity library
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // ConstraintLayout
//
//    // Testing dependencies
//    testImplementation("junit:junit:4.13.2") // Unit testing
//    androidTestImplementation("androidx.test.ext:junit:1.1.3") // AndroidX JUnit
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0") // Espresso for UI tests
//
//    // RecyclerView dependencies
//    implementation("androidx.recyclerview:recyclerview:1.2.1") // Stable version for RecyclerView
//    implementation("androidx.recyclerview:recyclerview-selection:1.0.0") // For item selection control
//
//}
