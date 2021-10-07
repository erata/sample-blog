import {PostListItem} from "./post-list-item";

export interface PageablePosts {
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
  content?: Array<PostListItem>;
}
