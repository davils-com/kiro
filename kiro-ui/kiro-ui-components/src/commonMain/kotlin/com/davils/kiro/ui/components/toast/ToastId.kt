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

package com.davils.kiro.ui.components.toast

import kotlin.jvm.JvmInline

/**
 * Identifies a toast of a [ToasterState], for [ToasterState.update] and [ToasterState.dismiss].
 *
 * @property value the number of the toast, unique within its toaster.
 * @since 1.0.0
 */
@JvmInline
public value class ToastId(public val value: Long)
