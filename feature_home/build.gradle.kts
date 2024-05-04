@Suppress("DSL_SCOPE_VIOLATION") // Because of IDE bug https://youtrack.jetbrains.com/issue/KTIJ-19370
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.symbolProcessing)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.testLogger)
    alias(libs.plugins.junit5Android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.yannick.leboncoin.feature.home"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        minSdk = libs.versions.minSdk.get().toInt()
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(projects.featureBase)
    implementation(projects.resources)
    implementation(libs.gson)
    implementation(libs.composeMaterial)
    implementation(libs.composeKoin)
    implementation(libs.timber)
    implementation(libs.coil)

    ksp(libs.roomCompiler)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.room)

    testImplementation(projects.libraryTestUtils)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.composeTestAndroid)
    debugImplementation(libs.composeDebug)
}
