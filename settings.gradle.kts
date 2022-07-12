pluginManagement {
    val bndVersion: String by settings
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("biz.aQute.bnd.builder") version bndVersion
    }
}

rootProject.name = "angular"
