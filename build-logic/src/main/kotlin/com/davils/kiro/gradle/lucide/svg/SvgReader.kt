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

package com.davils.kiro.gradle.lucide.svg

import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

private const val VIEW_BOX: String = "0 0 24 24"
private const val DISALLOW_DOCTYPE: String = "http://apache.org/xml/features/disallow-doctype-decl"
private const val NO_FILL: String = "none"
private val SEPARATORS: Regex = Regex("[\\s,]+")

internal object SvgReader {
    private val readers: Map<String, (Element) -> SvgShape> = mapOf(
        "path" to ::pathOf,
        "circle" to ::circleOf,
        "ellipse" to ::ellipseOf,
        "rect" to ::rectOf,
        "line" to ::lineOf,
        "polyline" to ::polylineOf,
        "polygon" to ::polygonOf,
    )

    fun read(svg: String): List<SvgShape> {
        val root = rootOf(svg)
        require(root.getAttribute("viewBox") == VIEW_BOX) { "An icon must use the view box $VIEW_BOX." }
        val children = root.childNodes
        val elements = (0 until children.length).map(children::item)
        return elements.filterIsInstance<Element>().map(::shapeOf)
    }

    private fun rootOf(svg: String): Element {
        val factory = DocumentBuilderFactory.newInstance()
        factory.setFeature(DISALLOW_DOCTYPE, true)
        factory.isXIncludeAware = false
        factory.isExpandEntityReferences = false
        val builder = factory.newDocumentBuilder()
        val document = StringReader(svg).use { reader -> builder.parse(InputSource(reader)) }
        return document.documentElement
    }

    private fun shapeOf(element: Element): SvgShape {
        val reader = requireNotNull(readers[element.tagName]) { "The element <${element.tagName}> is not supported." }
        return reader(element)
    }

    private fun pathOf(element: Element): SvgShape = SvgShape.Path(
        data = element.getAttribute("d"),
        isFilled = element.isFilled(),
    )

    private fun circleOf(element: Element): SvgShape = SvgShape.Circle(
        cx = element.number("cx"),
        cy = element.number("cy"),
        r = element.number("r"),
        isFilled = element.isFilled(),
    )

    private fun ellipseOf(element: Element): SvgShape = SvgShape.Ellipse(
        cx = element.number("cx"),
        cy = element.number("cy"),
        rx = element.number("rx"),
        ry = element.number("ry"),
        isFilled = element.isFilled(),
    )

    private fun rectOf(element: Element): SvgShape = SvgShape.Rect(
        x = element.optionalNumber("x") ?: 0.0,
        y = element.optionalNumber("y") ?: 0.0,
        width = element.number("width"),
        height = element.number("height"),
        rx = element.optionalNumber("rx"),
        ry = element.optionalNumber("ry"),
        isFilled = element.isFilled(),
    )

    private fun lineOf(element: Element): SvgShape = SvgShape.Line(
        x1 = element.number("x1"),
        y1 = element.number("y1"),
        x2 = element.number("x2"),
        y2 = element.number("y2"),
        isFilled = element.isFilled(),
    )

    private fun polylineOf(element: Element): SvgShape = SvgShape.Polyline(
        points = element.points(),
        isFilled = element.isFilled(),
    )

    private fun polygonOf(element: Element): SvgShape = SvgShape.Polygon(
        points = element.points(),
        isFilled = element.isFilled(),
    )

    private fun Element.number(name: String): Double =
        requireNotNull(optionalNumber(name)) { "The element <$tagName> needs the attribute $name." }

    private fun Element.optionalNumber(name: String): Double? {
        val value = getAttribute(name)
        if (value.isEmpty()) return null
        return value.toDouble()
    }

    private fun Element.points(): List<Double> {
        val values = getAttribute("points").trim()
        val numbers = values.split(SEPARATORS).map(String::toDouble)
        require(numbers.size % 2 == 0) { "The points of <$tagName> must come in pairs." }
        return numbers
    }

    private fun Element.isFilled(): Boolean {
        val fill = getAttribute("fill")
        return fill.isNotEmpty() && fill != NO_FILL
    }
}
