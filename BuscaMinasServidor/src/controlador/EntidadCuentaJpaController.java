/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import entidad.EntidadCuenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.EntidadPerfil;
import entidad.EntidadPartida;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import modelo.Cuenta;

/**
 *
 * @author fywer
 */
public class EntidadCuentaJpaController implements Serializable {

    public EntidadCuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntidadCuenta entidadCuenta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadPerfil entidadPerfil = entidadCuenta.getEntidadPerfil();
            /*
            if (entidadPerfil != null) {
                entidadPerfil = em.getReference(entidadPerfil.getClass(), entidadPerfil.getCuentaUsuario());
                entidadCuenta.setEntidadPerfil(entidadPerfil);
            }
            */
            EntidadPartida entidadPartida = entidadCuenta.getEntidadPartida();
            /*
            if (entidadPartida != null) {
                entidadPartida = em.getReference(entidadPartida.getClass(), entidadPartida.getCuentaUsuario());
                entidadCuenta.setEntidadPartida(entidadPartida);
            }
            */
            em.persist(entidadCuenta);
            /*
            if (entidadPerfil != null) {
                EntidadCuenta oldEntidadCuentaOfEntidadPerfil = entidadPerfil.getEntidadCuenta();
                if (oldEntidadCuentaOfEntidadPerfil != null) {
                    oldEntidadCuentaOfEntidadPerfil.setEntidadPerfil(null);
                    oldEntidadCuentaOfEntidadPerfil = em.merge(oldEntidadCuentaOfEntidadPerfil);
                }
                entidadPerfil.setEntidadCuenta(entidadCuenta);
                entidadPerfil = em.merge(entidadPerfil);
            }
            if (entidadPartida != null) {
                EntidadCuenta oldEntidadCuentaOfEntidadPartida = entidadPartida.getEntidadCuenta();
                if (oldEntidadCuentaOfEntidadPartida != null) {
                    oldEntidadCuentaOfEntidadPartida.setEntidadPartida(null);
                    oldEntidadCuentaOfEntidadPartida = em.merge(oldEntidadCuentaOfEntidadPartida);
                }
                entidadPartida.setEntidadCuenta(entidadCuenta);
                entidadPartida = em.merge(entidadPartida);
            }
            */
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntidadCuenta(entidadCuenta.getUsuario()) != null) {
                throw new PreexistingEntityException("EntidadCuenta " + entidadCuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EntidadCuenta entidadCuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadCuenta persistentEntidadCuenta = em.find(EntidadCuenta.class, entidadCuenta.getUsuario());
            EntidadPerfil entidadPerfilOld = persistentEntidadCuenta.getEntidadPerfil();
            EntidadPerfil entidadPerfilNew = entidadCuenta.getEntidadPerfil();
            EntidadPartida entidadPartidaOld = persistentEntidadCuenta.getEntidadPartida();
            EntidadPartida entidadPartidaNew = entidadCuenta.getEntidadPartida();
            List<String> illegalOrphanMessages = null;
            if (entidadPerfilOld != null && !entidadPerfilOld.equals(entidadPerfilNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EntidadPerfil " + entidadPerfilOld + " since its entidadCuenta field is not nullable.");
            }
            if (entidadPartidaOld != null && !entidadPartidaOld.equals(entidadPartidaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EntidadPartida " + entidadPartidaOld + " since its entidadCuenta field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadPerfilNew != null) {
                entidadPerfilNew = em.getReference(entidadPerfilNew.getClass(), entidadPerfilNew.getCuentaUsuario());
                entidadCuenta.setEntidadPerfil(entidadPerfilNew);
            }
            if (entidadPartidaNew != null) {
                entidadPartidaNew = em.getReference(entidadPartidaNew.getClass(), entidadPartidaNew.getCuentaUsuario());
                entidadCuenta.setEntidadPartida(entidadPartidaNew);
            }
            entidadCuenta = em.merge(entidadCuenta);
            if (entidadPerfilNew != null && !entidadPerfilNew.equals(entidadPerfilOld)) {
                EntidadCuenta oldEntidadCuentaOfEntidadPerfil = entidadPerfilNew.getEntidadCuenta();
                if (oldEntidadCuentaOfEntidadPerfil != null) {
                    oldEntidadCuentaOfEntidadPerfil.setEntidadPerfil(null);
                    oldEntidadCuentaOfEntidadPerfil = em.merge(oldEntidadCuentaOfEntidadPerfil);
                }
                entidadPerfilNew.setEntidadCuenta(entidadCuenta);
                entidadPerfilNew = em.merge(entidadPerfilNew);
            }
            if (entidadPartidaNew != null && !entidadPartidaNew.equals(entidadPartidaOld)) {
                EntidadCuenta oldEntidadCuentaOfEntidadPartida = entidadPartidaNew.getEntidadCuenta();
                if (oldEntidadCuentaOfEntidadPartida != null) {
                    oldEntidadCuentaOfEntidadPartida.setEntidadPartida(null);
                    oldEntidadCuentaOfEntidadPartida = em.merge(oldEntidadCuentaOfEntidadPartida);
                }
                entidadPartidaNew.setEntidadCuenta(entidadCuenta);
                entidadPartidaNew = em.merge(entidadPartidaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = entidadCuenta.getUsuario();
                if (findEntidadCuenta(id) == null) {
                    throw new NonexistentEntityException("The entidadCuenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadCuenta entidadCuenta;
            try {
                entidadCuenta = em.getReference(EntidadCuenta.class, id);
                entidadCuenta.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entidadCuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            EntidadPerfil entidadPerfilOrphanCheck = entidadCuenta.getEntidadPerfil();
            if (entidadPerfilOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EntidadCuenta (" + entidadCuenta + ") cannot be destroyed since the EntidadPerfil " + entidadPerfilOrphanCheck + " in its entidadPerfil field has a non-nullable entidadCuenta field.");
            }
            EntidadPartida entidadPartidaOrphanCheck = entidadCuenta.getEntidadPartida();
            if (entidadPartidaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EntidadCuenta (" + entidadCuenta + ") cannot be destroyed since the EntidadPartida " + entidadPartidaOrphanCheck + " in its entidadPartida field has a non-nullable entidadCuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(entidadCuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EntidadCuenta> findEntidadCuentaEntities() {
        return findEntidadCuentaEntities(true, -1, -1);
    }

    public List<EntidadCuenta> findEntidadCuentaEntities(int maxResults, int firstResult) {
        return findEntidadCuentaEntities(false, maxResults, firstResult);
    }

    private List<EntidadCuenta> findEntidadCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EntidadCuenta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EntidadCuenta findEntidadCuenta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EntidadCuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntidadCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EntidadCuenta> rt = cq.from(EntidadCuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //mérodo que obtiene la cuenta en función del usuario y la contraseña
    //String genero, String correo, Date fechaNacimiento, String idioma, String foto, Date fechaCreacion
    public Cuenta buscarCuenta(Cuenta cuenta) {
        EntityManager em = getEntityManager();
        try {
            EntidadCuenta entidadCuenta = (EntidadCuenta) em.createNamedQuery("EntidadCuenta.findByUsuarioAndContrasena")
                    .setParameter("usuario", cuenta.getUsuario()).setParameter("contrasena", cuenta.getContrasena())
                    .getSingleResult();
            return entidadCuenta.obtenerCuenta();
        } catch (NoResultException ex) {
            System.err.println("LA BASE DE DATOS NO HA ENCONTRA RESULTADOS");
        } catch (NullPointerException ex) {
            System.err.println("LA BASE DE DATOS TIENE ENTIDADES NO ASOCIADAS");
        }
        return null;
    }
}
