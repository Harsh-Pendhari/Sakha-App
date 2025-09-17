plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // ✅ Keep this
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.sakha"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sakha"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Explicitly Added
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    // ✅ Firebase BOM (manages versions)
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))

    // ✅ Firebase SDKs
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

}