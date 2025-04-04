import {ReactNode} from "react";

export interface UserRegisterModel {
    username: string;
    password: string;
}

export interface LoginButtonProps {
    title: string;
    onLogin: (token: string) => void;
    icon: ReactNode;
}