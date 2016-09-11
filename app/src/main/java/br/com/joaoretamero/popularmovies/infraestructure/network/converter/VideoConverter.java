package br.com.joaoretamero.popularmovies.infraestructure.network.converter;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.network.model.VideoJson;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Video;

public class VideoConverter {

    public List<Video> convertListToStorageModel(List<VideoJson> videoJsonList) {
        List<Video> videoList = new ArrayList<Video>(videoJsonList.size());

        for (VideoJson videoJson : videoJsonList) {
            videoList.add(convertToStorageModel(videoJson));
        }

        return videoList;
    }

    public Video convertToStorageModel(VideoJson videoJson) {
        Video video = new Video();
        video.name = videoJson.name;
        video.youtubeId = videoJson.key;

        return video;
    }
}
