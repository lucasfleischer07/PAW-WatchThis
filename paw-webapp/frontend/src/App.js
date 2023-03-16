import './App.css';
import {AuthProvider} from "./context/AuthContext";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./views/Login";
import Home from "./views/Home";
import CarrouselContent from "./views/components/CarrouselContent";
import {contentService} from "./services";
import {useEffect, useState} from "react";


function App() {

    // const [content, setContent] = useState(undefined)

    // useEffect(() => {
    //     if(content === undefined) {
    //         contentService.getSpecificContent(85)
    //             .then(data => {
    //                 setContent(data.data)
    //                 console.log(data.data)
    //
    //             })
    //             .catch(e => {
    //                 console.log(e)
    //             })
    //     }
    //
    // }, [])

  return (
      <AuthProvider>
          <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>
              <Routes>
                  <Route path='/' element={<Home/>}/>
                  {/*<Route path='/carrousel' element={() => {<CarrouselContent props={content}/> }}/>*/}
                  <Route path='/login' element={<Login/>}/>
              </Routes>
          </BrowserRouter>
      </AuthProvider>
  );
}

export default App;
