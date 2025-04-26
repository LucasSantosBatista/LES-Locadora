package br.com.locadora.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.locadora.usuario.exception.CriptoExistsException;
import br.com.locadora.usuario.exception.EmailExistsException;
import br.com.locadora.usuario.exception.ServiceExc;

import java.security.NoSuchAlgorithmException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvarUsuario(Usuario user) throws Exception {

        try {

            if(usuarioRepository.findByEmail(user.getEmail()) !=null) {
                throw new EmailExistsException("Este email já esta cadastrado: " + user.getEmail());
            }

            user.setSenha(Util.md5(user.getSenha()));

        } catch (NoSuchAlgorithmException e) {
            throw new CriptoExistsException("Error na criptografia da senha");
        }
        usuarioRepository.save(user);
    }

    public Usuario loginUser(String user, String senha) throws ServiceExc {

        return usuarioRepository.buscarLogin(user, senha);
    }
}
