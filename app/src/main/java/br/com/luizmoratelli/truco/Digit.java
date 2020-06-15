package br.com.luizmoratelli.truco;

public enum Digit {
    A, Two, Three, Four, Five, Six, Seven, Q, J, K;

    private static Digit[] vals = values();

    /*
    * Retorna o valor da próxima carta. (Para saber qual é o coringa)
     */
    public Digit next() {
        return vals[(this.ordinal() + 1 ) % vals.length];
    }
}
