import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import './style.css'

import livros from '../../Data/livros.json'
import autores from '../../Data/autores.json'

import Header from '../../Components/Header';

import { AiFillPhone, AiFillMail } from 'react-icons/ai'

import ListaLivros from '../../Components/ListaLivros'

export default function Autor(){

    //Resgatando o id do autor enviado pelas rotas
    const { id } = useParams()
    
    const [autor, setAutor] = useState()
    const [livrosBy, setLivros] = useState([])

    const getLivros = (autor) => {
        fetch(`http://localhost:8000/livros_por_autor/${id}`)
        .then(resp => resp.json())
        .then(data => setLivros(data))
        .catch(error => console.error(error))
    }

    //A cada vez que o id do parâmetro da rota é atualizado, o
    //useState "setAutor" é alterado com o livro correspondente
    useEffect(() => {

        fetch(`http://localhost:8000/autor/${id}`)
        .then(resp => resp.json())
        .then(data => setAutor(data))
        .catch(error => console.error(error))

    }, [id])

    //Toda vez que o livro o useState "livrosBy" é atualizado
    useEffect(() => {

        if(autor) getLivros(autor)

    }, [autor])

    if (!autor) return <h2>Autor não encontrado</h2>

    const { nome, imagem, email, telefone, bio } = autor

    return(
        <>
            <Header />
            <div className='container'>
                <main className="autor">
                    <div className="coluna img_autor">
                        <img src={ imagem } alt={`Imagem de ${ nome }`} />
                    </div>

                    <div className="coluna info_autor">
                        <h2> { nome } </h2>
                        <p> { bio } </p>

                        <p>
                            <AiFillPhone /> { telefone }
                        </p>

                        <p>
                            <AiFillMail /> { email }
                        </p>

                    </div>
                </main>

                <h2>Livros de { nome } </h2>
                <ListaLivros livros={ livrosBy } />
            </div>
        </>
    )
}