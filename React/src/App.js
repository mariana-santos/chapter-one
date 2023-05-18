
import { Routes, Route, BrowserRouter } from 'react-router-dom'

import Home from './Pages/Home'
import Loja from './Pages/Loja'
import Contato from './Pages/Contato'
import Carrinho from './Pages/Carrinho'
import Livro from './Pages/Livro'

import livros from './Data/livros.json'
import Autor from './Pages/Autor'

import { createContext, useContext, useState } from 'react'

export const CarrinhoContext = createContext();

function App() {

  const [carrinho, setCarrinho] = useState([]);

  const adicionarAoCarrinho = (livro) => {
    setCarrinho(prevCarrinho => [...prevCarrinho, livro]);
  };

  const atualizarLivro = (livro, id) => {
    setCarrinho(prevCarrinho => [...prevCarrinho, livro]);
  };

  const removerDoCarrinho = (livroId) => {
    setCarrinho(prevCarrinho => prevCarrinho.filter(item => item.id !== livroId));
  };

  const carrinhoContextValue = {
    carrinho,
    adicionarAoCarrinho,
    removerDoCarrinho
  };

  return (
    <CarrinhoContext.Provider value={ carrinhoContextValue }>
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
    </CarrinhoContext.Provider>
  );
}

export default App;
