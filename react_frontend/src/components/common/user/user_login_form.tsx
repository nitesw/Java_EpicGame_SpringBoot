import {Button, Form, Input, notification} from "antd";
import {UserRegisterModel} from "../../../models/users.ts";
import {useNavigate} from "react-router-dom";
import {useRegisterMutation} from "../../../services/api.auth.ts";
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {setSpinner} from "../../../redux/spinner/spinnerSlice.ts";
import {GoogleOAuthProvider} from "@react-oauth/google";
import {APP_ENV} from "../../../env";
import {GoogleOutlined} from "@ant-design/icons";
import GoogleLoginButton from "./user_google_login_btn.tsx";

const {Item} = Form;

const UserLoginForm = () => {
    const [form] = Form.useForm<UserRegisterModel>();
    // const navigate = useNavigate();
    // const [register, {isLoading: isRegistering}] = useRegisterMutation();
    // const dispatch = useDispatch();

    // useEffect(() => {
    //     dispatch(setSpinner(isRegistering));
    // }, [isRegistering, dispatch]);

    const onFinish = async (values: UserRegisterModel) => {
        console.log("onFinish", values);
        // try {
        //     console.log("Register fields: ", values);
        //     const response = await register(values).unwrap();
        //     notification.success({
        //         message: "Successfully registered!",
        //         description: response.message,
        //         placement: "top"
        //     });
        //     navigate("/login");
        // } catch (error) {
        //     let errorMessage = "Unknown error occurred";
        //
        //     if (typeof error === "object" && error !== null) {
        //         if ("data" in error && error.data && typeof error.data === "object") {
        //             errorMessage = (error.data as { message?: string }).message || errorMessage;
        //         } else if ("message" in error) {
        //             errorMessage = String(error.message);
        //         }
        //     }
        //
        //     notification.error({
        //         message: "Failed to register",
        //         description: errorMessage,
        //         placement: "top"
        //     });
        // }
    }

    const onGoogleLogin = (token: string) => {
        console.log("Google token: ", token);
    }

    return (
        <>
            <GoogleOAuthProvider clientId={APP_ENV.CLIENT_ID}>
                <div style={{maxWidth: '600px', margin: '0 auto'}}>
                    <h1 className="text-center text-4xl font-bold mb-5">Login</h1>
                    <Form form={form} onFinish={onFinish} layout={"vertical"}>
                        <Item
                            name="username"
                            label="Username"
                            rules={[
                                {required: true, message: "Username is required"},
                                {min: 3, message: "Username must be at least 3 characters"}
                            ]}
                        >
                            <Input placeholder="Enter username..."/>
                        </Item>
                        <Item
                            name="password"
                            label="Password"
                            rules={[
                                {required: true, message: "Password is required"},
                                {min: 6, message: "Password must be at least 6 characters"}
                            ]}
                        >
                            <Input.Password placeholder="Enter password..."/>
                        </Item>

                        <Item style={{width: '100%'}}>
                            <Button type="primary" htmlType="submit" style={{width: '100%'}}>
                                Login
                            </Button>
                        </Item>
                        <Item style={{width: '100%'}}>
                            <GoogleLoginButton
                                icon={<GoogleOutlined />}
                                title={"Login with google"}
                                onLogin={onGoogleLogin}
                            />
                        </Item>
                    </Form>
                </div>
            </GoogleOAuthProvider>
        </>
    )
}

export default UserLoginForm;