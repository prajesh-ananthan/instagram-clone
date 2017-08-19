package com.airasiabig.service;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Prajesh Ananthan
 *         Created on 11/8/2017.
 */
@Service
public interface InstagramTokenService {
  List<MediaFeedData> findMediaListByQuery(Instagram instagram, String query);
}
