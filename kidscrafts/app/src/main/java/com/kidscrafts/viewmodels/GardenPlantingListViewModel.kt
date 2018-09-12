/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kidscrafts.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.kidscrafts.data.GardenPlantingRepository
import com.kidscrafts.data.PlantAndGardenPlantings


class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {

    val gardenPlantings = gardenPlantingRepository.getGardenPlantings()

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
            Transformations.map(gardenPlantingRepository.getPlantAndGardenPlantings()) {
                it.filter { it.gardenPlantings.isNotEmpty() }
            }


    fun styleTextView(textView: TextView ,text: String, keyword: String) {

        val spanText = SpannableStringBuilder()
        spanText.append(text)
        spanText.append(" ")
        spanText.append(keyword)
        spanText.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor    // you can use custom color
                textPaint.isUnderlineText = false    // this remove the underline
            }
        }, spanText.length - keyword.length, spanText.length, 0)

        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.setText(spanText, TextView.BufferType.SPANNABLE)

    }

}