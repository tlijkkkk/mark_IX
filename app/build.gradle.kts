plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

val generatedAssetsDir = layout.buildDirectory.dir("generated/assets")

// copy sample files into src/main/assets before packaging
val copyPracticeAssets = tasks.register<Copy>("copyPracticeSourceCodeFilesToAssets") {
    val srcDir = projectDir.resolve("src/main/java")
    from(srcDir) {
        include("com/practice/lc/**/*.kt", "com/practice/lc/**/*.java") // include the `lc` folder and its Kotlin files
    }
    into(generatedAssetsDir.get().asFile)
    doFirst { generatedAssetsDir.get().asFile.mkdirs() } // ensure destination directory exists
}

tasks.named("preBuild") {
    dependsOn(copyPracticeAssets)
}

android {
    namespace = "com.practice.leetcode"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.practice.leetcode"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4" // use a version compatible with your Kotlin/Compose setup
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Controls emitted JVM bytecode version (classfile major version, e.g., "17")
    kotlinOptions {
        jvmTarget = "17"
    }

    // Tells Gradle which Java toolchain (JDK) to use for compilation, kapt, tests, etc.
    kotlin {
        jvmToolchain(17)
    }

    sourceSets["main"].assets.srcDirs(generatedAssetsDir.get().asFile)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp)
    implementation(libs.okhttp.longging.intercepter)
}
