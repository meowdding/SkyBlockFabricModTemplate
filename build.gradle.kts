import org.gradle.api.internal.artifacts.DefaultModuleIdentifier
import org.gradle.api.internal.artifacts.dependencies.DefaultMinimalDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultMutableVersionConstraint
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    idea
    alias(libs.plugins.loom)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.repo)
    alias(libs.plugins.resources)
}

repositories {
    maven(url = "https://maven.teamresourceful.com/repository/maven-public/")
    maven(url = "https://repo.hypixel.net/repository/Hypixel/")
    maven(url = "https://api.modrinth.com/maven")
    maven(url = "https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
    maven(url = "https://maven.nucleoid.xyz")
    mavenCentral()
    mavenLocal()
}

ksp {
    arg("meowdding.modules.project_name", project.name)
    arg("meowdding.modules.package", "com.example.exampleMod.generated")
}

dependencies {
    compileOnly(libs.meowdding.ktmodules)
    ksp(libs.meowdding.ktmodules)

    minecraft(libs.minecraft)
    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment(libs.parchmentmc.get().withMcVersion().toString())
    })

    modImplementation(libs.bundles.fabric)

    implementation(libs.repo) // included in sbapi, exposed through implementation

    includeModImplementationBundle(libs.bundles.sbapi)
    includeModImplementationBundle(libs.bundles.rconfig)
    includeModImplementationBundle(libs.bundles.libs)
    includeModImplementationBundle(libs.bundles.meowdding)

    includeImplementation(libs.keval)

    modRuntimeOnly(libs.devauth)
    modRuntimeOnly(libs.modmenu)
}

loom {
    runs {
        getByName("client") {
            property("devauth.configDir", rootProject.file(".devauth").absolutePath)
        }
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    compileKotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }
}

compactingResources {
    basePath = "repo"
}

java {
    withSourcesJar()
}

// <editor-fold desc="Util Methods">
fun ExternalModuleDependency.withMcVersion(): ExternalModuleDependency {
    return DefaultMinimalDependency(
        DefaultModuleIdentifier.newId(this.group, this.name.replace("<mc_version>", libs.versions.minecraft.get())),
        DefaultMutableVersionConstraint(this.versionConstraint)
    )
}

@Suppress("unused")
fun DependencyHandlerScope.includeImplementationBundle(bundle: Provider<ExternalModuleDependencyBundle>) =
    bundle.get().forEach {
        includeImplementation(provider { it })
    }

fun DependencyHandlerScope.includeModImplementationBundle(bundle: Provider<ExternalModuleDependencyBundle>) =
    bundle.get().forEach {
        includeModImplementation(provider { it })
    }

fun <T : ExternalModuleDependency> DependencyHandlerScope.includeImplementation(dependencyNotation: Provider<T>) =
    with(dependencyNotation.get().withMcVersion()) {
        include(this)
        modImplementation(this)
    }

fun <T : ExternalModuleDependency> DependencyHandlerScope.includeModImplementation(dependencyNotation: Provider<T>) =
    with(dependencyNotation.get().withMcVersion()) {
        include(this)
        modImplementation(this)
    }
// </editor-fold>
