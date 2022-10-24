plugins {
    id("java")
    `maven-publish`
}

group = "gg.overcast.commands"
version = "1.0"

repositories {
    mavenCentral()
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "gg.overcast"
            artifactId = "OvercastCommands"
            version = "1.0"
            from(components["java"])
        }
    }
}