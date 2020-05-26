package br.com.luizmoratelli.truco;

public enum Digit {
    A, Two, Three, Four, Five, Six, Seven, Q, J, K;

    private static Digit[] vals = values();

    public Digit next() {
        return vals[(this.ordinal() + 1 ) % vals.length];
    }
}
