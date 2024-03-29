import './App.css';
import {AuthProvider} from "./context/AuthContext";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./views/Login";
import Home from "./views/Home";
import ContentPage from "./views/ContentPage";
import WatchListPage from "./views/WatchListPage"
import UserPage from "./views/UserPage";
import ViewedListPage from "./views/ViewedListPage";
import ProfileEditionPage from "./views/ProfileEditionPage";
import UserInfoPage from "./views/UserInfoPage";

import {ToastContainer} from "react-toastify";
import {paths} from "./paths";

import LoginPage from "./views/LoginPage";
import ForgotPassword from "./views/ForgotPassword";
import ReviewRegistrationPage from "./views/ReviewRegistrationPage";
import ReviewEditionPage from "./views/ReviewEditionPage";
import ContentCreatePage from "./views/ContentCreatePage";
import ReportedPage from "./views/ReportedPage";
import ErrorPage from "./views/ErrorPage";
import InfoPage from "./views/InfoPage";
import SignUp from "./views/SignUp";

function App() {
  return (
      <BrowserRouter basename={paths.BASE_URL_LOCAL}>
          <AuthProvider>

              <Routes>
                  <Route path='/' element={<Home/>}/>
                  <Route path='/content/:contentType' element={<ContentPage/>}/>
                  <Route path='/content/:contentType/:contentId' element={<InfoPage/>}/>
                  <Route path='/content/form/:formType' element={<ContentCreatePage/>}/>
                  <Route path='/content/form/:formType/:contentId' element={<ContentCreatePage/>}/>
                  <Route path='/content/:contentType/:contentId/reviewRegistration' element={<ReviewRegistrationPage/>}/>
                  <Route path='/content/:contentType/:contentId/:reviewId/reviewEdition' element={<ReviewEditionPage/>}/>
                  <Route path='/login' element={<LoginPage/>}>
                      <Route index element={<Login/>}/>
                      <Route path='forgotPassword' element={<ForgotPassword/>}/>
                      <Route path='signUp' element={<SignUp/>}/>
                  </Route>
                  <Route path='/user' element={<UserPage/>}>
                      <Route path='profile/:userProfileId' element={<UserInfoPage/>}/>
                      <Route path='profile/editProfile' element={<ProfileEditionPage/>}/>
                      <Route path='watchList' element={<WatchListPage/>}/>
                      <Route path='viewedList' element={<ViewedListPage/>}/>
                  </Route>
                  <Route path='/reports' element={<ReportedPage/>}/>

                  <Route path='/error' element={<ErrorPage errorCode={404}/>}/>
                  <Route path='*' element={<ErrorPage errorCode={404}/>}/>

              </Routes>

              <ToastContainer
                  position="top-right"
                  autoClose={2000}
                  hideProgressBar={false}
                  newestOnTop={false}
                  closeOnClick
                  rtl={false}
                  pauseOnFocusLoss
                  draggable
                  pauseOnHover
                  theme="dark"
              />
          </AuthProvider>

      </BrowserRouter>
  );
}

export default App;
