import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import './style.css'

import editoras from '../../Data/editoras.json'
import livros from '../../Data/livros.json'
import categorias from '../../Data/categorias.json'

import Header from '../../Components/Header';

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

    const getEditora = (livro) => {
        const editora_obj = editoras.find((editora) => editora.id === livro.id_editora)
        setEditora(editora_obj.nome)
    }

    const getCategoria = (livro) => {
        const categoria_obj = categorias.find((categoria) => categoria.id === livro.id_categoria)
        setCategoria(categoria_obj.nome)
        console.log(livro)
    }

    //A cada vez que o id do parâmetro da rota é atualizado, o
    //useState "setLivro" é alterado com o livro correspondente
    useEffect(() => {
        const livro_obj = livros.find((livro) => livro.id === parseInt(id))
        setLivro(livro_obj)
    }, [id])

    //Toda vez que o livro o useState "setEditora" é atualizado
    useEffect(() => {

        if(livro) {
            getEditora(livro)
            getCategoria(livro)
        }

    }, [livro])

    if (!livro) return <h2>Livro não encontrado</h2>

    const { titulo, ano, imagem, paginas, resumo, isbn } = livro

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

                        <a href="/carrinho" className="btn">
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