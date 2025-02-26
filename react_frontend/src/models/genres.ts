export interface GenreModel {
    id: number;
    name: string;
    imageUrl: string;
    description: string;
}

export interface GenrePostModel {
    name: string;
    imageUrl: string;
    description: string;
}

export interface GenrePutModel extends GenrePostModel {
    id: number;
}