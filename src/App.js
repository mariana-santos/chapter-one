
import { Routes, Route, BrowserRouter } from 'react-router-dom'

import Home from './Pages/Home'
import Loja from './Pages/Loja'
import Contato from './Pages/Contato'
import Carrinho from './Pages/Carrinho'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/loja' element={<Loja />} />
        <Route path='/contato' element={<Contato />} />
        <Route path='/carrinho' element={<Carrinho />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
