plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeargs)
    id ("kotlin-parcelize")
    id ("com.google.firebase.crashlytics")
    id ("com.google.gms.google-services")
}

var version_name = "1.0.0"
var version_code = 100

android {
    namespace = "com.tugbaolcer.recipeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tugbaolcer.recipeapp"
        minSdk = 24
        targetSdk = 34
        versionCode = version_code
        versionName = version_name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }

        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("dev") {
            applicationId = "com.tugbaolcer.recipeapp"
            applicationIdSuffix = ".test"
            resValue ("string", "app_name", "RecipeApp ${version_name}-test")
            buildConfigField ("String", "ENDPOINT", "\"https://www.themealdb.com/api/json/v1/1/\"")
            versionNameSuffix = "-test"
        }

        create("staging") {
            applicationId = "com.tugbaolcer.recipeapp"
            applicationIdSuffix = ".prod"
            resValue ("string", "app_name", "RecipeApp ${version_name}-prod")
            buildConfigField ("String", "ENDPOINT", "\"https://www.themealdb.com/api/json/v1/1/\"")
            versionNameSuffix = "-prod"
        }
    }

    configurations {
        all {
            exclude( group = "androidx.lifecycle", module = "lifecycle-viewmodel-ktx")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewpager2)

    //Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //Networking and Data Serialization
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    //Firebase Integration
    implementation(libs.firebase.core)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics.ktx)


    //Security
    implementation(libs.androidx.security)

    //Google Play Services
    implementation(libs.app.update)
    implementation(libs.app.update.ktx)
    implementation(libs.play.services.base)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.tasks)

    //Glide
    implementation(libs.glide)


    //Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}