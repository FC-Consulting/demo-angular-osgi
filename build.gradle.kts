val osgiVersion: String by project

plugins {
    java
    id("biz.aQute.bnd.builder")
    `maven-publish`
}

group = "fr.fcolinet.fcconsulting"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {

    // OSGI
    compileOnly("org.osgi:osgi.cmpn:$osgiVersion")

}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.jar {
    enabled = true
    // Remove `plain` postfix from jar file name
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = rootProject.name
            version = project.version as String
            from(components["java"])
        }
    }
}

tasks.register("generateBnd") {
    group = "Angular"
    doFirst {
        copy {
            from("bnd.bnd.tpl")
            into(".")
            rename("bnd.bnd.tpl", "bnd.bnd")
            filter { line -> line.replace("%projectTitle%", "Angular :: Test") }
            filter { line -> line.replace("%projectGroup%", project.group as String) }
            filter { line -> line.replace("%projectName%", rootProject.name) }
            filter { line -> line.replace("%projectVersion%", project.version as String) }
            filter { line -> line.replace("%angularBuildPath%", "app/dist/test-angular") }
        }
    }
}

tasks.register("buildApp") {
    group = "Angular"
    doLast {
        exec {
            workingDir("app")
            commandLine("npm", "install")
        }
        exec {
            workingDir("app")
            commandLine("npm", "run", "build", "--prod", "--", "--base-href", "/${rootProject.name}/")
        }
    }
    finalizedBy(tasks.named("publishToMavenLocal"))
}
