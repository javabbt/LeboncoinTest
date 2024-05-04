rootProject.name = "LeboncoinTest"

include(
    ":app",
    ":feature_home",
    ":feature_base",
    ":library_test_utils",
    ":resources",
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("compileSdk", "34")
            version("targetSdk", "34")
            version("versionCode", "1")
            version("versionName", "0.0.1")
            version("minSdk", "24")

            version("kotlin", "1.8.10")
            version("coil", "2.2.2")
            version("kotlinSymbolProcessing", "1.8.10-1.0.9")
            version("kotlinCompilerExtensionVersion", "1.4.2")
            version("testLogger", "3.2.0")
            version("coroutines", "1.6.4")
            version("material", "1.8.0")
            version("retrofit", "2.9.0")
            version("okhttp", "4.10.0")
            version("koin", "3.5.6")
            version("room", "2.5.0")
            version("kotlinxSerializationConverter", "0.8.0")
            version("timber", "5.0.1")
            version("compose", "1.3.3")
            version("junit", "4.13.2")
            version("jupiter", "5.9.3")
            version("compose_activity", "1.8.2")
            version("materialCompose", "1.0.1")
            version("accompanistFlowLayout", "0.28.0")
            version("spotless", "6.15.0")
            version("detekt", "1.22.0")
            version("androidGradlePlugin", "7.4.1")
            version("junit", "5.9.2")
            version("androidJUnit5", "1.8.2.1")
            version("kluent", "1.72")
            version("testRunner", "1.5.2")
            version("mockk", "1.13.4")
            version("espresso", "3.5.1")
            version("lifecycle", "2.6.0-alpha03")
            version("coreKtx", "1.9.0")
            version("coreTesting", "2.2.0")
            version("composeKoin", "3.5.6")
            version("composeNavigation", "2.7.6")
            version("gson", "2.11.0")
            version("turbine", "0.7.0")
            version("truth", "1.1.3")
            version("testCoroutine", "1.5.1")
            version("composeTest", "1.5.4")

            // Gradle Plugins https://plugins.gradle.org/
            plugin("android-application", "com.android.application").versionRef("androidGradlePlugin")
            plugin("android-library", "com.android.library").versionRef("androidGradlePlugin")
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-android", "org.jetbrains.kotlin.android").versionRef("kotlin")
            plugin("kotlin-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("spotless", "com.diffplug.spotless").versionRef("spotless")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")
            plugin("junit5Android", "de.mannodermaus.android-junit5").versionRef("androidJUnit5")
            plugin("testLogger", "com.adarshr.test-logger").versionRef("testLogger")
            plugin("kotlin-symbolProcessing", "com.google.devtools.ksp").versionRef("kotlinSymbolProcessing")

            library("kotlin", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            library("coroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-android").versionRef("coroutines")
            library("retrofitCore", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library(
                "kotlinxSerializationConverter",
                "com.jakewharton.retrofit",
                "retrofit2-kotlinx-serialization-converter",
            ).versionRef("kotlinxSerializationConverter")
            library("okhttp", "com.squareup.okhttp3", "okhttp").versionRef("okhttp")
            library("okhttpInterceptor", "com.squareup.okhttp3", "logging-interceptor").versionRef("okhttp")
            library("koin", "io.insert-koin", "koin-android").versionRef("koin")
            library("koinNavigation", "io.insert-koin", "koin-androidx-navigation").versionRef("koin")
            library("timber", "com.jakewharton.timber", "timber").versionRef("timber")

            library("composeMaterial", "androidx.compose.material3", "material3").versionRef("materialCompose")

            library("composeKoin", "io.insert-koin", "koin-androidx-compose").versionRef("composeKoin")
            library("composeNavigation", "androidx.navigation", "navigation-compose").versionRef("composeNavigation")

            library("viewmodelKtx", "androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef("lifecycle")
            library("composeUI", "androidx.compose.ui", "ui").versionRef("compose")
            library("composeActivity", "androidx.activity", "activity-compose").versionRef("compose_activity")
            library("toolingPreview", "androidx.compose.ui", "ui-tooling-preview").versionRef("compose")
            library("roomKtx", "androidx.room", "room-ktx").versionRef("room")
            library("roomRuntime", "androidx.room", "room-runtime").versionRef("room")
            library("roomCompiler", "androidx.room", "room-compiler").versionRef("room")

            library("junit", "junit", "junit").versionRef("junit")
            library("testCoroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-test").versionRef("coroutines")
            library("kluent", "org.amshove.kluent", "kluent-android").versionRef("kluent")

            library("materialGoogle", "com.google.android.material", "material").versionRef("material")

            library("coreKtx", "androidx.core", "core-ktx").versionRef("coreKtx")
            library("coreTesting", "androidx.arch.core", "core-testing").versionRef("coreTesting")

            library("testRunner", "androidx.test", "runner").versionRef("testRunner")
            library("espresso", "androidx.test.espresso", "espresso-core").versionRef("espresso")
            library("mockk", "io.mockk", "mockk").versionRef("mockk")
            library("junitJupiterApi", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
            library("junitJupiterEngine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("jupiter")
            library("coil", "io.coil-kt", "coil-compose").versionRef("coil")
            library("gson", "com.squareup.retrofit2", "converter-gson").versionRef("gson")

            library("composeTest", "androidx.compose.ui", "ui-test").versionRef("composeTest")
            library("composeTestAndroid", "androidx.compose.ui", "ui-test-junit4").versionRef("composeTest")
            library("composeDebug", "androidx.compose.ui", "ui-test-manifest").versionRef("composeTest")

            library("turbine", "app.cash.turbine", "turbine").versionRef("turbine")
            library("truth", "com.google.truth", "truth").versionRef("truth")

            bundle("retrofit", listOf("retrofitCore", "kotlinxSerializationConverter", "okhttp", "okhttpInterceptor"))
            bundle("koin", listOf("koin", "koinNavigation"))
            bundle("compose", listOf("composeUI", "toolingPreview", "composeActivity", "coil", "composeKoin", "composeNavigation"))
            bundle("lifecycle", listOf("viewmodelKtx"))
            bundle("room", listOf("roomKtx", "roomRuntime"))
            bundle(
                "test",
                listOf(
                    "testCoroutines",
                    "kluent",
                    "testRunner",
                    "espresso",
                    "mockk",
                    "junitJupiterApi",
                    "junitJupiterEngine",
                    "coreTesting",
                    "junit",
                    "turbine",
                    "truth",
                    "composeTest",
                ),
            )
        }
    }
}
