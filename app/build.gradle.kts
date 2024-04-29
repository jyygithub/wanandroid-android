plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleKsp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.jiangyy.wanandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jiangyy.wanandroid"
        minSdk = 26
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.4"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    debugImplementation(libs.leakcanary)
    implementation("androidx.core:core-ktx:1.9.0")
    implementation(libs.appcompat)
    implementation(libs.google.android.material)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.recyclerview)
    implementation(libs.swiperefreshlayout)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.guolindev.permissionx:permissionx:1.7.1")
    implementation("io.coil-kt:coil:2.2.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.github.franmontiel:PersistentCookieJar:v1.0.1")
    implementation("com.github.Justson.AgentWeb:agentweb-core:v4.1.9-androidx")
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper:4.0.0-beta04")
    implementation("com.koonny.appcompat:appcompat:1.0.0-SNAPSHOT")
    implementation("com.koonny.dialog:dialog:1.0.0-SNAPSHOT")
    implementation(libs.timber)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}