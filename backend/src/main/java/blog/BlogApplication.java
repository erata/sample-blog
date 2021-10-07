package blog;

import blog.model.Category;
import blog.model.Comment;
import blog.model.Post;
import blog.model.User;
import blog.repository.PostRepository;
import blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner runner(UserRepository userRepository,
								   PostRepository postRepository,
									PasswordEncoder bcryptEncoder) {
		return (args) -> {
           *//*
			User user1 = new User("Ibrahim Erata", "asdx","info.erata@gmail.com",bcryptEncoder.encode("123456"));
			user1.setImage("https://via.placeholder.com/100x100?text=USER");
			user1.setIsActive(true);
			userRepository.save(user1);

			User user2 = new User("Baris Manco","barismanco","baris.manco@gmaix.com",bcryptEncoder.encode("32458"));
			user2.setIsActive(true);
			userRepository.save(user2);

			User user3 = new User("Zeki Müren","zekimuren","teki.muren@gmaix.com",bcryptEncoder.encode("32458"));
			userRepository.save(user3);


			log.info("Users saved...Fetch all Users with findAll():");
			userRepository.findAll().forEach(user -> {
				log.info(user.toString());
			});
			// for (User _user : userRepository.findAll()) { log.info(_user.toString());	}


			Post post1 = new Post("Post Title - 1",  "Lorem Ipsum ..... ", user1);
			Post post2 = new Post("Post Title - 2",  "Lorem Ipsum ..... ", user1);
			Post post3 = new Post("Post Title - 3",  "Lorem Ipsum ..... ", user2);
			Post post4 = new Post("Post Title - 4",  "Lorem Ipsum ..... ", user2);
			Post post5 = new Post("Post Title - 5",  "Lorem Ipsum ..... ", user3);

			post1.setIsPublished(true);
			post2.setIsPublished(true);
			post3.setIsPublished(true);

			post1.addComment(new Comment("commentor1", "commentor1@gmx.com", "Comment ...."));
			post1.addComment(new Comment("commentor2", "commentor2@gmx.com", "Comment ...."));
			post1.addComment(new Comment("commentor3", "commentor3@gmx.com", "Comment ...."));
			post2.addComment(new Comment("commentor4", "commentor4@gmx.com", "Comment ...."));
			post2.addComment(new Comment("commentor5", "commentor5@gmx.com", "Comment ...."));
			post3.addComment(new Comment("commentor6", "commentor6@gmx.com", "Comment ...."));
			post3.addComment(new Comment("commentor7", "commentor7@gmx.com", "Comment ...."));
			post3.addComment(new Comment("commentor8", "commentor8@gmx.com", "Comment ...."));
			post3.addComment(new Comment("commentor9", "commentor9@gmx.com", "Comment ...."));
			post4.addComment(new Comment("commentor2", "commentor2@gmx.com", "Comment ...."));
			post4.addComment(new Comment("commentor3", "commentor3@gmx.com", "Comment ...."));
			post4.addComment(new Comment("commentor4", "commentor4@gmx.com", "Comment ...."));
			post4.addComment(new Comment("commentor1", "commentor1@gmx.com", "Comment ...."));
			post4.addComment(new Comment("commentor2", "commentor2@gmx.com", "Comment ...."));
			post5.addComment(new Comment("commentor10", "commentor10@gmx.com", "Comment ...."));

			Category category = new Category("Category 1");
			post1.addCategory(category);
			post1.addCategory(new Category("Category 2"));
			post2.addCategory(new Category("Category 3"));
			post2.addCategory(category);
			post3.addCategory(new Category("Category 4"));
			post4.addCategory(new Category("Category 5"));
			post4.addCategory(new Category("Category 6"));
			post4.addCategory(category);
			post4.addCategory(new Category("Category 7"));
			post5.addCategory(new Category("Category 10"));
			post5.addCategory(category);

			postRepository.save(post1);
			postRepository.save(post2);
			postRepository.save(post3);
			postRepository.save(post4);
			postRepository.save(post5);

			log.info("Posts saved...Fetch all Posts with findAll():");
			for (PostListItemView post : postRepository.findAll()) {
				log.info(String.valueOf(post.getId()) + " - " + post.getTitle()  + " - " + post.getUserName()  + " - " + post.getCommentCount() + " - " + post.getCategories());
			}

			Optional<PostDetailsView> postDetailsView = postRepository.findPostDetailsById(1);
			if (postDetailsView.isPresent()) {
				PostDetailsView post = postDetailsView.get();
				log.info(String.valueOf(post.getId()) + " - " + post.getTitle() + " - " + post.getContent() + " - " + post.getCreatedAt() + " - " + post.getUserName() + " - " + post.getCommentCount() + " - " + post.getCategories());
			}*//*

		};
	}*/

}
