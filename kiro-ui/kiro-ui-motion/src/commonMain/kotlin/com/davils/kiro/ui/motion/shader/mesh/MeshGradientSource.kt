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

package com.davils.kiro.ui.motion.shader.mesh

internal const val MESH_GRADIENT_SOURCE: String = """
uniform float4 ground;
uniform float count;
uniform float softness;
uniform float intensity;
uniform float dither;
uniform float3 anchors[6];
uniform float4 tints[6];

float noiseOf(float2 p) {
    float3 q = fract(float3(p.xyx) * 0.1031);
    q += dot(q, q.yzx + 33.33);
    return fract((q.x + q.y) * q.z);
}

half4 main(float2 p) {
    float3 color = ground.rgb * ground.rgb;
    for (int i = 0; i < 6; i++) {
        if (float(i) < count) {
            float2 d = (p - anchors[i].xy) / anchors[i].z;
            float w = exp(-softness * dot(d, d)) * tints[i].a * intensity;
            color = mix(color, tints[i].rgb * tints[i].rgb, w);
        }
    }
    float3 shaded = sqrt(color) + (noiseOf(p) - 0.5) * dither / 255.0;
    return half4(half3(shaded * ground.a), half(ground.a));
}
"""
