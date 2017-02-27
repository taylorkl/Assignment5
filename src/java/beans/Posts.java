/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@Named
@ApplicationScoped
/**
 *
 * @author c0537794
 */
public class Posts {

    private static void remove(Post oldPost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private List<Post> posts;
    private Post currentPost;

    public Posts() {
        currentPost = new Post(-1, -1, "", null, "");
        getPostsFromDB();
    }

    private void getPostsFromDB() {
       try (Connection conn = DBUtils.getConnection()) {
           posts = new ArrayList<>();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM posts");
           while (rs.next()) {
               Post p = new Post (
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getTimestamp("created_time"),
                rs.getString("contents")
                );
               posts.add(p);
             }
           }catch (SQLException ex) {
               Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
               posts = new ArrayList<>();
       }
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
    public Post getCurrentPost() {
        return currentPost;
    }
    
    public Post getPostById(int Id) {
        for (Post p : posts) {
            if (p.getId() == Id) {
                return p;
            }
        }
        return null;
    }
    
    public Post getPostByTitle(String title) {
        for (Post p : posts) {
            if (p.getTitle().equals(title)) {
                return p;
            }
        }
        return null;
    }
    
    public String viewPost(Post post) {
        currentPost = post;
        return "viewpost";
    }
    
    public String addPost() {
        currentPost = new Post(-1,-1, "", null, "");
        return "editPost";
    }
    
    public String editPost() {
        return "editPost";
    }
    
    public String cancelPost() {
        int id = currentPost.getId();
        getPostsFromDB();
        currentPost = getPostById(id);
        return "viewPost";
    }
    
    public void deletePost(int id) {
        Post oldPost = getPostById(id);
        Posts.remove(oldPost);
    }
    
    public String savePost(User user) {
       try (Connection conn = DBUtils.getConnection()) {
         if (currentPost.getId() >= 0) {
             String sql = "UPDATE posts SET title = ?, contents = ? WHERE id = ?";
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, currentPost.getTitle());
             pstmt.setString(2, currentPost.getContents());
             pstmt.setInt(3, currentPost.getId());
             pstmt.executeUpdate();
         } else {
             String sql = "INSERT into posts(user_id, title, created_time, contents) VALUES (?, ?, NOW(), ?)";
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setInt( 1, user.getId());
             pstmt.setString(2, currentPost.getTitle());
             pstmt.setString(3, currentPost.getContents());
             pstmt.executeUpdate();
         } 
       } catch(SQLException ex) {
           Logger.getLogger(Posts.class.getName()).log(Level. SEVERE, null, ex);
       }
     getPostsFromDB();
     currentPost = getPostByTitle(currentPost.getTitle());
     return "viewPost";
    }   
    
        
}
