package org.devmaster.player;

import android.support.annotation.NonNull;

class PlayerPresenter implements Player.Presenter {

    private final Player.View mView;

    PlayerPresenter(@NonNull Player.View view) {
        mView = view;
    }

    @Override
    public void reset() {
        mView.setVideoUrl("about:blank");
    }

    @Override
    public void loadVideo(@NonNull String videoId, @NonNull @VideoSource String source) {
        reset();

        String playerUrl = null;

        if (Source.VIMEO.equals(source)) {
            playerUrl = "https://player.vimeo.com/video/" + videoId;
        } else if (Source.YOUTUBE.equals(source)) {
            playerUrl = "https://www.youtube.com/embed/" + videoId + "?feature=oembed";
        }


        if (playerUrl != null) {


            String html = "<!DOCTYPE html><html>" +
                    "<head>" +
                    "<meta name=\"viewport\" content=\"width=device-height, user-scalable=yes\">" +
                    "<meta-data android:name=\"android.webkit.WebView.MetricsOptOut\"\n" +
                    "            android:value=\"true\" />" +
                    "<style>" +
                    "body {margin: 0;}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<iframe width=\"480\" height=\"356\"" +
                    "   src=\"" + playerUrl  + "\"" +
                    "   frameborder=\"0\" " +
                    "   allowfullscreen></iframe>" +
                    "</body></html>";

            mView.setVideoHtml(html);
        }
    }
}
