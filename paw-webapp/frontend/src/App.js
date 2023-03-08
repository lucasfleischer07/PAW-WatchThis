import './App.css';
import {AuthProvider} from "./context/AuthContext";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./views/Login";


function App() {

  return (
      <AuthProvider>
          <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>
              <Routes>
                  <Route path='/login' element={<Login/>}/>
              </Routes>
          </BrowserRouter>
      </AuthProvider>
  );
}

export default App;
