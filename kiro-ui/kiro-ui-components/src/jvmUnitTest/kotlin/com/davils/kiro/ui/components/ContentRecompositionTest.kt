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

package com.davils.kiro.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.attachment.Attachment
import com.davils.kiro.ui.components.attachment.AttachmentContent
import com.davils.kiro.ui.components.attachment.AttachmentDescription
import com.davils.kiro.ui.components.attachment.AttachmentGroup
import com.davils.kiro.ui.components.attachment.AttachmentMedia
import com.davils.kiro.ui.components.attachment.AttachmentMediaVariant
import com.davils.kiro.ui.components.attachment.AttachmentSize
import com.davils.kiro.ui.components.attachment.AttachmentState
import com.davils.kiro.ui.components.attachment.AttachmentTitle
import com.davils.kiro.ui.components.attachment.attachmentStyle
import com.davils.kiro.ui.components.bubble.Bubble
import com.davils.kiro.ui.components.bubble.BubbleContent
import com.davils.kiro.ui.components.bubble.BubbleGroup
import com.davils.kiro.ui.components.bubble.BubbleReactions
import com.davils.kiro.ui.components.bubble.BubbleReactionsSide
import com.davils.kiro.ui.components.bubble.BubbleVariant
import com.davils.kiro.ui.components.bubble.bubbleStyle
import com.davils.kiro.ui.components.chart.BarChart
import com.davils.kiro.ui.components.chart.ChartConfig
import com.davils.kiro.ui.components.chart.ChartContainer
import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.ChartPoint
import com.davils.kiro.ui.components.chart.ChartSeries
import com.davils.kiro.ui.components.chart.PieChart
import com.davils.kiro.ui.components.chart.chartStyle
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.message.Message
import com.davils.kiro.ui.components.message.MessageAlign
import com.davils.kiro.ui.components.message.MessageFooter
import com.davils.kiro.ui.components.message.MessageGroup
import com.davils.kiro.ui.components.message.MessageHeader
import com.davils.kiro.ui.components.message.messageStyle
import com.davils.kiro.ui.components.messagescroller.MessageScroller
import com.davils.kiro.ui.components.messagescroller.messageScrollerStyle
import com.davils.kiro.ui.components.messagescroller.rememberMessageScrollerState
import com.davils.kiro.ui.components.questionnaire.Question
import com.davils.kiro.ui.components.questionnaire.QuestionKey
import com.davils.kiro.ui.components.questionnaire.QuestionKind
import com.davils.kiro.ui.components.questionnaire.Questionnaire
import com.davils.kiro.ui.components.questionnaire.QuestionnaireChoice
import com.davils.kiro.ui.components.questionnaire.QuestionnaireChoices
import com.davils.kiro.ui.components.questionnaire.QuestionnaireItem
import com.davils.kiro.ui.components.questionnaire.QuestionnaireProgress
import com.davils.kiro.ui.components.questionnaire.QuestionnaireTitle
import com.davils.kiro.ui.components.questionnaire.questionnaireStyle
import com.davils.kiro.ui.components.questionnaire.rememberQuestionnaireState
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ROUNDS: Int = 3
private const val FIXED: String = "fixed"
private val visits = ChartKey("visits")

@Composable
private fun Conversation(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    val align = MessageAlign.End.takeIf { isOdd } ?: MessageAlign.Start
    MessageScroller(
        modifier = Modifier.height(240.dp),
        state = rememberMessageScrollerState(),
        isButtonShown = isOdd,
        style = messageScrollerStyle(),
    ) {
        MessageGroup(modifier = Modifier, style = messageStyle()) {
            Message(modifier = Modifier, align = align, avatar = { Text(label) }, style = messageStyle()) {
                MessageHeader(modifier = Modifier) { Text(label) }
                BubbleGroup(modifier = Modifier, style = bubbleStyle(BubbleVariant.Muted)) {
                    Bubble(
                        modifier = Modifier,
                        variant = BubbleVariant.Muted,
                        align = align,
                        style = bubbleStyle(BubbleVariant.Muted),
                    ) {
                        BubbleContent(modifier = Modifier, onClick = {}) { Text(label) }
                        BubbleReactions(
                            modifier = Modifier,
                            side = BubbleReactionsSide.Top,
                            align = align,
                        ) { Text(label) }
                    }
                }
                MessageFooter(modifier = Modifier) { Text(label) }
            }
        }
        AttachmentGroup(modifier = Modifier, style = attachmentStyle(AttachmentSize.Xs)) {
            Attachment(
                modifier = Modifier,
                state = AttachmentState.Error.takeIf { isOdd } ?: AttachmentState.Done,
                size = AttachmentSize.Xs,
                orientation = Orientation.Horizontal,
                onClick = {},
                style = attachmentStyle(AttachmentSize.Xs),
            ) {
                AttachmentMedia(modifier = Modifier, variant = AttachmentMediaVariant.Image) { Text(label) }
                AttachmentContent(modifier = Modifier) {
                    AttachmentTitle(text = label, modifier = Modifier)
                    AttachmentDescription(text = label, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun Insights(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    val question = remember { Question(key = QuestionKey(FIXED), kind = QuestionKind.Single) }
    Questionnaire(
        state = rememberQuestionnaireState(question),
        onSubmit = {},
        modifier = Modifier,
        style = questionnaireStyle(),
    ) {
        QuestionnaireProgress(modifier = Modifier)
        QuestionnaireItem(key = question.key, modifier = Modifier) {
            QuestionnaireTitle(text = label, modifier = Modifier)
            QuestionnaireChoices(modifier = Modifier) {
                QuestionnaireChoice(
                    value = label,
                    modifier = Modifier,
                    enabled = !isOdd,
                    shortcut = 'a',
                ) { Text(label) }
            }
        }
    }
    val config = ChartConfig(listOf(ChartSeries(key = visits, label = label)))
    val data = listOf(ChartPoint(label = label, values = mapOf(visits to tick + 1.0)))
    Box(modifier = Modifier.size(width = 320.dp, height = 180.dp)) {
        ChartContainer(config = config, modifier = Modifier, style = chartStyle(), aspectRatio = null) {
            Column {
                BarChart(
                    data = data,
                    modifier = Modifier.weight(1f),
                    orientation = Orientation.Vertical,
                    isStacked = isOdd,
                ) {
                    tooltip()
                    bar(visits)
                }
                PieChart(
                    data = data,
                    key = visits,
                    modifier = Modifier.weight(1f),
                    innerRadius = 8.dp,
                    paddingAngle = 0f,
                    isLabelShown = isOdd,
                ) {
                    legend()
                }
            }
        }
    }
}

@OptIn(ExperimentalTestApi::class)
class ContentRecompositionTest : FunSpec({
    context("content components") {
        test("take every argument explicitly and recompose with changed and unchanged arguments") {
            var round by mutableIntStateOf(0)
            var rendered = -1

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Column {
                            val isOdd = round % 2 == 1
                            val label = "Round $round"
                            Conversation(label = label, isOdd = isOdd, tick = round)
                            Conversation(label = FIXED, isOdd = false, tick = round)
                            Insights(label = label, isOdd = isOdd, tick = round)
                            Insights(label = FIXED, isOdd = false, tick = round)
                            rendered = round
                        }
                    }
                }
                repeat(ROUNDS) {
                    round++
                    waitForIdle()
                }
            }

            rendered shouldBe ROUNDS
        }
    }
})
