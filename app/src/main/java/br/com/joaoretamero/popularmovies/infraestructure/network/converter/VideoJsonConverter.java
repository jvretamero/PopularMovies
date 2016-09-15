package br.com.joaoretamero.popularmovies.infraestructure.network.converter;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Video;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.VideoJson;

public class VideoJsonConverter {

    public List<Video> convertListToDomainModel(List<VideoJson> videoJsonList) {
        List<Video> videoList = new ArrayList<Video>(videoJsonList.size());

        for (VideoJson videoJson : videoJsonList) {
            videoList.add(convertToDomainModel(videoJson));
        }

        return videoList;
    }

    public Video convertToDomainModel(VideoJson videoJson) {
        return new Video(videoJson.name, videoJson.key);
    }
}
