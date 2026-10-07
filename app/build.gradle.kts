import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

val localProperties = Properties().apply {
    val localFile = rootProject.file("local.properties")
    if (localFile.exists()) {
        localFile.inputStream().use { load(it) }
    }
}

fun safeProperty(name: String, defaultValue: String = ""): String =
    localProperties.getProperty(name, defaultValue)
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")

val smtpUser = safeProperty("SMTP_USER")
val smtpPassword = safeProperty("SMTP_PASSWORD")
val smtpTo = safeProperty("SMTP_TO")
val smtpHost = safeProperty("SMTP_HOST", "smtp.gmail.com")
val smtpPort = safeProperty("SMTP_PORT", "587")

android {
    namespace = "com.example.lecano"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.lecano"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "SMTP_USER", "\"$smtpUser\"")
        buildConfigField("String", "SMTP_PASSWORD", "\"$smtpPassword\"")
        buildConfigField("String", "SMTP_TO", "\"$smtpTo\"")
        buildConfigField("String", "SMTP_HOST", "\"$smtpHost\"")
        buildConfigField("String", "SMTP_PORT", "\"$smtpPort\"")
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/LICENSE"
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.fragment:fragment-ktx:1.8.9")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("com.mikhaellopez:circularimageview:4.3.1")

    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
