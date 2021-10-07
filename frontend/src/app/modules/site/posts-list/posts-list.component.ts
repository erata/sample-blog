import {Component, OnDestroy, OnInit} from '@angular/core';
import {PostService} from "../../../services/post.service";
import {PageablePosts} from "../../../shared/models/pageable-posts";
import {ActivatedRoute, Router} from "@angular/router";
import {PagingService} from "../../../services/paging.service";

@Component({
  selector: 'app-posts-list',
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.css']
})
export class PostsListComponent implements OnInit, OnDestroy {
  posts: PageablePosts;
  currentPost = null;
  currentIndex = -1;
  title = '';
  page:number = 0;
  size:number = 10;
  category:string;
  searchText:string;
  comment: string;
  //subscription: Subscription;

  constructor(private postService: PostService,
              private router: Router,
              private route: ActivatedRoute,
              private pagingService: PagingService) {

  }

  ngOnInit() {
   /*
   this.route.queryParams.subscribe(params => {
      this.category = decodeURIComponent(params['category']|| "");
      this.searchText = decodeURIComponent(params['search'] || "");
    });

    let category = this.route.snapshot.paramMap.get('category');
    let searchText = this.route.snapshot.paramMap.get('search');
    */

    this.pagingService.pageEvent.subscribe((item) => {
      //open your sidebar by setting classes, whatever
      console.log("event item....." , item, this.page);
      this.category = item.category || '';
      this.searchText = item.search || '';

      this.retrievePosts();
    });

    this.retrievePosts();
  }

  retrievePosts() {
    //back-end pager start 0
    if(this.page !== 0) {
      this.page = this.page - 1;
    }

    this.postService.getAll(this.category, this.searchText, this.page, this.size)
      .subscribe(
        (data: PageablePosts) => {
          this.posts = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });

  }


  pageChanged(event: any){
    console.log("selectedPage: " , event.page);
    this.page = event.page;
    console.log("selectedPage: " , event.page, this.page);
    this.retrievePosts();
  }

  goToPost(id:number) {
    this.router.navigate(['/posts', id]);
  }

  refreshList() {
    this.retrievePosts();
    this.currentPost = null;
    this.currentIndex = -1;
  }

  setActivePost(tutorial, index) {
    this.currentPost = tutorial;
    this.currentIndex = index;
  }

  removeAllPosts() {
    this.postService.deleteAll()
      .subscribe(
        response => {
          console.log(response);
          this.retrievePosts();
        },
        error => {
          console.log(error);
        });
  }

  searchTitle() {
    this.postService.findByTitle(this.title)
      .subscribe(
        (data: PageablePosts) => {
          this.posts = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    //this.subscription.unsubscribe();
  }
}

