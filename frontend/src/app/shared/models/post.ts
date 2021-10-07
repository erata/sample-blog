export class Post {
  id?: number;
  title?: string;
  content?: string;
  isPublished?: boolean;
  categories?: Array<string>;
  comments?: Array<string>;

  constructor() {
    this.isPublished = false;
  }

}
