plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2"
}

android {
    namespace = "com.example.record_tool"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.record_tool"
        minSdk = 31
        targetSdk = 33
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
}

dependencies {

    implementation(libs.play.services.maps)
    runtimeOnly("com.google.android.material:material:1.12.0")
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("com.android.support.test:rules:1.0.2")
    androidTestImplementation ("com.android.support.test:runner:1.0.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test:runner:1.2.0")
    androidTestImplementation ("androidx.test:rules:1.2.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.1")
    testImplementation ("junit:junit:4.13")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.google.firebase:firebase-auth:23.1.0")
    implementation ("com.google.android.gms:play-services-auth:21.3.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    implementation ("com.google.firebase:firebase-firestore:25.1.1")
    implementation ("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation ("androidx.work:work-runtime:2.9.1")
    implementation ("com.google.firebase:firebase-storage:21.0.1")
// https://mvnrepository.com/artifact/org.mockito/mockito-all
    testImplementation ("org.mockito:mockito-all:1.10.19")
    androidTestImplementation ("org.mockito:mockito-android:4.11.0")
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation ("org.mockito:mockito-core:5.14.2")

}
