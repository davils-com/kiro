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

package com.davils.kiro.ui.motion.fixture

import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.geometry.OrbitArtStyle
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import java.lang.invoke.MethodHandles
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.time.Duration

private const val SMALL_WIDTH: Int = 96
private const val SMALL_HEIGHT: Int = 64
private const val DURATION_GETTER: String = "getPeriod-"

private val durationSuffix: String = durationGetter().substringAfter(delimiter = '-')

private val primitives: Map<Class<*>?, (String?) -> List<Any?>> = mapOf(
    Int::class.javaPrimitiveType to ::countsOf,
    Float::class.javaPrimitiveType to { _ -> listOf(0f, -1f) },
    Long::class.javaPrimitiveType to ::durationsOf,
    Boolean::class.javaPrimitiveType to { _ -> listOf(true, false) },
    List::class.java to { _ -> listOf(emptyList<Any>()) },
)

private fun durationGetter(): String {
    val methods = OrbitArtStyle::class.java.methods
    return methods.first { method -> method.name.startsWith(DURATION_GETTER) }.name
}

internal fun <S : Any> FunSpec.extremeChecks(name: String, style: S, artOf: (S) -> Art) {
    test("$name draws every extreme value of its style without throwing") {
        val variants = extremesOf(style = style)
        variants.size shouldBeGreaterThan 0
        variants.forEach { variant -> artOf(variant).drawAtExtremes() }
    }
}

private fun Art.drawAtExtremes() {
    render(Render(width = SMALL_WIDTH, height = SMALL_HEIGHT))
    render(Render(width = SMALL_WIDTH, height = SMALL_HEIGHT, time = later, quality = QualityLevel.Low))
    render(Render(width = SMALL_WIDTH, height = SMALL_HEIGHT, theme = Theme.dark(), density = 2f))
    render(Render(width = 1, height = 1))
    drawEmpty()
}

private fun <S : Any> extremesOf(style: S): List<S> {
    val type = style.javaClass
    val fields = type.declaredFields.filter { field -> !Modifier.isStatic(field.modifiers) }
    fields.forEach { field -> field.isAccessible = true }
    val values = fields.map { field -> field.get(style) }
    val constructor = MethodHandles.lookup().unreflectConstructor(constructorOf(type = type, count = fields.size))
    return fields.flatMapIndexed { index, field ->
        replacementsOf(field = field, type = type).map { replacement ->
            val arguments = values.toMutableList()
            arguments[index] = replacement
            type.cast(constructor.invokeWithArguments(arguments))
        }
    }
}

private fun constructorOf(type: Class<*>, count: Int): Constructor<*> {
    val constructor = type.declaredConstructors.first { candidate ->
        candidate.parameterCount == count && !candidate.isSynthetic
    }
    constructor.isAccessible = true
    return constructor
}

private fun replacementsOf(field: Field, type: Class<*>): List<Any?> {
    val getter = type.methods.firstOrNull { method -> method.name.startsWith("get${field.name.capitalized()}-") }
    val replacements = primitives[field.type] ?: return enumsOf(type = field.type)
    return replacements(getter?.name)
}

private fun countsOf(getter: String?): List<Any?> {
    if (getter != null) return emptyList()
    return listOf(0, -1)
}

private fun durationsOf(getter: String?): List<Any?> {
    if (getter == null || !getter.endsWith(suffix = durationSuffix)) return emptyList()
    return listOf(Duration.ZERO, Duration.INFINITE, -Duration.INFINITE).map { duration -> rawOf(duration) }
}

private fun rawOf(duration: Duration): Long {
    val boxed: Any = duration
    val field = boxed.javaClass.getDeclaredField("rawValue")
    field.isAccessible = true
    return field.getLong(boxed)
}

private fun enumsOf(type: Class<*>): List<Any?> = type.enumConstants?.toList().orEmpty()

private fun String.capitalized(): String = replaceFirstChar { character -> character.uppercaseChar() }
