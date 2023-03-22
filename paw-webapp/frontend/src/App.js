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

function App() {
  return (
      <AuthProvider>
          <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>
              <Routes>
                  <Route path='/' element={<Home/>}/>
                  <Route path='/content/:contentType' element={<ContentPage/>}/>
                  <Route path='/login' element={<Login/>}/>
                  <Route path='/user' element={<UserPage/>}>
                      <Route path='profile/:userProfileId' element={<UserInfoPage/>}/>
                      <Route path='profile/editProfile' element={<ProfileEditionPage/>}/>
                      <Route path='watchList' element={<WatchListPage/>}/>
                      <Route path='viewedList' element={<ViewedListPage/>}/>
                  </Route>
              </Routes>

              <ToastContainer
                  position="top-right"
                  autoClose={5000}
                  hideProgressBar={false}
                  newestOnTop={false}
                  closeOnClick
                  rtl={false}
                  pauseOnFocusLoss
                  draggable
                  pauseOnHover
                  theme="colored"
              />

          </BrowserRouter>
      </AuthProvider>
  );
}

export default App;
