import './App.css';
import {AuthProvider} from "./context/AuthContext";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./views/Login";
import Home from "./views/Home";
import ContentPage from "./views/ContentPage";


function App() {
  return (
      <AuthProvider>
          <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>
              <Routes>
                  <Route path='/' element={<Home/>}/>
                  <Route path='/content/:contentType' element={<ContentPage/>}/>
                  <Route path='/login' element={<Login/>}/>
              </Routes>
          </BrowserRouter>
      </AuthProvider>
  );
}

export default App;
