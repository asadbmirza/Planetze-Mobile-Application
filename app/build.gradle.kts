import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val newsApiKey: String = localProperties.getProperty("NEWS_API_KEY")
val youtubeApiKey: String = localProperties.getProperty("YOUTUBE_API_KEY")
val rapidApiKey: String = localProperties.getProperty("RAPID_API_KEY")

plugins {
    id("com.google.gms.google-services")
    alias(libs.plugins.android.application)

}

android {
    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
        }
    }
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
    }

    buildTypes {
        debug {
            buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")
            buildConfigField("String", "YOUTUBE_API_KEY", "\"$youtubeApiKey\"")
            buildConfigField("String", "RAPID_API_KEY", "\"$rapidApiKey\"")
        }
        release {
            buildConfigField("String", "NEWS_API_KEY", "\"${newsApiKey}\"")
            buildConfigField("String", "YOUTUBE_API_KEY", "\"$youtubeApiKey\"")
            buildConfigField("String", "RAPID_API_KEY", "\"$rapidApiKey\"")
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
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.database)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    implementation(libs.google.api.client.android)
    implementation(libs.google.api.services.youtube)
    implementation(libs.google.http.client.android)
    implementation(libs.google.http.client)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}