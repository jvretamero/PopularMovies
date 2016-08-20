package br.com.joaoretamero.popularmovies.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.json.VideoJson;
import br.com.joaoretamero.popularmovies.domain.local.Video;

public class VideoMapper {

    public List<Video> mapJsonListToLocalList(List<VideoJson> videoJsonList) {
        List<Video> videoList = new ArrayList<Video>(videoJsonList.size());

        for (VideoJson videoJson : videoJsonList) {
            videoList.add(mapJsonToLocal(videoJson));
        }

        return videoList;
    }

    public Video mapJsonToLocal(VideoJson videoJson) {
        Video video = new Video();
        video.name = videoJson.name;
        video.youtubeId = videoJson.key;

        return video;
    }
}
