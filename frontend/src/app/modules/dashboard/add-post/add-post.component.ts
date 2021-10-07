import { Component, OnInit } from '@angular/core';
import {PostService} from "../../../services/post.service";
import {Post} from "../../../shared/models/post";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  post: Post;
  strCategories: string;
  submitted = false;
  editor: string;

  constructor(private postService: PostService) {
    this.post = new Post();
  }

  ngOnInit() {}

  savePost() {
    if(this.strCategories) {
      this.strCategories = this.strCategories.trim().replace(/\s*,\s*/g, ",").replace(/[,|,\s]*(?=$)/,"");
      this.post.categories = this.strCategories.split(',')
    }

    this.postService.create(this.post)
      .subscribe(
        response => {
          console.log(response);
          this.submitted = true;
          this.strCategories = "";
        },
        error => {
          console.log(error);
        });
  }

  publishPost(){
    this.post.isPublished = true;
    this.savePost();
  }

  newPost() {
    this.submitted = false;
    this.post = new Post();
  }

}
