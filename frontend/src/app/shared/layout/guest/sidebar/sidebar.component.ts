import { Component, OnInit } from '@angular/core';
import {CategoryListItem} from "../../../models/category-list-item";
import {CategoryService} from "../../../../services/category.service";
import {PagingService} from "../../../../services/paging.service";

@Component({
  selector: 'site-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  categories: Array<CategoryListItem>;
  searchText: string;
  //location: Location;

  constructor(private categoryService: CategoryService,
              private pagingService: PagingService) { }

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(
      (data: Array<CategoryListItem>) => {
        this.categories = data;
        console.log(data);
      },
      error => {
        console.log(error);
      });
  }

  getCategoryPosts(category: string){
    console.log("category ", category);
    this.pagingService.pageEvent.emit({category: category})

    //this.pagingService.subject.next(new PagingFilter("", "", 0, 10));
  }

  getSearchPosts(searchedText: string){
    console.log("searchText ", searchedText);
    this.pagingService.pageEvent.emit({search: searchedText})

    //this.pagingService.subject.next(new PagingFilter("", "", 0, 10));
  }

}
