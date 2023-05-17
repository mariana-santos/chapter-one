import { useEffect, useState } from "react"
import Header from "../../Components/Header"
import ListaLivros from "../../Components/ListaLivros"

import livros from '../../Data/livros.json'
import categorias from '../../Data/categorias.json'

import './style.css'

export default function Loja(){

    const [pesquisa, setPesquisa] = useState()
    const [categoria, setCategoria] = useState('Todas')
    
    const [livros, setLivros] = useState([])
    const [carregando, setCarregando] = useState(true)
    const [categorias, setCategorias] = useState([])

    useEffect(() => {

        fetch('http://localhost:8000/livros')
        .then(resp => resp.json())
        .then((data) => {
            setLivros(data)
            console.log(data)
        })
        .catch(error => console.error(error))   

        fetch(`http://localhost:8000/categorias`)
        .then(resp => resp.json())
        .then(categorias => setCategorias(categorias))
        .catch(error => console.error(error))

    }, [])

    return(
        <>
            <Header />
            <div className="container" id="loja">

                <h3 className="titulo">Pesquisar um livro</h3>
                <div className="wrap_filter">
                    <input type="text" value={pesquisa} onChange={(e) => { setPesquisa(e.target.value) }} />

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