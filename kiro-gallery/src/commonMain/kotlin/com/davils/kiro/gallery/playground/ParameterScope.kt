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

import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.motion.color.ArtPalette
import kotlin.reflect.KProperty1
import kotlin.time.Duration

private const val NOTHING: String = ""

internal class ParameterScope<S> {
    val parameters: MutableList<Parameter<S>> = mutableListOf()

    val fixed: MutableList<String> = mutableListOf()

    fun float(property: KProperty1<S, Float>, range: ClosedFloatingPointRange<Float>, set: S.(Float) -> S) {
        slider(property = property, range = range, measure = floats, set = set)
    }

    fun int(property: KProperty1<S, Int>, range: IntRange, set: S.(Int) -> S) {
        slider(property = property, range = range.first.toFloat()..range.last.toFloat(), measure = ints, set = set)
    }

    fun dp(property: KProperty1<S, Dp>, range: ClosedFloatingPointRange<Float>, set: S.(Dp) -> S) {
        slider(property = property, range = range, measure = dps, set = set)
    }

    fun duration(property: KProperty1<S, Duration>, range: ClosedFloatingPointRange<Float>, set: S.(Duration) -> S) {
        slider(property = property, range = range, measure = durations, set = set)
    }

    fun toggle(property: KProperty1<S, Boolean>, set: S.(Boolean) -> S) {
        parameters += Parameter(
            name = property.name,
            control = Control.Toggle,
            reader = { style, _ -> Setting.Flag(isOn = property.get(style)) },
            writer = { style, setting, _ -> flagOf(setting)?.let { isOn -> style.set(isOn) } ?: style },
            coder = { setting -> flagOf(setting)?.toString() ?: NOTHING },
        )
    }

    fun <V> pick(property: KProperty1<S, V>, choices: List<Choice<V>>, set: S.(V) -> S) {
        parameters += Parameter(
            name = property.name,
            control = Control.Menu(labels = choices.map { choice -> choice.label }),
            reader = { style, palette -> Setting.Pick(index = indexOf(choices, property.get(style), palette)) },
            writer = { style, setting, palette ->
                choiceOf(choices, setting)?.let { choice -> style.set(choice.value(palette)) } ?: style
            },
            coder = { setting -> choiceOf(choices, setting)?.code ?: NOTHING },
        )
    }

    inline fun <reified E : Enum<E>> choice(property: KProperty1<S, E>, noinline set: S.(E) -> S) {
        val type = E::class.simpleName.orEmpty()
        val choices = enumValues<E>().map { entry ->
            Choice(label = entry.name, code = "$type.${entry.name}") { _ -> entry }
        }
        pick(property = property, choices = choices, set = set)
    }

    fun fixed(property: KProperty1<S, *>) {
        fixed += property.name
    }

    private fun <V> slider(
        property: KProperty1<S, V>,
        range: ClosedFloatingPointRange<Float>,
        measure: Measure<V>,
        set: S.(V) -> S,
    ) {
        parameters += Parameter(
            name = property.name,
            control = Control.Slider(range = range, step = stepOf(range)),
            reader = { style, _ -> Setting.Amount(value = measure.amountOf(property.get(style))) },
            writer = { style, setting, _ ->
                amountOf(setting)?.let { amount -> style.set(measure.valueOf(amount)) } ?: style
            },
            coder = { setting -> amountOf(setting)?.let { amount -> codeOf(measure, amount) } ?: NOTHING },
        )
    }
}

private fun amountOf(setting: Setting): Float? = (setting as? Setting.Amount)?.value

private fun flagOf(setting: Setting): Boolean? = (setting as? Setting.Flag)?.isOn

private fun <V> choiceOf(choices: List<Choice<V>>, setting: Setting): Choice<V>? {
    val index = (setting as? Setting.Pick)?.index ?: return null
    return choices.getOrNull(index)
}

private fun <V> indexOf(choices: List<Choice<V>>, value: V, palette: ArtPalette): Int =
    choices.indexOfFirst { choice -> choice.value(palette) == value }

private fun <V> codeOf(measure: Measure<V>, amount: Float): String = measure.code(measure.valueOf(amount))
