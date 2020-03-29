package Calendar.Logic;

public interface ISubject {

    public void notifyAllObservers();

    public void registriesObservers(IObserver observer);

    public void deregisterObservers(IObserver observer);

    public void deleteAllObject();
}
