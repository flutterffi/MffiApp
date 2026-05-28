plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
}

tasks.register("checkArchitecture") {
    group = "verification"
    description = "Checks module dependency and import boundaries."

    doLast {
        val violations = mutableListOf<String>()
        val featureNames = setOf("home", "explore", "messages", "profile")
        val concreteFeatureModules = featureNames.map { ":feature:$it" }.toSet()
        val featureDependencyPattern = Regex("""project\("(:feature:[^"]+)"\)""")
        val featureImportPattern = Regex("""^import com\.flutterffi\.mffiapp\.feature\.([a-zA-Z0-9_]+)\.""")

        fun String.relativePath(): String = rootDir.toPath().relativize(file(this).toPath()).toString()

        fun collectFeatureProjectDependencies(modulePath: String): Set<String> {
            val buildFile = file(modulePath.removePrefix(":").replace(':', '/') + "/build.gradle.kts")
            if (!buildFile.exists()) return emptySet()
            return featureDependencyPattern.findAll(buildFile.readText())
                .map { it.groupValues[1] }
                .toSet()
        }

        concreteFeatureModules.forEach { module ->
            val illegalDependencies = collectFeatureProjectDependencies(module) - ":feature:shared"
            illegalDependencies.forEach { dependency ->
                violations += "$module must not depend on $dependency"
            }
        }

        collectFeatureProjectDependencies(":feature:shared").forEach { dependency ->
            violations += ":feature:shared must not depend on $dependency"
        }

        fileTree("core") {
            include("**/build.gradle.kts")
        }.forEach { buildFile ->
            featureDependencyPattern.findAll(buildFile.readText()).forEach { match ->
                violations += "${buildFile.path.relativePath()} must not depend on ${match.groupValues[1]}"
            }
        }

        fileTree("feature") {
            include("**/*.kt")
            exclude("**/build/**")
        }.forEach { sourceFile ->
            val moduleName = sourceFile.toPath()
                .let { file("feature").toPath().relativize(it) }
                .first()
                .toString()
            sourceFile.readLines().forEachIndexed { index, line ->
                val importedFeature = featureImportPattern.find(line)?.groupValues?.get(1)
                if (importedFeature != null && importedFeature != moduleName && importedFeature != "shared") {
                    violations += "${sourceFile.path.relativePath()}:${index + 1} imports feature.$importedFeature from feature.$moduleName"
                }
            }
        }

        fileTree("core/navigation/src") {
            include("**/*.kt")
            exclude("**/build/**")
        }.forEach { sourceFile ->
            sourceFile.readLines().forEachIndexed { index, line ->
                if (line.startsWith("import com.flutterffi.mffiapp.feature.")) {
                    violations += "${sourceFile.path.relativePath()}:${index + 1} imports a feature module"
                }
            }
        }

        fileTree("core/domain/src") {
            include("**/*.kt")
            exclude("**/build/**")
        }.forEach { sourceFile ->
            sourceFile.readLines().forEachIndexed { index, line ->
                val forbiddenImport = line.startsWith("import com.flutterffi.mffiapp.core.data.") ||
                    line.startsWith("import com.flutterffi.mffiapp.core.navigation.") ||
                    line.startsWith("import com.flutterffi.mffiapp.feature.") ||
                    line.startsWith("import com.flutterffi.mffiapp.di.") ||
                    line.startsWith("import com.flutterffi.mffiapp.navigation.")
                if (forbiddenImport) {
                    violations += "${sourceFile.path.relativePath()}:${index + 1} imports outside the domain boundary"
                }
            }
        }

        if (violations.isNotEmpty()) {
            throw GradleException(violations.joinToString(separator = "\n", prefix = "Architecture violations:\n"))
        }
    }
}
