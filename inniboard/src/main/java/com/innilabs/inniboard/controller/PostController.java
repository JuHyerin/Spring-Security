package com.innilabs.inniboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innilabs.inniboard.dto.Post;
import com.innilabs.inniboard.repository.PostDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
@DependsOn("testapi")//bean 생성 순서 조절
@RestController
//@Api(value="/posts", description = "게시물 자원을 이용한 api") //Controller 단위로 api 메타데이터 명시
@RequestMapping("/api/board")
public class PostController {
    ObjectMapper om;
    @PostConstruct
    void init(){
        //PostController가 생성된 후 호출되는 function -> Component를 초기화해줄 수 있음
        //ObjectMapper(ex:Json<->Object)
        om = new ObjectMapper(); //한번만 만들고 싶으니까!
    }

    @Autowired
    PostDao postDao;
     
    @ApiOperation(value = "Get All Posts", notes = "모든 게시물 조회") //하나의 REST API 요청 URL에 맵핑됨
    @GetMapping(value="/posts")
    public ResponseEntity<Map<Integer,Post>> getAllPosts(){
        Map<Integer,Post> postList = postDao.getAllPosts();
        if(postList.isEmpty()){
            return new ResponseEntity<Map<Integer,Post>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<Integer,Post>>(postList,HttpStatus.OK);
    }

    //@ApiOperation(value = "Get Post By PostId in path", notes = "path에 게시물id로 게시물 조회")
    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<Post> getPostByIdInPath(@ApiParam(value = "조회할 게시물 아이디", defaultValue = "1")@PathVariable(value = "id", required = true) int id){
        Post post = postDao.getPostById(id);
        if(post == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Post By PostId in query", notes = "query에 게시물id로 게시물 조회")
    @ApiImplicitParams({
        @ApiImplicitParam(name="id", value = "조회할 게시물 아이디", dataType = "int", defaultValue = "1")
    })
    @PostMapping(value = "/posts/query")
    public ResponseEntity<Post> getPostByIdInQuery(int id){
        Post post = postDao.getPostById(id);
        if(post == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Post by postId or Title in query", notes = "query에 게시물 아이디나 제목으로 게시물 조회")
    @ApiImplicitParams({
        @ApiImplicitParam(name="option", value="검색조건", required = true, defaultValue = "id"),
        @ApiImplicitParam(name="word", value="검색단어", dataType = "string", defaultValue = "1")
    })
    @GetMapping(value = "/posts/search")
    public ResponseEntity<Post> getPostByIdInQuery(String option, String word){
        Post post = null;
       if(option.equals("id")){
            post = postDao.getPostById(Integer.parseInt(word));
            
        }else if(option.equals("title")){
            post = postDao.getPostByTitle(word);
        }

        if(post == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

   
    @ApiOperation(value = "Insert New Post", notes = "게시물 등록")
    @ApiResponses({
        @ApiResponse(code = 200, message = "(C)Createed !!"),
        @ApiResponse(code = 201, message = "(C)Createed !!"),
        @ApiResponse(code = 500, message = "(C)Internal Server Error !!"),
        @ApiResponse(code = 405, message = "(C)Alreadt Exist !!")
    })
    @PostMapping(value = "/posts")
    public ResponseEntity<Post> createPost(@ApiParam(value = "등록할 게시물") @RequestBody Post post){
        Post createdPost = postDao.createPost(post);
        if(createdPost==null){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);//postid중복
        }
        return new ResponseEntity<Post>(createdPost, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Post", notes = "게시물 수정")
    @PutMapping(value = "/posts")
    public ResponseEntity<Post> updatePost(@ApiParam(value = "수정할 게시물 아이디", required = true, defaultValue = "1") @RequestParam int id, 
                                        @ApiParam(value = "수정할 내용") @RequestBody Post post){  
        if(postDao.getPostById(id)==null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//postid없음

        }                
        Post updatedPost = postDao.updatePostAtId(id, post);              
        return new ResponseEntity<Post>(updatedPost, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Post by PostID", notes = "게시물 삭제")
    @DeleteMapping(value = "/posts/{id}")
    public ResponseEntity<Post> deletePostById(@ApiParam(value="삭제할 게시물 아이디", defaultValue = "1")@PathVariable int id){
    
        Post deletedPost = postDao.deletePostById(id);
        if(deletedPost == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(deletedPost, HttpStatus.OK);
    }
}