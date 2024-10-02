package com.example.cookbook

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

// Этот класс требуется Glide для оптимизации его работы
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    // Оставляем пустым, если не требуется дополнительной конфигурации
}
