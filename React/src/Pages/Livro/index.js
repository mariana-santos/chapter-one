import React, { useState, useEffect, useContext } from 'react';
import { useParams } from 'react-router-dom';

import './style.css'

import editoras from '../../Data/editoras.json'
import livros from '../../Data/livros.json'
import categorias from '../../Data/categorias.json'
import Header from '../../Components/Header';
import { CarrinhoContext } from '../../App';

import { BiShareAlt, BiBuildings } from 'react-icons/bi'
import { HiShoppingCart } from 'react-icons/hi2'
import { TbBarcode, TbBook, TbCalendarEvent, TbCategory } from 'react-icons/tb'
import { BsPersonFill, BsTextParagraph } from 'react-icons/bs'

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function Livro(){
    
    //Resgatando o id do livro enviado pelas rotas
    const { id } = useParams()
    
    const [editora, setEditora] = useState('')
    const [categoria, setCategoria] = useState('')
    const [livro, setLivro] = useState()

    const current_url = document.location.href

    const [verMais, setVerMais] = useState(false)

    const getEditora = (id) => {
        fetch(`http://localhost:8000/editora/${id}`)
        .then(resp => resp.json())
        .then(data => setEditora(data.nome))
        .catch(error => console.error(error))
    }

    const getCategoria = (id) => {
        fetch(`http://localhost:8000/categoria/${id}`)
        .then(resp => resp.json())
        .then(data => setCategoria(data.nome))
        .catch(error => console.error(error))
    }

    const { adicionarAoCarrinho, carrinho } = useContext(CarrinhoContext);

    //A cada vez que o id do parâmetro da rota é atualizado, o
    //useState "setLivro" é alterado com o livro correspondente
    useEffect(() => {
        fetch(`http://localhost:8000/livro/${id}`)
        .then(resp => resp.json())
        .then(data => setLivro(data))
        .catch(error => console.error(error))

        // const livro_obj = livros.find((livro) => livro.id === parseInt(id))
        // setLivro(livro_obj)
    }, [id])

    //Toda vez que o livro o useState "setEditora" é atualizado
    useEffect(() => {

        if(livro) {
            getEditora(id)
            getCategoria(id)
        }

    }, [livro])

    if (!livro) return <h2>Livro não encontrado</h2>

    const { titulo, ano, imagem, paginas, resumo, isbn, desconto, preco } = livro

    return(
        <>
            <Header />
            <div className='container'>
                <main className="livro">
                    <div className="coluna img_livro">
                        <img src={ imagem } alt={`Capa de ${ titulo }`} />
                    </div>

                    <div className="coluna info_livro">
                        <h3>
                            { titulo }
                            <BiShareAlt 
                                onClick={() => {
                                    navigator.clipboard.writeText(current_url)
                                    toast.success('Link copiado para a sua área de transferência!')
                                }}
                            />
                        </h3>

                        <InfoLivro label="Ano" value={ ano } icon={  <TbCalendarEvent />} />
                        <InfoLivro label="Categoria" value={ categoria } icon={  <TbCategory />} />
                        <InfoLivro label="Editora" value={ editora } icon={  <BiBuildings />} />
                        <InfoLivro label="Autor" value={ editora } icon={  <BsPersonFill />} />
                        <InfoLivro label="Páginas" value={ paginas } icon={  <TbBook />} />
                        <InfoLivro label="ISBN" value={ isbn } icon={  <TbBarcode />} />

                        <p className={`resumo ${ verMais ? 'ver_mais' : 'ver_menos'}`}> 
                            <span>
                                <BsTextParagraph /> Resumo: 
                            </span>
                            { resumo }
                        </p>
  
                        <a  href="#" 
                            onClick={() => { setVerMais(!verMais) }}
                            className="btn_secondary">
                                { verMais ? 'ver menos' : 'ver mais' }
                        </a>

                        <p className="preco">
                            {desconto > 0 && <span className='desconto'>{formatarPreco(preco)}</span>}
                            {formatarPreco(preco - desconto)}
                        </p>

                        <a href="/carrinho" 
                           onClick={() => { adicionarAoCarrinho({ ...livro, qtd: 1 }) }}
                           className="btn">
                            Adicionar ao carrinho
                            <HiShoppingCart />
                        </a>
                    </div>
                </main>

                <ToastContainer 
                    position='bottom-right'
                    theme="dark"
                />
            </div>
        </>
    )
}

function InfoLivro(props){
    const { label, icon, value } = props
    return(
        <p> 
            <span>
                {icon } {label}: 
            </span>
            { value } 
        </p>
    )
}

function formatarPreco(preco) {
    return "R$ " + preco.toFixed(2).replace(".", ",")
  }
  