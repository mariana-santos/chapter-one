
import { Routes, Route, BrowserRouter } from 'react-router-dom'

import Home from './Pages/Home'
import Loja from './Pages/Loja'
import Contato from './Pages/Contato'
import Carrinho from './Pages/Carrinho'
import Livro from './Pages/Livro'

import livros from './Data/livros.json'
import Autor from './Pages/Autor'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/loja' element={<Loja />} />
        <Route path='/contato' element={<Contato />} />
        <Route path='/carrinho' element={<Carrinho />} />
        <Route path='/livro/:id' element={<Livro />} />
        <Route path='/autor/:id' element={<Autor />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
