import './App.css'
import {Route, Routes} from "react-router-dom";
import Layout from "./components/common/layout/layout.tsx";
import NotFound from "./pages/common/page_not_found.tsx";
import HomePage from "./pages/common/home_page.tsx";
import AboutPage from "./pages/common/about_page.tsx";
import ContactPage from "./pages/common/contact_page.tsx";
import GenresListPage from "./pages/common/genres/genres_list_page.tsx";
import RegisterPage from "./pages/common/register_page.tsx";
import LoginPage from "./pages/common/login_page.tsx";
import { Spin } from 'antd';
import { useAppSelector } from './redux/hooks.ts';
import {selectSpinner} from "./redux/spinner/spinnerSlice.ts";
import { LoadingOutlined } from '@ant-design/icons';
import GenreCreateForm from "./components/common/genres/genres_create_form.tsx";
import GenreEditForm from "./components/common/genres/genres_edit_form.tsx";

function App() {
    const spinner = useAppSelector(selectSpinner);

    return (
        <>
            {spinner && (
                <div style={{
                    position: "fixed",
                    top: 0,
                    left: 0,
                    width: "100vw",
                    height: "100vh",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    backgroundColor: "rgba(255, 255, 255, 0.7)",
                    zIndex: 9999,
                }}>
                    <Spin indicator={<LoadingOutlined spin />} spinning={spinner} size="large" />
                </div>
            )}
            <div style={{minHeight: "100vh"}}>
                <Routes>
                    <Route path="/" element={<Layout/>}>
                        <Route index element={<HomePage/>}/>
                        <Route path="about" element={<AboutPage/>}/>
                        <Route path="genres">
                            <Route index element={<GenresListPage/>} />
                            <Route path="create" element={<GenreCreateForm />} />
                            <Route path="edit/:id" element={<GenreEditForm />} />
                        </Route>
                        <Route path="contact" element={<ContactPage/>}/>
                        <Route path="register" element={<RegisterPage/>}/>
                        <Route path="login" element={<LoginPage/>}/>
                        <Route path="*" element={<NotFound/>}/>
                    </Route>
                </Routes>
            </div>
        </>
    )
}

export default App
