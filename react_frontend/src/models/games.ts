import dayjs from "dayjs";

export interface GameModel {
    id: number;
    title: string;
    developer: string;
    publisher: string;
    releaseDate: string;
    price: number;
    isFree: boolean;
    genreName: string;
    genreId: string;
    images: GameImageModel[];
}

export interface GameImageModel {
    id: number;
    imageUrl: string;
    priority: number;
}

export interface GamePostModel {
    title: string;
    developer: string;
    publisher: string;
    releaseDate?: string;
    price: number;
    isFree?: boolean;
    genreId: string;
    images?: File[];
}

export interface GamePutModel {
    id: number;
    title: string;
    developer: string;
    publisher: string;
    releaseDate?: dayjs.Dayjs | string;
    price: number;
    isFree?: boolean;
    genreId: string;
    images?: File[];
}