package br.com.etecalbertoferes.CaixaDeSugestoes.domain;

public record AdminAccount(Long id, String username, String passwordHash, boolean active) {
    @Override public String toString() { return "AdminAccount[id=" + id + ", active=" + active + "]"; }
}
