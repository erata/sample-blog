export interface PostListItem {
  id?: number;
  title?: string;
  isPublished?: boolean;
  createdAt?: string;
  userName?: string;
  commentCount?: number;
  categories?: Array<string>;
}
