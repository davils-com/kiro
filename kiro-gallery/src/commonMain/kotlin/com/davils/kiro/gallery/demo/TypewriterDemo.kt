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

package com.davils.kiro.gallery.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.motion.art.gradient.GradientArt
import com.davils.kiro.ui.motion.host.MotionCanvas
import com.davils.kiro.ui.motion.scene.scene
import com.davils.kiro.ui.motion.typewriter.TypewriterText
import com.davils.kiro.ui.motion.typewriter.typewriter
import com.davils.kiro.ui.motion.typewriter.typewriterCycle
import com.davils.kiro.ui.motion.typewriter.typewriterStyle

@Composable
internal fun TypewriterDemo() {
    val headline = remember {
        typewriterCycle(phrases = listOf("Calm interfaces.", "Gentle motion.", "Every detail themed."))
    }
    val greeting = remember {
        typewriter {
            type("Hello, world")
            pause()
            delete(count = 5)
            type("Kiro.")
        }
    }
    val style = typewriterStyle()
    val large = style.copy(textStyle = style.textStyle.copy(fontSize = 30.sp, fontWeight = FontWeight.SemiBold))
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        MotionCanvas(scene = scene { layer(GradientArt()) }, modifier = Modifier.artFrame(theme, height = 200.dp)) {
            TypewriterText(script = headline, modifier = Modifier.align(Alignment.Center), style = large)
        }
        TypewriterText(script = greeting)
    }
}
