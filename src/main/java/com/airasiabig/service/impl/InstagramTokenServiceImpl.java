package com.airasiabig.service.impl;

import com.airasiabig.service.InstagramTokenService;
import org.jinstagram.Instagram;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Prajesh Ananthan
 *         Created on 11/8/2017.
 */
@Service
public class InstagramTokenServiceImpl implements InstagramTokenService {

  private static final Logger log = LoggerFactory.getLogger(InstagramTokenServiceImpl.class);

  @Value("${max.page.size}")
  private int pageSize;

  @Override
  public List<MediaFeedData> findMediaListByQuery(Instagram instagram, String query) {
    List<MediaFeedData> mediaList = null;
    int counter = 0;

    if (!StringUtils.isEmpty(query)) {
      try {
        log.debug("Searching post with tag: #" + query);
        TagMediaFeed mediaFeed = instagram.getRecentMediaTags(query);
        mediaList = mediaFeed.getData();
        MediaFeed recentMediaNextPage = instagram.getRecentMediaNextPage(mediaFeed.getPagination());
        while (recentMediaNextPage.getPagination() != null && counter < pageSize) {
          mediaList.addAll(recentMediaNextPage.getData());
          recentMediaNextPage = instagram.getRecentMediaNextPage(recentMediaNextPage.getPagination());
          counter++;
        }
      } catch (Exception ex) {
        // TODO: Render error message on homepage
        ex.getMessage();
      }
    }
    return mediaList;
  }
}
