package com.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.entity.UserDetail;

//import java.net.URI;
import java.util.*;

@Service
public class PostService {

    private RestTemplate restTemplate;
	//private String url= "https://jsonplaceholder.typicode.com/posts";

  //  @Value("${Post_service_url}")
  //  @Value("${post_service_url}")
   // private String url;

    @Autowired
    public PostService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

  
  //  public String getUrl() {
  //      return url;
   // }

   
   // public void setUrl(String url) {
      //  this.url = url;
  //  }

   
    public List<UserDetail> getAll(String sortKey) {
    	UserDetail[] arr = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", UserDetail[].class);
        List<UserDetail> posts = new ArrayList<>(Arrays.asList(arr));

        if (sortKey != null) {
            switch (sortKey) {
                case "id":
                    posts.sort(Comparator.comparing(UserDetail::getId));
                    break;
                case "userId":
                    posts.sort(Comparator.comparing(UserDetail::getUserId));
                    break;
                case "title":
                    posts.sort(Comparator.comparing(UserDetail::getTitle));
                    break;
            }
        }

        return posts;
    }

  
    @Cacheable(value = "posts", key = "#id")
    public UserDetail get(String id) {
        String url="https://jsonplaceholder.typicode.com/posts";
		return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts" + "/" + id, UserDetail.class);
    }

    
    @CachePut(value = "posts", key = "#result.id")
    public UserDetail update(String id, UserDetail post) {
        String url="https://jsonplaceholder.typicode.com/posts";
		return restTemplate.patchForObject(url + "/" + id, post, UserDetail.class);
    }

    
    @CachePut(value = "posts", key = "#result.id")
    public UserDetail add(UserDetail post) {
        return restTemplate.postForObject("https://jsonplaceholder.typicode.com/posts", post, UserDetail.class);
    }

    
    @CacheEvict(value = "posts", key = "#id")
    public void delete(String id) {
        restTemplate.delete("https://jsonplaceholder.typicode.com/posts" + "/" + id);
    }

   
    public HashMap<String, Long> meta() {
        HashMap<String, Long> counters = new HashMap<>();
        HashMap<Integer, Integer> countsByUser = new HashMap<>();

        List<UserDetail> posts = this.getAll(null);

        for (UserDetail post : posts) {
            Integer newCountByUser = countsByUser.get(post.getUserId());
            if (newCountByUser == null) {
                newCountByUser = 1;
            } else {
                newCountByUser += 1;
            }
            countsByUser.put(post.getUserId(), newCountByUser);
        }

        Long numPosts = (long) posts.size();
        Long numUsers = (long) countsByUser.size();

        counters.put("posts", numPosts);
        counters.put("users", numUsers);

        return counters;
    }

}
