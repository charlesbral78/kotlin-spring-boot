package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.TopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Service
class TopicoService(
        private var topicos: List<Topico> = ArrayList(),
        private val topicoViewMapper: TopicoViewMapper,
        private val topicoFormMapper: TopicoFormMapper
  ) {

    fun listar(): List<TopicoView> {
        return topicos.stream().map {
                t -> topicoViewMapper.map(t)
        }.collect(Collectors.toList())
        }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicos.stream().filter{
            t -> t.id == id
        }.findFirst().get()
        return topicoViewMapper.map(topico)
    }
    fun cadastrar(form: TopicoForm) {
        val topico = topicoFormMapper.map(form)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(topico)

    }

    fun atualizar(form: AtualizacaoTopicoForm) {
        val topico = topicos.stream().filter {
            t -> t.id == form.id
        }.findFirst().get()
        //Exclui o topico anterior e cria um novo sendo que, atualiza os atributos da classe
        //form e o que não atualizar pega da classe topico
        topicos = topicos.minus(topico).plus(Topico(
            //Exceto o id que pode ser tanto do form quanto do topico
            id = form.id,
            titulo = form.titulo,
            mensagem = form.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            resposta = topico.resposta,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        ))
    }
}

