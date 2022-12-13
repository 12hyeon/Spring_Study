package hello.springjpa.post;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PostForm {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private String title;
   private String description;

   public String getTitle() {
      return title;
   }
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) { this.description = description; }
   public void setTitle(String title) { this.title = title; }
}
