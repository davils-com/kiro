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

package com.davils.kiro.ui.motion.shader.silk

internal const val SILK_SOURCE: String = """
uniform float2 size;
uniform float time;
uniform float4 ground;
uniform float4 tint;
uniform float4 shadow;
uniform float4 sheen;
uniform float intensity;
uniform float scale;
uniform float warp;
uniform float relief;
uniform float shine;
uniform float angle;
uniform float shift;
uniform float3 pointer;
uniform float pointerLight;
uniform float dither;

float noiseOf(float2 p) {
    float3 q = fract(float3(p.xyx) * 0.1031);
    q += dot(q, q.yzx + 33.33);
    return fract((q.x + q.y) * q.z);
}

float heightOf(float2 q, float t) {
    float bend = warp * (sin(0.23 * q.y + t + shift) + 0.5 * sin(0.11 * q.y - t + 2.0 * shift));
    float a = sin(q.x + bend);
    float b = sin(0.47 * q.x - 0.12 * q.y + 0.7 * bend - t + 3.0 * shift);
    return 0.65 * a + 0.35 * b;
}

half4 main(float2 p) {
    float t = time * 6.2831853;
    float2 r = float2(cos(angle), sin(angle));
    float2 q = float2(dot(p, r), dot(p, float2(-r.y, r.x))) / max(scale, 1.0);
    float e = 0.01;
    float h = heightOf(q, t);
    float dx = (heightOf(q + float2(e, 0.0), t) - h) / e;
    float dy = (heightOf(q + float2(0.0, e), t) - h) / e;
    float2 slope = float2(dx * r.x - dy * r.y, dx * r.y + dy * r.x) * relief;
    float3 n = normalize(float3(-slope, 1.0));
    float2 lean = (pointer.xy / size - 0.5) * pointer.z * pointerLight;
    float3 light = normalize(float3(-0.45 + lean.x, -0.6 + lean.y, 0.7));
    float diffuse = clamp(dot(n, light), 0.0, 1.0);
    float gloss = pow(clamp(dot(n, normalize(light + float3(0.0, 0.0, 1.0))), 0.0, 1.0), shine);
    float3 base = ground.rgb * ground.rgb;
    float3 body = mix(base, tint.rgb * tint.rgb, intensity * tint.a * (0.55 + 0.45 * h));
    body = mix(body, shadow.rgb * shadow.rgb, shadow.a * (1.0 - diffuse));
    body = mix(body, sheen.rgb * sheen.rgb, sheen.a * gloss);
    float3 color = sqrt(max(body, 0.0)) + (noiseOf(p) - 0.5) * dither / 255.0;
    return half4(half3(color * ground.a), half(ground.a));
}
"""
