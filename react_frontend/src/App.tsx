import './App.css'
import {Route, Routes} from "react-router-dom";

function App() {

  return (
      <Routes>
          <Route path="/" element={<div>Home page</div>} />
      </Routes>
  )
}

export default App
