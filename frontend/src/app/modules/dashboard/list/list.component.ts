import { Component, OnInit } from '@angular/core';
import {PageablePosts} from "../../../shared/models/pageable-posts";
import {PostService} from "../../../services/post.service";
import {PagingService} from "../../../services/paging.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DashboardEventsService} from "../../../services/dashboard-events.service";

@Component({
  selector: 'app-post-list-authorised',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
  posts: PageablePosts;
  currentPost = null;
  currentIndex = -1;
  title = '';
  page:number = 0;
  size:number = 50;
  category:string;
  searchText:string;
  //subscription: Subscription;

  selectedRows: any[] = [];

  constructor(private postService: PostService,
              private router: Router,
              private route: ActivatedRoute,
              private pagingService: PagingService,
              private dashboardEventsService: DashboardEventsService){}

  ngOnInit() {
    /*
    this.route.queryParams.subscribe(params => {
       this.category = decodeURIComponent(params['category']|| "");
       this.searchText = decodeURIComponent(params['search'] || "");
     });

     let category = this.route.snapshot.paramMap.get('category');
     let searchText = this.route.snapshot.paramMap.get('search');
     */

    console.log(this.category, this.searchText);
    console.log("before pagingService")
    this.pagingService.pageEvent.subscribe((item) => {
      this.category = item.category || '';
      this.searchText = item.search || '';

      this.retrievePosts();
    });

    this.dashboardEventsService.postBulkActionEvent.subscribe((value) => {
     if(value == 'delete')
       this.bulkDelete();
     if(value == 'publish')
       this.bulkPublish();
      if(value == 'unpublish')
        this.bulkUnpublish();
    });

    this.retrievePosts();
  }

  retrievePosts() {
    this.postService.getAllUserPosts(this.page, this.size)
      .subscribe(
        (data: PageablePosts) => {
          this.posts = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });

  }

  pageChanged(selectedPage: number){
    console.log("selectedPage: " + selectedPage);
    selectedPage = selectedPage - 1;
    this.page = selectedPage;
    this.retrievePosts();
  }

  deletePost(id: number, index: number) {
    this.postService.delete(id)
      .subscribe(
        (data: any) => {
          console.log(data);
          //this.retrievePosts();

          this.posts.content.splice(index, 1);
        },
        error => {
          console.log(error);
        });
  }

  changePostStatus(id: number, isPublished: boolean){
    this.postService.update(id, { isPublished: isPublished })
      .subscribe(
        (data: any) => {
          console.log(data);

          let itemIndex = this.posts.content.findIndex(item => item.id == id);
          this.posts.content[itemIndex].isPublished = isPublished;

        },
        error => {
          console.log(error);
        });
  }

  checkRow(event:any, id: number){
    let isChecked: boolean = event.target.checked;

    if(isChecked)
      this.selectedRows.push(id);
    else
      this.selectedRows.splice(this.selectedRows.indexOf(id), 1);

    //to activate or passivate bulk menues in dashboard
    this.dashboardEventsService.postSelectionEvent.emit(this.selectedRows.length);
  }

  bulkDelete(){
    this.postService.bulkDelete(this.selectedRows)
      .subscribe(
        (data: any) => {
          console.log(data);
          this.selectedRows.length = 0;
          this.retrievePosts();
        },
        error => {
          console.log(error);
        });
  }

  bulkPublish(){
    console.log("bulkPublish")
    this.postService.bulkPublish(this.selectedRows)
      .subscribe(
        (data: any) => {
          console.log(data);
          this.selectedRows.length = 0;
          this.retrievePosts();
        },
        error => {
          console.log(error);
        });
  }

  bulkUnpublish(){
    this.postService.bulkUnpublish(this.selectedRows)
      .subscribe(
        (data: any) => {
          console.log(data);
          this.selectedRows.length = 0;
          this.retrievePosts();
        },
        error => {
          console.log(error);
        });
  }

}
