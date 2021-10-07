import { Component, OnInit } from '@angular/core';
import {PostService} from "../../../services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../../shared/models/post";

@Component({
  selector: 'app-authorised-post-details',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {
  currentPost: Post;
  strCategories: string;
  message = '';
  editorContent:string;
  isPublished: boolean;
  postId: number;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router) {

    this.message = '';
    this.isPublished = false;
  }

  ngOnInit() {
    this.postId = +this.route.snapshot.paramMap.get('id');
    this.getPost();
  }

  getPost() {
    this.postService.get(this.postId)
      .subscribe(
        data => {
          this.currentPost = data;
          this.editorContent = this.currentPost.content;
          this.strCategories = this.currentPost.categories.join();
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  logClick(parameters: { i: any }){
    let i = parameters.i;
    console.log(i)};

  changePostStatus(isPublished: boolean) {
    this.postService.update(this.postId, { isPublished: isPublished })
      .subscribe(
        response => {
          this.currentPost = response;
          console.log(response);
        },
        error => {
          console.log(error);
        });
  }

  savePost() {
    if(this.strCategories) {
      this.strCategories = this.strCategories.trim().replace(/\s*,\s*/g, ",").replace(/[,|,\s]*(?=$)/,"");
      this.currentPost.categories = this.strCategories.split(',')
    }

    this.currentPost.isPublished = this.isPublished || false;

    this.postService.update(this.postId, this.currentPost)
      .subscribe(
        response => {
          console.log(response);
          this.message = 'The tutorial was updated successfully!';
          this.isPublished = false;
          this.strCategories = "";
        },
        error => {
          console.log(error);
        });
  }

  publishPost(){
    this.isPublished= true;
    this.savePost();
  }

  deletePost() {
    this.postService.delete(this.postId)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/dashboard']);
        },
        error => {
          console.log(error);
        });
  }

}
