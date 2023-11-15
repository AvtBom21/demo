package Model;

public interface Function <E>{
    void getAll();

    void getOne(String Email);

    void add(E object);

    void delete_onep(String Email);
    void delete(E object);
    void update(E object);
}
