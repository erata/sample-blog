export class PagingFilter {
  constructor(
    public category: string,
    public search:string,
    public page: number,
    public size: number
  ) {}
}

