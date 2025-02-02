buildscript {
    val kotlinVersion: String by project

    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:$kotlinVersion")
    }
}

plugins {
    java
    idea
    id("org.jetbrains.intellij") version "1.3.0"
    id("com.gradle.plugin-publish") version "0.19.0"
}

apply(plugin = "kotlin")

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
    kotlinOptions {
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
        jvmTarget = "1.8"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
    this.maybeCreate("main").apply {
        java {
            //srcDirs("korge-intellij-plugin/src/main/kotlin")
            //srcDirs("src/main/kotlin")
        }
        resources {
            //srcDirs("korge-intellij-plugin/src/main/resources")
            //srcDirs("src/main/resources")
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
}

val kbox2dVersion: String by project
val korgeVersion: String by project
val kotlinVersion: String by project

dependencies {
    //implementation("com.soywiz.korlibs.korge.plugins:korge-build:$korgeVersion")
    implementation("com.soywiz.korlibs.korge2:korge-jvm:$korgeVersion")
    implementation("com.soywiz.korlibs.korge2:korge-dragonbones-jvm:$korgeVersion")
    implementation("com.soywiz.korlibs.korge2:korge-spine-jvm:$korgeVersion")
    implementation("com.soywiz.korlibs.korge2:korge-swf-jvm:$korgeVersion")
    implementation("com.soywiz.korlibs.kbox2d:kbox2d:$kbox2dVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    //implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.8")
    //implementation("javax.xml.bind:jaxb-api:2.3.1")
    //implementation("com.sun.xml.bind:jaxb-impl:2.3.1")
    //implementation("net.sourceforge.mydoggy:mydoggy:1.4.2")
    //implementation("net.sourceforge.mydoggy:mydoggy-plaf:1.4.2")
    //implementation("net.sourceforge.mydoggy:mydoggy-api:1.4.2")
    //implementation("net.sourceforge.mydoggy:mydoggy-res:1.4.2")
    //implementation(project(":korge-build"))
}

val globalProps = properties

intellij {
    // IntelliJ IDEA dependency
    version.set("IC-2021.3.1")
    // Bundled plugin dependencies
    plugins.addAll(
        "gradle", "java", "platform-images", "Kotlin", "gradle-java"
    )

    pluginName.set("KorgePlugin")
    updateSinceUntilBuild.set(false)

    downloadSources.set(true)

    //sandboxDir.set(layout.projectDirectory.dir(".sandbox").toString())
}

tasks {
    val runIde by existing(org.jetbrains.intellij.tasks.RunIdeTask::class) {
        maxHeapSize = "4g"
    }
    val runDebugTilemap by creating(JavaExec::class) {
        //classpath = sourceSets.main.runtimeClasspath
        classpath = sourceSets["main"].runtimeClasspath + configurations["idea"]

        main = "com.soywiz.korge.intellij.editor.tile.MyTileMapEditorFrame"
    }
    val runUISample by creating(JavaExec::class) {
        //classpath = sourceSets.main.runtimeClasspath
        classpath = sourceSets["main"].runtimeClasspath + configurations["idea"]

        main = "com.soywiz.korge.intellij.ui.UIBuilderSample"
    }
}
