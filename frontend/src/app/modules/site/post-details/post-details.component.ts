import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PostService} from "../../../services/post.service";
import {Comment} from "../../../shared/models/comment";
import {Post} from "../../../shared/models/post";

@Component({
  selector: 'app-posts-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  currentPost: Post;
  message = '';
  postId: string;
  comment: Comment;
  hideComment:boolean = true;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router) {
    this.comment = new Comment();
  }

  ngOnInit() {
    this.postId = this.route.snapshot.paramMap.get('id');
    this.getPost();
  }

  getPost() {
    console.log("this.postId ",this.postId)
    this.postService.get(this.postId)
      .subscribe(
        data => {
          this.currentPost = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  addComment(){
    this.postService.createComment(this.postId, this.comment)
      .subscribe(
        data => {
          console.log(data);
          this.currentPost = data;
          this.comment = new Comment();
        },
        error => {
          console.log(error);
        });
  }

}
