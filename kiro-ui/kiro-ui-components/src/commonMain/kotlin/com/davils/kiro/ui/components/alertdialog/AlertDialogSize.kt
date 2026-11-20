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

package com.davils.kiro.ui.components.alertdialog

/**
 * The size of an [AlertDialog], which limits its width and decides the layout of its header, texts and buttons.
 *
 * @since 1.0.0
 */
public interface AlertDialogSize {
    /**
     * The default size: a wider dialog whose media sits next to the texts, which are aligned to the start.
     *
     * @since 1.0.0
     */
    public data object Default : AlertDialogSize

    /**
     * The small size: a narrower dialog whose media and texts are centered and whose buttons share the width of
     * the footer.
     *
     * @since 1.0.0
     */
    public data object Sm : AlertDialogSize
}
