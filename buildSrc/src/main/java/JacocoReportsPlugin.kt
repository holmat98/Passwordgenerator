import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.LibraryVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.DefaultDomainObjectSet
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoReportsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            plugins.apply(JACOCO_PLUGIN_ID)

            doAfterEvaluate()

            dependencies {
                "implementation"(JACOCO_DEPENDENCY)
            }
        }
    }

    private fun Project.doAfterEvaluate() =
        afterEvaluate {
            val variants = getAllVariants()
            val buildDirectory = layout.buildDirectory

            variants.forEach { variant ->
                val testTaskName = "test${variant.name.capitalized()}UnitTest"

                val javaDirectories = fileTree(
                    "${buildDirectory}/tmp/kotlin-classes/${variant.name}"
                ) { exclude(EXCLUDED_FILES) }

                tasks.register<JacocoReport>("${testTaskName}Coverage") {
                    dependsOn(testTaskName)
                    group = "Reporting"
                    description = "Generate Jacoco coverage reports on the ${variant.name} build."

                    reports {
                        csv.required.set(true)
                        xml.required.set(false)
                        html.required.set(true)
                    }

                    classDirectories.setFrom(files(javaDirectories))
                    sourceDirectories.setFrom(files(COVERAGE_SOURCE_DIRS))
                    additionalSourceDirs.setFrom(files(COVERAGE_SOURCE_DIRS))
                    executionData.setFrom("${buildDirectory}/jacoco/${testTaskName}.exec")
                }
            }
        }

    private fun Project.getAllVariants(): List<BaseVariant> {
        val variants = mutableListOf<BaseVariant>()

        libraryVariants?.forEach { variants.add(it) }

        applicationVariants?.forEach { variants.add(it) }

        return variants
    }

    private val Project.applicationVariants: DomainObjectSet<ApplicationVariant>?
        get() = extensions.findByType(AppExtension::class.java)?.applicationVariants

    private val Project.libraryVariants: DefaultDomainObjectSet<LibraryVariant>?
        get() = extensions.findByType(LibraryExtension::class.java)?.libraryVariants

    private companion object {
        const val JACOCO_PLUGIN_ID = "jacoco"
        const val JACOCO_DEPENDENCY = "org.jacoco:org.jacoco.core:0.8.11"
        val EXCLUDED_FILES = listOf(
            "**/R.class",
            "**/R*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "ndroid/**",
            "ndroidx/**",
            "om/google/**",
            "om/intellij/**'",
            "unit/**",
            "rg/**",
            "**/di/**",
            "*/models/**",
            "*/extensions/**",
            "*/utils/**"
        )
        val COVERAGE_SOURCE_DIRS = listOf("src/main/java")
    }
}
