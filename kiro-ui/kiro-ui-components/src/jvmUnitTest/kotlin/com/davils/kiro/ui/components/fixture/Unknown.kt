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

package com.davils.kiro.ui.components.fixture

import com.davils.kiro.ui.components.alert.AlertVariant
import com.davils.kiro.ui.components.alertdialog.AlertDialogSize
import com.davils.kiro.ui.components.attachment.AttachmentSize
import com.davils.kiro.ui.components.avatar.AvatarSize
import com.davils.kiro.ui.components.badge.BadgeVariant
import com.davils.kiro.ui.components.bubble.BubbleVariant
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.card.CardSize
import com.davils.kiro.ui.components.codeblock.CodeBlockSize
import com.davils.kiro.ui.components.codeblock.CodeBlockVariant
import com.davils.kiro.ui.components.inputgroup.InputGroupButtonSize
import com.davils.kiro.ui.components.item.ItemSize
import com.davils.kiro.ui.components.item.ItemVariant
import com.davils.kiro.ui.components.nativeselect.NativeSelectSize
import com.davils.kiro.ui.components.select.SelectSize
import com.davils.kiro.ui.components.switch.SwitchSize
import com.davils.kiro.ui.components.tabs.TabsVariant
import com.davils.kiro.ui.components.toggle.ToggleSize
import com.davils.kiro.ui.components.toggle.ToggleVariant

internal object Unknown :
    ButtonVariant,
    ButtonSize,
    BadgeVariant,
    CardSize,
    AlertVariant,
    AvatarSize,
    ItemVariant,
    ItemSize,
    InputGroupButtonSize,
    SwitchSize,
    ToggleVariant,
    ToggleSize,
    NativeSelectSize,
    TabsVariant,
    SelectSize,
    AlertDialogSize,
    CodeBlockVariant,
    CodeBlockSize,
    AttachmentSize,
    BubbleVariant
