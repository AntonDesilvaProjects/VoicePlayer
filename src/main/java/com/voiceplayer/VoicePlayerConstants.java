package com.voiceplayer;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class VoicePlayerConstants {
    public static final Map<String, Double> SPEED_PHRASE_TO_NUMERICAL_VALUE = ImmutableMap.of(
            "quarter", 0.25,
            "half", 0.5,
            "double", 2.0,
            "triple", 3.0,
            "twice", 2.0
    );
}
