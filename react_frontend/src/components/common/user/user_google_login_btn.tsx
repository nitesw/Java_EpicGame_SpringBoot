import {LoginButtonProps} from "../../../models/users.ts";
import {useGoogleLogin} from "@react-oauth/google";
import {Button} from "antd";

const GoogleLoginButton: React.FC<LoginButtonProps> = ({ onLogin, title, icon }) => {
    const login = useGoogleLogin({
        onSuccess: async (authCodeResponse) => {
            console.log("Google login token: ", authCodeResponse.access_token);
            onLogin(authCodeResponse.access_token);
        },
        onError: (error) => {
            console.error("Login failed: ", error);
        }
    });

    return (
        <Button icon={icon} onClick={() => login()} style={{width:'100%'}}>
            {title}
        </Button>
    )
}

export default GoogleLoginButton;