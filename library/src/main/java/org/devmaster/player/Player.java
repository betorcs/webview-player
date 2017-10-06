package org.devmaster.player;

import android.support.annotation.NonNull;

interface Player {

    interface Presenter {
        void reset();
        void loadVideo(@NonNull String videoId, @VideoSource @NonNull String source);
    }

    interface View {
        void setVideoHtml(@NonNull String html);
        void setVideoUrl(@NonNull String url);
    }

}
