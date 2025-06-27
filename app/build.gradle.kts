plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kover)
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jmailen.kotlinter")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.tahn.minipokedex"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tahn.minipokedex"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
        }

        create("prod") {}
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // Or latest
    }

    kover {
        reports {
            filters {
                excludes {
                    classes(
                        "*.*BuildConfig*",
                        "*.*Module*",
                        "*.*Factory*",
                        "*.*Fragment*",
                        "*.*Activity*",
                        "*.*Database*",
                        "*.*Database_Impl*",
                        "*.retrofit.*Services*",
                        "*.App",
                        "*.BuildFlavor",
                        "*.SecretHelper",
                        "*.AndroidKeyStoreProvider",
                        "*.DispatcherProviderImpl",
                    )
                    // packages( )
                }
            }
        }
    }
}

dependencies {
    implementation(project(":feature"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.koin.androidx.compose)

    // Logger
    implementation(libs.timber)

    // DI
    val koin_version = "3.5.0"
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_version")
    implementation("io.insert-koin:koin-androidx-compose-navigation:$koin_version")

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose UI
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.material)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.activity.compose)

    // Jetpack navigation
    implementation(libs.navigation)
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    implementation(libs.kotlinx.serialization.json)
}
