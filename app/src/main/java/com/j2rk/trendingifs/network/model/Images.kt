package com.j2rk.trendingifs.network.model

data class Images(
    val fixed_width: FixedWidth,
    val fixed_width_downsampled: FixedWidthDownsampled,
    val fixed_width_small: FixedWidthSmall,
    val fixed_width_small_still: FixedWidthSmallStill,
    val fixed_width_still: FixedWidthStill,
)