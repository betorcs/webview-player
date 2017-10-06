package org.devmaster.player;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({Source.YOUTUBE, Source.VIMEO})
public @interface VideoSource {}
