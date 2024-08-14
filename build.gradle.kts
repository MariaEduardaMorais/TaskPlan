buildscript {
    val agp_version by extra("8.1.3")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    kotlin("jvm") version "1.8.10"
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}
