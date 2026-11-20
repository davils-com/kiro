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

package com.davils.kiro.ui.components.command.search

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private const val ROUTING: String = "Routing"
private const val OSPF: String = "ospf"

class SearchRankingTest : FunSpec({
    context("ranking") {
        test("orders exact before prefix before word start before inside") {
            val items = listOf("show running-config", "running", "run", "prune", "Run history")

            ranked(items = items, query = "run") { item -> listOf(item) } shouldBe
                listOf("run", "running", "Run history", "show running-config", "prune")
        }

        test("keeps the original order for an empty query") {
            val items = listOf("b", "a")

            ranked(items = items, query = "  ") { item -> listOf(item) } shouldBe items
        }

        test("matches keywords and drops what does not match") {
            val items = listOf("Interfaces", ROUTING)

            ranked(
                items = items,
                query = OSPF,
            ) { item -> listOf(item, OSPF.takeIf { item == ROUTING }.orEmpty()) } shouldBe
                listOf(ROUTING)
            rankOf(terms = listOf("vlan"), needle = "bgp").shouldBeNull()
        }
    }
})
