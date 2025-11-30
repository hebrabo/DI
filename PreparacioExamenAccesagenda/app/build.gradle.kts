plugins {
    // 1. Plugins principales (¡Evita la duplicación usando solo alias o solo id!)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)


}

android {
    namespace = "com.example.accesagenda"
    compileSdk = 36 // Versión de SDK para compilar

    defaultConfig {
        applicationId = "com.example.accesagenda"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        // Desactiva Compose si estás usando solo vistas basadas en XML/Fragments
        compose = false
        // Activa View Binding para usarlo en tus Fragments o Activities
        viewBinding = true
    }
}

dependencies {
    // --- Básicas del Sistema ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // --- UI Clásica (XML) ---
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // ConstraintLayout (se incluye la versión explícita en caso de no estar en libs.versions.toml)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // --- Navegación (Fragments) ---
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // --- Tests ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Dependencias de Compose (Comentadas o Opcionales, basadas en tu configuración) ---
    /*
    // La plataforma BOM ayuda a gestionar versiones consistentes
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))

    // Dependencias individuales de Compose UI y Material (necesarias para Color.kt)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    */
}