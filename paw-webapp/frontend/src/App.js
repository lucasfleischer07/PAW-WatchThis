import './App.css';
import {AuthProvider} from "./context/AuthContext";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./views/Login";
import Home from "./views/Home";
import ContentPage from "./views/ContentPage";
import WatchListPage from "./views/WatchListPage"
import UserPage from "./views/UserPage";
import ViewedListPage from "./views/ViewedListPage";

function App() {
  return (
      <AuthProvider>
          <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>
              <Routes>
                  <Route path='/' element={<Home/>}/>
                  <Route path='/content/:contentType' element={<ContentPage/>}/>
                  <Route path='/login' element={<Login/>}/>
                  <Route path='/user' element={<UserPage/>}>
                      <Route path='watchList' element={<WatchListPage/>}/>
                      <Route path='viewedList' element={<ViewedListPage/>}/>
                  </Route>
              </Routes>
          </BrowserRouter>
      </AuthProvider>
  );
}

export default App;
