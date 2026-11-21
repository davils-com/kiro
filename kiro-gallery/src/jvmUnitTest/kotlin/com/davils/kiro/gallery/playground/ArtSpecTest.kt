/*
 * Copyright 2026 Davils
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.davils.kiro.gallery.playground

import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.export.renderFrame
import com.davils.kiro.ui.motion.scene.scene
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File
import kotlin.time.Duration.Companion.seconds

private const val WIDTH: Int = 160
private const val HEIGHT: Int = 100
private const val MOMENT: Int = 3

private val motionSources: File = File("../kiro-ui/kiro-ui-motion/src/commonMain/kotlin")
private val artClass: Regex = Regex(
    pattern = """^public class (\w+Art)\(public val style: \w+\? = null\)""",
    option = RegexOption.MULTILINE,
)
private val styleField: Regex = Regex(pattern = """^\s+public val (\w+): """, option = RegexOption.MULTILINE)

private fun sources(): List<File> = motionSources.walkTopDown().toList()

private fun artsInSources(): Set<String> {
    val files = sources().filter { file -> file.name.endsWith("Art.kt") }
    val names = files.mapNotNull { file -> nameOf(file.readText()) }
    return names.toSet()
}

private fun nameOf(text: String): String? {
    val match = artClass.find(text) ?: return null
    return match.groupValues[1]
}

private fun fieldsOf(style: String): List<String> {
    val file = sources().single { candidate -> candidate.name == "$style.kt" }
    val header = file.readText().substringAfter("public class $style(")
    val fields = styleField.findAll(header.substringBefore("\n) {"))
    return fields.map { match -> match.groupValues[1] }.toList()
}

private fun <S : Any> ArtSpec<S>.extremes(theme: Theme, palette: ArtPalette): List<Map<String, Setting>> {
    val style = defaultsOf(theme)
    return listOf(false, true).map { isHigh ->
        parameters.associate { parameter ->
            parameter.name to extremeOf(parameter = parameter, style = style, palette = palette, isHigh = isHigh)
        }
    }
}

private fun <S> extremeOf(parameter: Parameter<S>, style: S, palette: ArtPalette, isHigh: Boolean): Setting =
    when (val control = parameter.control) {
        is Control.Slider -> Setting.Amount(value = control.range.endInclusive.takeIf { isHigh } ?: control.range.start)
        Control.Toggle -> Setting.Flag(isOn = parameter.read(style, palette) == Setting.Flag(isOn = !isHigh))
        is Control.Menu -> Setting.Pick(index = control.labels.lastIndex.takeIf { isHigh } ?: 0)
    }

private fun <S : Any> ArtSpec<S>.checkRoundTrips(theme: Theme, palette: ArtPalette) {
    val style = defaultsOf(theme)
    parameters.forEach { parameter ->
        val defaults = parameter.read(style, palette)
        val reads = listOf(false, true).map { isHigh ->
            val extreme = extremeOf(parameter = parameter, style = style, palette = palette, isHigh = isHigh)
            val written = parameter.write(style, extreme, palette)
            val read = parameter.read(written, palette)

            parameter.write(style, read, palette) shouldBe written
            parameter.valueCode(read) shouldNotBe ""
            read
        }
        reads.any { read -> read != defaults } shouldBe true
    }
}

class ArtSpecTest : FunSpec({
    val theme = Theme.light()
    val palette = ArtPalettes.Refined.resolve(theme)

    context("specs") {
        test("cover every art of kiro-ui-motion") {
            val arts = artsInSources()

            arts.size shouldNotBe 0
            artSpecs.map { spec -> spec.art } shouldContainExactlyInAnyOrder arts
        }

        test("list every field of the style of their art") {
            artSpecs.forEach { spec ->
                val names = spec.parameters.map { parameter -> parameter.name } + spec.fixed

                names shouldContainExactlyInAnyOrder fieldsOf("${spec.art}Style")
            }
        }

        test("name their arts and style factories as the code shows them") {
            val spec = artSpecs.single { candidate -> candidate.name == "Isometric City" }

            spec.art shouldBe "IsometricCityArt"
            spec.styleFactory shouldBe "isometricCityArtStyle()"
            artFamilies.values.flatten() shouldBe artSpecs
        }
    }

    context("parameters") {
        test("read back what they write") {
            artSpecs.forEach { spec -> spec.checkRoundTrips(theme = theme, palette = palette) }
        }

        test("render every art at the ends of every range") {
            listOf(Theme.light(), Theme.dark()).forEach { current ->
                artSpecs.forEach { spec ->
                    spec.extremes(theme = current, palette = palette).forEach { settings ->
                        val art = spec.artOf(theme = current, palette = palette, settings = settings)
                        val frame = renderFrame(
                            scene = scene { layer(art) },
                            size = IntSize(width = WIDTH, height = HEIGHT),
                            time = MOMENT.seconds,
                            theme = current,
                        )

                        frame.width shouldBe WIDTH
                    }
                }
            }
        }

        test("keep the style of the theme until a field changes") {
            val spec = artSpecs.first()
            val art = spec.artOf(theme = theme, palette = palette, settings = emptyMap())
            val values = spec.settingsOf(theme = theme, palette = palette, settings = emptyMap())

            spec.changesOf(theme = theme, palette = palette, settings = emptyMap()) shouldBe emptyList()
            art.toString() shouldBe "${spec.art}(style=null)"
            values.keys shouldContainAll spec.parameters.map { parameter -> parameter.name }
        }
    }
})
