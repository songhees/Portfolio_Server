package pofo_server.vo;

public record Pageable(int page, int size) {

    public Pageable() {
        this(0, 10);
    }
}