package Calendar.Logic;

public interface ISubject {

    public void notifyAllObservers();

    public void registriesObservers(Iobserver observer);

    public void deregisterObservers(Iobserver observer);
}
