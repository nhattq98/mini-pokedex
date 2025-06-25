
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kover)
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jmailen.kotlinter")
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
                    packages(
                        "com.tahn.githubusers.databinding",
                        "com.tahn.githubusers.ui.utils",
                        "com.tahn.githubusers.ui.base",
                        "com.tahn.githubusers.ui.dialog",
                        "com.tahn.githubusers.ui.view",
                        "com.tahn.githubusers.ui.feature.*.adapter",
                    )
                }
            }
        }
    }
}

dependencies {
    kover(project(":domain"))
    kover(project(":data"))

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Logger
    implementation(libs.timber)

    // DI
    implementation(libs.koin)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation)

    // Image
    implementation(libs.glide)

    // SwipeToRefresh
    implementation(libs.swipeRefreshLayout)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose UI
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Coil for images (optional)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Jetpack Paging 3
    implementation("androidx.paging:paging-runtime:3.2.1")

    // Jetpack Paging Compose
    implementation("androidx.paging:paging-compose:3.2.1") // <-- this is required
}
