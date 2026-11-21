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

package com.davils.kiro.ui.motion.art

import kotlin.jvm.JvmInline

private const val GOLDEN: Int = -0x61c88647
private const val MIX: Int = 0x45d9f3b
private const val SHIFT: Int = 16

/**
 * The seed of a generative picture: the same seed draws the same picture on every platform and in every run.
 *
 * @property value the number the picture grows from.
 * @since 1.0.0
 */
@JvmInline
public value class ArtSeed(public val value: Int) {
    /**
     * Returns another seed that follows from this one and a salt, such as the index of a layer, so that every layer
     * of a scene gets its own seed that does not depend on the layers before it.
     *
     * @param salt the number that tells the derived seeds apart.
     * @return the derived seed.
     * @since 1.0.0
     */
    public fun derive(salt: Int): ArtSeed {
        var mixed = value xor salt + GOLDEN + (value shl 6) + (value ushr 2)
        mixed = (mixed ushr SHIFT xor mixed) * MIX
        return ArtSeed(mixed ushr SHIFT xor mixed)
    }
}
