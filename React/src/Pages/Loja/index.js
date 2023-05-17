import { useState } from "react"
import Header from "../../Components/Header"
import ListaLivros from "../../Components/ListaLivros"

import livros from '../../Data/livros.json'
import categorias from '../../Data/categorias.json'

import './style.css'

export default function Loja(){

    const [pesquisa, setPesquisa] = useState()
    const [categoria, setCategoria] = useState('Todas')

    return(
        <>
            <Header />
            <div className="container" id="loja">

                <h3>Pesquisar um livro</h3>
                <div className="wrap_filter">
                    <input 
                        type="text" 
                        value={pesquisa} 
                        onChange={(e) => { setPesquisa(e.target.value) }}
                        placeholder="O que você está procurando?"
                    />

                    <select id="categoria" value={categoria} onChange={(e) => { setCategoria(e.target.value) }} >
                        { categorias.map((categoria) => {
                            return(<option value={categoria.id}> {categoria.nome} </option>)
                        }) }
                    </select>
                </div>

                <h2 id="livros"> Livros disponíveis </h2>
                <ListaLivros livros={livros} />

            </div>
        </>
    )
}