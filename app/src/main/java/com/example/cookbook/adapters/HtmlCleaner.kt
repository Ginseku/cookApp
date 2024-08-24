package com.example.cookbook.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.jsoup.Jsoup
import java.lang.reflect.Type

class HtmlCleaner: JsonDeserializer<String> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        val html = json?.asString
        return Jsoup.parse(html).text()
    }

}