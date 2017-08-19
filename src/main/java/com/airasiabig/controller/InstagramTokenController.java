package com.airasiabig.controller;

import com.airasiabig.service.InstagramTokenService;
import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.tags.TagInfoData;
import org.jinstagram.entity.tags.TagSearchFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Prajesh Ananthan
 *         Created on 11/8/2017.
 */
@Controller
@RequestMapping("/handleInstagramToken")
public class InstagramTokenController {

  private static final String INDEX_PAGE = "index";
  private static final String SEARCH_PAGE = "search";
  private static final String SEARCH_PAGE_REDIRECT = "redirect:/" + SEARCH_PAGE + "/";
  private static final String CODE = "code";
  private static final String TAG = "tag";
  private static final Logger LOG = LoggerFactory.getLogger(InstagramTokenController.class);
  private static final String MEDIA_LIST = "mediaList";
  private static final String MEDIA_COUNT = "mediaCount";
  private static final String INSTAGRAM = "instagram";
  private static final String AUTHORIZATION_URL = "authorizationUrl";

  private InstagramService instagramService;
  private InstagramTokenService tokenService;
  private Instagram instagram;

  @Autowired
  public void setTokenService(InstagramTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Autowired
  public void setInstagramService(InstagramService instagramService) {
    this.instagramService = instagramService;
  }

  @GetMapping
  public String home(Model model) {
    model.addAttribute(AUTHORIZATION_URL, instagramService.getAuthorizationUrl());
    return INDEX_PAGE;
  }

  @GetMapping(params = {CODE})
  public String load(@RequestParam String code, Model model) throws InstagramException {
    Verifier verifier = new Verifier(code);
    Token accessToken = instagramService.getAccessToken(verifier);
    instagram = new Instagram(accessToken);
    model.addAttribute(INSTAGRAM);
    return SEARCH_PAGE;
  }

  @PostMapping
  public String findMediaListByTag(HttpServletRequest request, Model model) throws InstagramException {
    List<MediaFeedData> mediaList = tokenService.findMediaListByQuery(instagram, request.getParameter(TAG));
    if (mediaList != null) {
      model.addAttribute(MEDIA_LIST, mediaList);
      model.addAttribute(MEDIA_COUNT, mediaList.size());
    }

    return SEARCH_PAGE;
  }

  @ResponseBody
  public List<TagInfoData> listTags(HttpServletRequest request) throws InstagramException {
    final String query = request.getParameter(TAG);
    TagSearchFeed searchFeed = instagram.searchTags(query);
    List<TagInfoData> tags = searchFeed.getTagList();
    return tags;
  }
}
