import { useEffect, useState } from "react"
import Header from "../../Components/Header"
import ListaLivros from "../../Components/ListaLivros"

import img_erro from '../../Assets/illustr_search.svg'

import api from "../../services/api"

import './style.css'

export default function Loja() {

    const params = new URL(document.location).searchParams;
    let categoriaParam= params.get("categoria");

    const [pesquisa, setPesquisa] = useState('')
    const [categoria, setCategoria] = useState(categoriaParam ? categoriaParam : 'Todas')

    const [livros, setLivros] = useState([])
    const [livrosFiltrados, setLivrosFiltrados] = useState([])
    const [carregando, setCarregando] = useState(true)
    const [categorias, setCategorias] = useState([])

    useEffect(() => {
        api.get('/livros')
            .then((resp) => {
                setLivros(resp.data)
                setLivrosFiltrados(resp.data)
                setCarregando(false)
            })
            .catch(error => console.error(error))

        api.get(`/categorias`)
            .then(resp => setCategorias(resp.data))
            .catch(error => console.error(error))
    }, [])

    function filtrarLivros(livros, pesquisa, categoria) {
        // Filtrar por pesquisa
        const livrosFiltrados = livros.filter((livro) => {
            const titulo = livro.titulo.toLowerCase();
            return titulo.includes(pesquisa.toLowerCase());
        });

        // Filtrar por categoria
        if (categoria !== 'Todas') {
            return livrosFiltrados.filter((livro) => livro.id_categoria == categoria);
        }

        return livrosFiltrados;
    }
    
    useEffect(() => {
        setLivrosFiltrados(filtrarLivros(livros, pesquisa, categoria))

        console.log(categoria)
    }, [categoria, pesquisa])

    return (
        <>
            <Header />
            <div className="container" id="loja">

                <h3 className="titulo">Pesquisar um livro</h3>
                <div className="wrap_filter">
                    <input
                        type="text"
                        value={pesquisa}
                        onChange={(e) => { setPesquisa(e.target.value) }}
                        placeholder="O que você está procurando?"
                    />

                    <select id="categoria" value={categoria} onChange={(e) => { setCategoria(e.target.value) }} >
                        <option value="Todas">Todas as categorias</option>
                        {categorias.map((categoria) => {
                            return (<option value={categoria.id}> {categoria.nome} </option>)
                        })}
                    </select>
                </div>

                {livrosFiltrados.length > 0 ? (
                    <>
                        <h2 id="livros"> Livros disponíveis </h2>
                        <ListaLivros livros={livrosFiltrados} />
                    </>
                ) : (
                    <div className="error">
                        <img src={img_erro} />
                        <p>Opa! Parece que não encontramos nada por aqui.</p>
                        <button className="btn" 
                        onClick={() => {
                            setCategoria('Todas')
                            setPesquisa('')
                        }}>
                            ver todos os livros</button>
                    </div>
                )}

            </div>
        </>
    )
}