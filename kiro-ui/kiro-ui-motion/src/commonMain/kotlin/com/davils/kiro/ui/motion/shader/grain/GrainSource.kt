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

package com.davils.kiro.ui.motion.shader.grain

internal const val GRAIN_SOURCE: String = """
uniform float cell;
uniform float2 offset;
uniform float amount;
uniform float balance;
uniform float4 shade;
uniform float4 tone;

float noiseOf(float2 p) {
    float3 q = fract(float3(p.xyx) * 0.1031);
    q += dot(q, q.yzx + 33.33);
    return fract((q.x + q.y) * q.z);
}

half4 main(float2 p) {
    float2 c = floor(p / max(cell, 1.0)) + offset;
    float n = noiseOf(c);
    float strength = noiseOf(c + float2(57.0, 113.0));
    float4 speck = n < balance ? shade : tone;
    float a = speck.a * amount * strength * strength;
    return half4(half3(speck.rgb * a), half(a));
}
"""
