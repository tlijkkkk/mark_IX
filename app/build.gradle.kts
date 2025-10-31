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
        include("com/practice/lc/**/*.kt") // include the `lc` folder and its Kotlin files
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
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    sourceSets["main"].assets.srcDirs(generatedAssetsDir.get().asFile)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}

