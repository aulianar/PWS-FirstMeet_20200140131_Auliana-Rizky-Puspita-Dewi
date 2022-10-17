/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.connectingdb;

import com.example.connectingdb.exceptions.IllegalOrphanException;
import com.example.connectingdb.exceptions.NonexistentEntityException;
import com.example.connectingdb.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER DJOGJA
 */
public class TransaksiJpaController implements Serializable {

    public TransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaksi transaksi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Pembeli idPembeliOrphanCheck = transaksi.getIdPembeli();
        if (idPembeliOrphanCheck != null) {
            Transaksi oldTransaksiOfIdPembeli = idPembeliOrphanCheck.getTransaksi();
            if (oldTransaksiOfIdPembeli != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pembeli " + idPembeliOrphanCheck + " already has an item of type Transaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
            }
        }
        Karyawan idKaryawanOrphanCheck = transaksi.getIdKaryawan();
        if (idKaryawanOrphanCheck != null) {
            Transaksi oldTransaksiOfIdKaryawan = idKaryawanOrphanCheck.getTransaksi();
            if (oldTransaksiOfIdKaryawan != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Karyawan " + idKaryawanOrphanCheck + " already has an item of type Transaksi whose idKaryawan column cannot be null. Please make another selection for the idKaryawan field.");
            }
        }
        Kue kodeKueOrphanCheck = transaksi.getKodeKue();
        if (kodeKueOrphanCheck != null) {
            Transaksi oldTransaksiOfKodeKue = kodeKueOrphanCheck.getTransaksi();
            if (oldTransaksiOfKodeKue != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Kue " + kodeKueOrphanCheck + " already has an item of type Transaksi whose kodeKue column cannot be null. Please make another selection for the kodeKue field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pembeli idPembeli = transaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli = em.getReference(idPembeli.getClass(), idPembeli.getIdPembeli());
                transaksi.setIdPembeli(idPembeli);
            }
            Karyawan idKaryawan = transaksi.getIdKaryawan();
            if (idKaryawan != null) {
                idKaryawan = em.getReference(idKaryawan.getClass(), idKaryawan.getIdKaryawan());
                transaksi.setIdKaryawan(idKaryawan);
            }
            Kue kodeKue = transaksi.getKodeKue();
            if (kodeKue != null) {
                kodeKue = em.getReference(kodeKue.getClass(), kodeKue.getKodeKue());
                transaksi.setKodeKue(kodeKue);
            }
            em.persist(transaksi);
            if (idPembeli != null) {
                idPembeli.setTransaksi(transaksi);
                idPembeli = em.merge(idPembeli);
            }
            if (idKaryawan != null) {
                idKaryawan.setTransaksi(transaksi);
                idKaryawan = em.merge(idKaryawan);
            }
            if (kodeKue != null) {
                kodeKue.setTransaksi(transaksi);
                kodeKue = em.merge(kodeKue);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaksi(transaksi.getKodeTransaksi()) != null) {
                throw new PreexistingEntityException("Transaksi " + transaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaksi transaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi persistentTransaksi = em.find(Transaksi.class, transaksi.getKodeTransaksi());
            Pembeli idPembeliOld = persistentTransaksi.getIdPembeli();
            Pembeli idPembeliNew = transaksi.getIdPembeli();
            Karyawan idKaryawanOld = persistentTransaksi.getIdKaryawan();
            Karyawan idKaryawanNew = transaksi.getIdKaryawan();
            Kue kodeKueOld = persistentTransaksi.getKodeKue();
            Kue kodeKueNew = transaksi.getKodeKue();
            List<String> illegalOrphanMessages = null;
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                Transaksi oldTransaksiOfIdPembeli = idPembeliNew.getTransaksi();
                if (oldTransaksiOfIdPembeli != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pembeli " + idPembeliNew + " already has an item of type Transaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
                }
            }
            if (idKaryawanNew != null && !idKaryawanNew.equals(idKaryawanOld)) {
                Transaksi oldTransaksiOfIdKaryawan = idKaryawanNew.getTransaksi();
                if (oldTransaksiOfIdKaryawan != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Karyawan " + idKaryawanNew + " already has an item of type Transaksi whose idKaryawan column cannot be null. Please make another selection for the idKaryawan field.");
                }
            }
            if (kodeKueNew != null && !kodeKueNew.equals(kodeKueOld)) {
                Transaksi oldTransaksiOfKodeKue = kodeKueNew.getTransaksi();
                if (oldTransaksiOfKodeKue != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Kue " + kodeKueNew + " already has an item of type Transaksi whose kodeKue column cannot be null. Please make another selection for the kodeKue field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPembeliNew != null) {
                idPembeliNew = em.getReference(idPembeliNew.getClass(), idPembeliNew.getIdPembeli());
                transaksi.setIdPembeli(idPembeliNew);
            }
            if (idKaryawanNew != null) {
                idKaryawanNew = em.getReference(idKaryawanNew.getClass(), idKaryawanNew.getIdKaryawan());
                transaksi.setIdKaryawan(idKaryawanNew);
            }
            if (kodeKueNew != null) {
                kodeKueNew = em.getReference(kodeKueNew.getClass(), kodeKueNew.getKodeKue());
                transaksi.setKodeKue(kodeKueNew);
            }
            transaksi = em.merge(transaksi);
            if (idPembeliOld != null && !idPembeliOld.equals(idPembeliNew)) {
                idPembeliOld.setTransaksi(null);
                idPembeliOld = em.merge(idPembeliOld);
            }
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                idPembeliNew.setTransaksi(transaksi);
                idPembeliNew = em.merge(idPembeliNew);
            }
            if (idKaryawanOld != null && !idKaryawanOld.equals(idKaryawanNew)) {
                idKaryawanOld.setTransaksi(null);
                idKaryawanOld = em.merge(idKaryawanOld);
            }
            if (idKaryawanNew != null && !idKaryawanNew.equals(idKaryawanOld)) {
                idKaryawanNew.setTransaksi(transaksi);
                idKaryawanNew = em.merge(idKaryawanNew);
            }
            if (kodeKueOld != null && !kodeKueOld.equals(kodeKueNew)) {
                kodeKueOld.setTransaksi(null);
                kodeKueOld = em.merge(kodeKueOld);
            }
            if (kodeKueNew != null && !kodeKueNew.equals(kodeKueOld)) {
                kodeKueNew.setTransaksi(transaksi);
                kodeKueNew = em.merge(kodeKueNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = transaksi.getKodeTransaksi();
                if (findTransaksi(id) == null) {
                    throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi;
            try {
                transaksi = em.getReference(Transaksi.class, id);
                transaksi.getKodeTransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.", enfe);
            }
            Pembeli idPembeli = transaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli.setTransaksi(null);
                idPembeli = em.merge(idPembeli);
            }
            Karyawan idKaryawan = transaksi.getIdKaryawan();
            if (idKaryawan != null) {
                idKaryawan.setTransaksi(null);
                idKaryawan = em.merge(idKaryawan);
            }
            Kue kodeKue = transaksi.getKodeKue();
            if (kodeKue != null) {
                kodeKue.setTransaksi(null);
                kodeKue = em.merge(kodeKue);
            }
            em.remove(transaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaksi> findTransaksiEntities() {
        return findTransaksiEntities(true, -1, -1);
    }

    public List<Transaksi> findTransaksiEntities(int maxResults, int firstResult) {
        return findTransaksiEntities(false, maxResults, firstResult);
    }

    private List<Transaksi> findTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaksi.class));
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

    public Transaksi findTransaksi(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaksi> rt = cq.from(Transaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
