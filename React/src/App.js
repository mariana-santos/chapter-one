
import { Routes, Route, BrowserRouter } from 'react-router-dom'

import Home from './Pages/Home'
import Loja from './Pages/Loja'
import Contato from './Pages/Contato'
import Carrinho from './Pages/Carrinho'
import Livro from './Pages/Livro'

import livros from './Data/livros.json'
import Autor from './Pages/Autor'

import Footer from './Components/Footer'

import { createContext, useContext, useState, useEffect } from 'react'

export const CarrinhoContext = createContext();

function App() {

  const [carrinho, setCarrinho] = useState([]);

  useEffect(() => {
    const carrinhoSalvo = sessionStorage.getItem('carrinho');
    if (carrinhoSalvo) {
      setCarrinho(JSON.parse(carrinhoSalvo));
    }
  }, []);

  useEffect(() => {
    carrinho.length >= 1 && sessionStorage.setItem('carrinho', JSON.stringify(carrinho));
  }, [carrinho]);

  const adicionarAoCarrinho = (livro) => {
    const livroExistente = carrinho.find((item) => item.id === livro.id);
    if (livroExistente) {
      console.log('Livro já está no carrinho');
      return;
    }
    setCarrinho((prevCarrinho) => [...prevCarrinho, livro]);
  };

  const atualizarLivro = (livro, id) => {
    setCarrinho(prevCarrinho => {
      const livroIndex = prevCarrinho.findIndex(item => item.id === id);
      if (livroIndex !== -1) {
        const novoCarrinho = [...prevCarrinho];
        novoCarrinho[livroIndex] = livro;
        return novoCarrinho;
      }
      return prevCarrinho;
    });
  };

  const removerDoCarrinho = (livroId) => {
    setCarrinho(prevCarrinho => prevCarrinho.filter(item => item.id !== livroId));
  };

  const carrinhoContextValue = {
    carrinho,
    adicionarAoCarrinho,
    removerDoCarrinho,
    atualizarLivro,
    setCarrinho
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
      <Footer />
    </CarrinhoContext.Provider>
  );
}

export default App;
