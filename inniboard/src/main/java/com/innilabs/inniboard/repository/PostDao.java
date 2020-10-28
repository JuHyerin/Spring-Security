package com.innilabs.inniboard.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.innilabs.inniboard.dto.Post;

import org.springframework.stereotype.Repository;


@Repository
public class PostDao {
    private static Map<Integer, Post> postMap = new HashMap<Integer, Post>();
    public PostDao(){
        Post p1 = new Post();
        p1.setPostId(1);
        p1.setTitle("title1");
        p1.setContents("contents1");
        p1.setWriter("writer1");
        postMap.put(p1.getPostId(), p1);

        Post p2 = new Post();
        p2.setPostId(2);
        p2.setTitle("title2");
        p2.setContents("contents2");
        p2.setWriter("writer2");
        postMap.put(p2.getPostId(), p2);

        Post p3 = new Post();
        p3.setPostId(3);
        p3.setTitle("title3");
        p3.setContents("contents3");
        p3.setWriter("writer3");
        postMap.put(p3.getPostId(), p3);
    }
    
    public Map<Integer, Post> getAllPosts(){
        return postMap;
    }

	public Post createPost(Post newPost) {
        Post post = getPostById(newPost.getPostId());
        if (post!=null){
            return null;
        }
        postMap.put(newPost.getPostId(), newPost);
        return newPost;
    }

	public Post getPostById(int id) {
        Post resultPost = postMap.get(id);
        
		return resultPost;
	}

	public Post getPostByTitle(String title) {
        Post resultPost = null;
        for (int key : postMap.keySet()) {
            if(postMap.get(key).getTitle().equals(title)){
                resultPost = postMap.get(key);
                break;
            }        
        }
		return resultPost;
	}

	public Post updatePostAtId(int id, Post post) {
        postMap.put(id, post);//controller에서 존재하는 id만 걸러서 보냄
        return post;
        
	}

	public Post deletePostById(int id) {
		return postMap.remove(id); //hashmap.remove:삭제된 value 반환, key에 해당하는 value 없으면 null 반환
	}

}