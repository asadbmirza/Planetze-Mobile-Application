import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val newsApiKey: String = localProperties.getProperty("NEWS_API_KEY")

plugins {
    id("com.google.gms.google-services")
    alias(libs.plugins.android.application)

}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.plantezemobileapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.plantezemobileapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        println(newsApiKey)
    }

    buildTypes {
        debug {
            buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")
        }
        release {
            buildConfigField("String", "NEWS_API_KEY", "\"${newsApiKey}\"")
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.database)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    implementation(libs.picasso)
}