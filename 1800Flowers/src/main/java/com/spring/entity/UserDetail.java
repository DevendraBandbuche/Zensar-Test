	package com.spring.entity;
	
	
	
	import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

	import java.io.Serializable;
	import java.util.Objects;


	@SuppressWarnings("serial")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class UserDetail implements Serializable {

	    private Integer userId;
	    private Integer id;
	    private String title;
	    private String body;

	  
	    public UserDetail() {
	    }

	    
	  
	    public Integer getUserId() {
	        return userId;
	    }

	   
	    public void setUserId(Integer userId) {
	        this.userId = userId;
	    }

	   
	    public Integer getId() {
	        return id;
	    }

	   
	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	  
	    public void setTitle(String title) {
	        this.title = title;
	    }

	
	    public String getBody() {
	        return body;
	    }

	   
	    public void setBody(String body) {
	        this.body = body;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        UserDetail post = (UserDetail) o;
	        return Objects.equals(userId, post.userId) &&
	                Objects.equals(id, post.id) &&
	                Objects.equals(title, post.title) &&
	                Objects.equals(body, post.body);
	    }

	    @Override
	    public int hashCode() {

	        return Objects.hash(userId, id, title, body);
	    }

	    @Override
	    public String toString() {
	        return "Post{" +
	                "userId=" + userId +
	                ", id=" + id +
	                ", title='" + title + '\'' +
	                ", body='" + body + '\'' +
	                '}';
	    }
	}

	
	
	
	
	

