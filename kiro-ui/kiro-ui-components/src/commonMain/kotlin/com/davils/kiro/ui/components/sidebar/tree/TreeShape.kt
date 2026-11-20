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

package com.davils.kiro.ui.components.sidebar.tree

internal fun <T> parentsOf(nodes: List<TreeNode<T>>, parent: T? = null): Map<T, T> = nodes.fold(
    emptyMap(),
) { parents, node ->
    val own = parent?.let { above -> mapOf(node.value to above) }.orEmpty()
    parents + own + childParentsOf(node)
}

internal fun <T> branchesOf(nodes: List<TreeNode<T>>): Set<T> {
    val branches = nodes.mapNotNull { node -> node as? TreeNode.Branch<T> }
    return branches.fold(emptySet()) { found, branch -> found + branch.value + branchesOf(branch.children) }
}

internal fun <T> visibleOf(nodes: List<TreeNode<T>>, expanded: Set<T>): List<TreeNode<T>> = nodes.flatMap { node ->
    listOf(node) + visibleOf(nodes = openChildrenOf(node = node, expanded = expanded), expanded = expanded)
}

private fun <T> childParentsOf(node: TreeNode<T>): Map<T, T> {
    if (node !is TreeNode.Branch<T>) return emptyMap()
    return parentsOf(nodes = node.children, parent = node.value)
}

private fun <T> openChildrenOf(node: TreeNode<T>, expanded: Set<T>): List<TreeNode<T>> {
    if (node !is TreeNode.Branch<T> || node.value !in expanded) return emptyList()
    return node.children
}
