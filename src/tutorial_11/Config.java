package src.tutorial_11;

class Config {
    final int size;

    Config() {
        this.size = 100; // happens-before publishing the reference
    }
}